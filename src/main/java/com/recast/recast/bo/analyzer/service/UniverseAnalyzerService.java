package com.recast.recast.bo.analyzer.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.crystaldecisions.sdk.exception.SDKException;
import com.crystaldecisions.sdk.framework.CrystalEnterprise;
import com.crystaldecisions.sdk.framework.IEnterpriseSession;
import com.recast.recast.bo.analyzer.dto.BoReportConfigDTO;
import com.recast.recast.bo.analyzer.model.BOColumn;
import com.recast.recast.bo.analyzer.model.BOItem;
import com.recast.recast.bo.analyzer.model.BOJoin;
import com.recast.recast.bo.analyzer.model.BONavigationPath;
import com.recast.recast.bo.analyzer.model.BOTable;
import com.recast.recast.bo.analyzer.model.BOUniverse;
import com.sap.sl.sdk.authoring.businesslayer.Attribute;
import com.sap.sl.sdk.authoring.businesslayer.BlItem;
import com.sap.sl.sdk.authoring.businesslayer.Dimension;
import com.sap.sl.sdk.authoring.businesslayer.Filter;
import com.sap.sl.sdk.authoring.businesslayer.Folder;
import com.sap.sl.sdk.authoring.businesslayer.Measure;
import com.sap.sl.sdk.authoring.businesslayer.NativeRelationalFilter;
import com.sap.sl.sdk.authoring.businesslayer.NavigationPath;
import com.sap.sl.sdk.authoring.businesslayer.RelationalBinding;
import com.sap.sl.sdk.authoring.businesslayer.RelationalBusinessLayer;
import com.sap.sl.sdk.authoring.cms.CmsResourceService;
import com.sap.sl.sdk.authoring.datafoundation.Column;
import com.sap.sl.sdk.authoring.datafoundation.DataFoundation;
import com.sap.sl.sdk.authoring.datafoundation.DatabaseTable;
import com.sap.sl.sdk.authoring.datafoundation.Join;
import com.sap.sl.sdk.authoring.datafoundation.MonoSourceDataFoundation;
import com.sap.sl.sdk.authoring.datafoundation.SQLJoin;
import com.sap.sl.sdk.authoring.datafoundation.Table;
import com.sap.sl.sdk.authoring.local.LocalResourceService;
import com.sap.sl.sdk.framework.SlContext;
import com.sap.sl.sdk.framework.cms.CmsSessionService;

public class UniverseAnalyzerService {
	private static DataFoundation dataFoundation;
	private static CmsResourceService cmsResourceService;
	private static LocalResourceService localResourceService;
	private static IEnterpriseSession enterpriseSession;
	private static RelationalBusinessLayer businessLayer;
	private static SlContext slContext;
	
	private static String AUTHENTICATION = "secEnterprise";
	private static String TEMP_PATH = System.getProperty("java.io.tmpdir");
	
	private static Logger logger = Logger.getLogger(UniverseAnalyzerService.class);
	
	//private static List<Dimension> dimensionList = new ArrayList<Dimension>();
	//private static List<Attribute> attributeList = new ArrayList<Attribute>();
	//private static List<Measure> measureList = new ArrayList<Measure>();
	//private static List<Filter> filterList = new ArrayList<Filter>();
	//private static List<Folder> folderList = new ArrayList<Folder>();
	
	private static Set<Dimension> dimensionList = new LinkedHashSet<Dimension>();
	private static Set<Attribute> attributeList = new LinkedHashSet<Attribute>();
	private static Set<Measure> measureList = new LinkedHashSet<Measure>();
	private static Set<Filter> filterList = new LinkedHashSet<Filter>();
	private static Set<Folder> folderList = new LinkedHashSet<Folder>();
	
	private static BOUniverse boUniverse;
	
	
	
	
	
	public static BOUniverse getBOUniverseData(BoReportConfigDTO config, String path, BOUniverse unv) {
		boUniverse = unv;
		boolean flag = false;
		slContext = SlContext.create();
		try {
			/* Connect to BO */
			connectToBOBJ(config); 
			
			/* Set all services for the analyzer */
			cmsResourceService = slContext.getService(CmsResourceService.class);
			localResourceService = slContext.getService(LocalResourceService.class);
			
			extractSemanticLayer(path);
			getTables();
			getJoins();
			getBlModelRecurcively(businessLayer.getRootFolder(),flag);
			processItems();
			
			System.out.println("BO Universe::"+boUniverse.getItems().get("folders"));
			
			getNavigationPaths(); 
			//getConnections(path);
		}
		catch(SDKException e) {
			e.printStackTrace();
		}
		return boUniverse;
	}
	
	private static void connectToBOBJ(BoReportConfigDTO config) throws SDKException {
		/* Connect to BO */
		String userName = config.getUsername();
		String password = config.getPassword();
		String server = config.getHostname();
		enterpriseSession = CrystalEnterprise.getSessionMgr().logon(userName, password, server, AUTHENTICATION);
		slContext.getService(CmsSessionService.class).setSession(enterpriseSession);
		System.out.println("Connection successful");
	}
	
	private static void extractSemanticLayer(String path) {
		/* Download semantic layer to temporary location */
		System.out.println("Downloading universe");
		String retrieveUniverse = "";
		try {
			retrieveUniverse = cmsResourceService.retrieveUniverse(path, TEMP_PATH, true);
		}
		catch(Exception e) {
			e.printStackTrace();
			return;
		}
		System.out.println("Universe downloaded at: " + retrieveUniverse);
		try
		{
			localResourceService.load(retrieveUniverse);
			businessLayer = (RelationalBusinessLayer) localResourceService.load(retrieveUniverse);
			String dfxPath = businessLayer.getDataFoundationPath();
			dataFoundation = (MonoSourceDataFoundation) localResourceService.load(dfxPath);	
		}
		catch(Exception e1)
		{
			e1.printStackTrace();
		}
		
	}
	
	private static void getTables() {
		/* Extract list of tables and their columns from data foundation and add to universe object */
		System.out.println("Extracting tables");		
		for(Table x : dataFoundation.getTables()) {
			try {
				DatabaseTable t = (DatabaseTable) x;
				System.out.println("Owner: " + t.getOwner() + "\nName: " + t.getName() + "\nQualifier" + t.getQualifier() + "\nPrimary Key" + t.getPrimaryKey());
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			BOTable table = new BOTable();
			table.setName(x.getName());
			for (Column c : x.getColumns()) {
				BOColumn column = new BOColumn();
				column.setName(c.getName());
				column.setDataType(c.getDataType().toString());
				table.getColumns().add(column);
				
			}
			boUniverse.getTables().add(table);
		};
		//System.out.println("Table extraction complete");
	}
	
	private static void getJoins() {
		/* Extract list of joins from data foundation and add to universe object */
		System.out.println("Extracting joins");
		for(Join j : dataFoundation.getJoins()) {
			SQLJoin sj = (SQLJoin) j;
			BOJoin join = new BOJoin();
			join.setCardinality(sj.getCardinality().toString());
			join.setIdentifier(sj.getIdentifier());
			join.setLeftTable(sj.getLeftTable().getName());
			join.setRightTable(sj.getRightTable().getName());
			join.setOuterType(sj.getOuterType().toString());
			join.setExpression(sj.getExpression());
			boUniverse.getJoins().add(join);
		}
		System.out.println("Join extraction complete");
	}
	
	private static void getBlModelRecurcively(BlItem biRootFolder, boolean flag) {
		
	//	System.out.println("BI Root folder::"+biRootFolder.getName());
		
		if(!flag)
		{
			folderList = new LinkedHashSet<Folder>();
			dimensionList = new LinkedHashSet<Dimension>();
			attributeList = new LinkedHashSet<Attribute>();
			measureList = new LinkedHashSet<Measure>();
			filterList = new LinkedHashSet<Filter>();
			
		}
		
		//System.out.println("Folder List::"+folderList);
		
		List<BlItem> kids = new ArrayList<BlItem>();
		
		//Set<BlItem> kids = new HashSet<BlItem>();
		
		/* If folder add contents to the kids list */
		
		//folderList = new LinkedHashSet<Folder>();
		
		if (biRootFolder instanceof Folder) {
			Folder rootFolder = (Folder) biRootFolder;
			folderList.add(rootFolder);
			kids = rootFolder.getChildren();
			//kids = (Set<BlItem>) rootFolder.getChildren();

		}

		/* Iterate recursively */
		if (kids != null && kids.size() != 0) {
			List<BlItem> folders = getChildren(kids);
			if (folders != null && folders.size() != 0) {
				for (BlItem folder : folders) {
					getBlModelRecurcively(folder,true);
				}
			}
		}
	}
	
	private static List<BlItem> getChildren(List<BlItem> blItems) {
		List<BlItem> classList = new ArrayList<BlItem>();
		if (blItems != null) {
			for (BlItem blItem : blItems) {
				
				// Extracting Dimensions
				if (blItem instanceof Dimension) {
					Dimension dimension = (Dimension) blItem;
					
					//System.out.println("Dimension::"+dimension);
					
					dimensionList.add(dimension);
					List<BlItem> attributes = dimension.getChildren();
					//Set<BlItem> attributes = (Set<BlItem>) dimension.getChildren();
					
					// Extracting attributes for the dimension
					for (BlItem biAttribute : attributes) {
						Attribute attribute = (Attribute) biAttribute;
						//System.out.println("Attribute::"+attribute.);
						attributeList.add(attribute);
					}
				}

				// Extracting Measures
				if (blItem instanceof Measure) {
					Measure measure = (Measure) blItem;
					//System.out.println("");
					measureList.add(measure);
				}

				// Extracting Filters
				if (blItem instanceof Filter) {
					Filter filter = (Filter) blItem;
					filterList.add(filter);
				}

				classList.add(blItem);
			}
			return classList;
		} 
		else {
			return null;
		}	
	}
	
	private static void processItems() {
		/* Add all items to the BO Universe object*/
		
		/* Initialize empty array lists in the items map */
		boUniverse.getItems().put("dimensions", new LinkedHashSet<BOItem>());
		boUniverse.getItems().put("measures", new LinkedHashSet<BOItem>());
		boUniverse.getItems().put("filters", new LinkedHashSet<BOItem>());
		boUniverse.getItems().put("attributes", new LinkedHashSet<BOItem>());
		boUniverse.getItems().put("folders", new LinkedHashSet<BOItem>());
		
		System.out.println("Folders List Map::"+boUniverse.getItems().get("folders"));
		
		/* Add dimensions */
		for (Dimension x : dimensionList) {
			BOItem item = new BOItem();
			RelationalBinding binding = (RelationalBinding) x.getBinding();
			item.setName(x.getName());
			item.setDataType(x.getDataType().toString());
			item.setSelect(binding.getSelect());
			item.setWhere(binding.getWhere());
			item.setPath(modifyPath(x.getPath()));
		//	System.out.println("Identifier Dimension::"+x.getIdentifier());
			item.setObjectIdentifier(x.getIdentifier());
			boUniverse.getItems().get("dimensions").add(item);
		}
		
		/* Add measures */
		for (Measure x : measureList) {
			BOItem item = new BOItem();
			RelationalBinding binding = (RelationalBinding) x.getBinding();
			item.setName(x.getName());
			item.setDataType(x.getDataType().toString());
			item.setSelect(binding.getSelect());
			item.setWhere(binding.getWhere());
			item.setProjectionFunction(x.getProjectionFunction().toString());
			item.setPath(modifyPath(x.getPath()));
			//System.out.println("Identifier Measure::"+x.toString());
			item.setObjectIdentifier(x.getIdentifier());
			boUniverse.getItems().get("measures").add(item);
		}
		
		/* Add Filters */
		for (Filter y : filterList) {
			BOItem item = new BOItem();
			try {
				/* Try casting to native relational filter */
				NativeRelationalFilter x = (NativeRelationalFilter) y;
				RelationalBinding binding = (RelationalBinding) x.getBinding();
				item.setSelect(binding.getSelect());
				item.setWhere(binding.getWhere());
			//	System.out.println("Identifier Filters x::"+x.getIdentifier());
				//item.setObjectIdentifier();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			
		//	System.out.println("Identifier Filters y::"+y.getIdentifier());
			item.setObjectIdentifier(y.getIdentifier());
			item.setName(y.getName());
			item.setPath(modifyPath(y.getPath()));
			
			
			boUniverse.getItems().get("filters").add(item);
		}
		
		/* Add attributes */
		for (Attribute x : attributeList) {
			BOItem item = new BOItem();
			RelationalBinding binding = (RelationalBinding) x.getBinding();
			item.setName(x.getName());
			item.setDataType(x.getDataType().toString());
			item.setSelect(binding.getSelect());
			item.setWhere(binding.getWhere());
			item.setPath(modifyPath(x.getPath()));
		//	System.out.println("Identifier Attributes::"+x.toString());
			item.setObjectIdentifier(x.getIdentifier());
			boUniverse.getItems().get("attributes").add(item);
		}
		
		/* Add folders */
		
		
		
		System.out.println("Folder List length::"+folderList.size());
		
		for (Folder x : folderList) {
			BOItem item = new BOItem();
			item.setName(x.getName());
			item.setPath(modifyPath(x.getPath()));
			item.setObjectIdentifier(x.getIdentifier());
			boUniverse.getItems().get("folders").add(item);
		}
	}
	
	public static void getConnections(String path) {
		List<String> connections = cmsResourceService.getUniverseConnections(path);
		connections.forEach(x -> System.out.println("Connection kalpesh:"+x));
	}
	
	public static void getUsedTables(BOUniverse unv) {
		for (Set<BOItem> itemList : unv.getItems().values()) {
			for (BOItem item : itemList) {
				processQuery(item.getSelect(), 2);
			}
		}
	}
	
	public static void processQuery(String s, int format) {
		Map<String, Set<String>> objects = new HashMap<String, Set<String>> ();
		Pattern pattern;
		if(format == 3)
			pattern = Pattern.compile("[a-zA-Z0-9_]+\\.[a-zA-Z0-9_]+\\.[a-zA-Z0-9_]+"); // db.table.column
		else
			pattern = Pattern.compile("[a-zA-Z0-9_]+\\.[a-zA-Z0-9_]+"); // table.column
		Matcher matcher = pattern.matcher(s);
		while (matcher.find()) {
			String dotSplit[] = matcher.group().split("\\.");
			/* table.column */
			if(dotSplit.length == 2) {  
				if(!objects.containsKey(dotSplit[0])) {
					objects.put(dotSplit[0], new HashSet<String>());
				} 
				objects.get(dotSplit[0]).add(dotSplit[1]);
			}	
			/* db.table.column */
			else if(dotSplit.length == 3) {
				if(!objects.containsKey(dotSplit[1])) {
					objects.put(dotSplit[1], new HashSet<String>());
				}
				objects.get(dotSplit[1]).add(dotSplit[2]);
			}			
		}
		for(Map.Entry<String, Set<String>> entry : objects.entrySet()) {
			System.out.println("Table: " + entry.getKey());
			System.out.println("Columns:");
			for(String value : entry.getValue()) {
				System.out.println(value);
			}
		}
	}
	
	public static void processNestedQuery(String s) {
		Pattern pattern = Pattern.compile("@Select\\(.+?\\)");
		Matcher matcher = pattern.matcher(s);
		while (matcher.find()) {
			String splitSlash[] = matcher.group().split("\\\\");
			System.out.println("Nested Object: " + splitSlash[splitSlash.length - 1].replace(")", ""));
		}
	}
	
	public static void getNavigationPaths() {
		for(NavigationPath x : businessLayer.getCustomNavigationPaths()) {
			BONavigationPath navigationPath = new BONavigationPath();
			navigationPath.setName(x.getName());
			System.out.println(x.getName());
			for(Dimension d : x.getDimensions()) {
				navigationPath.getChildren().add(d.getName());
				System.out.println(d.getName());
			}
			System.out.println("---------------");
		}
	}
	private static String modifyPath(String path)
	{
		if(path!=null)
		{
		if(path.contains("|folder"))
		{
			path = path.replaceAll("\\|folder", "");
		}
		if(path.contains("|filter"))
		{
			path = path.replaceAll("\\|filter", "");
		}
		if(path.contains("|dimension"))
		{
			path = path.replaceAll("\\|dimension", "");
		}
		if(path.contains("|attribute"))
		{
			path = path.replaceAll("\\|attribute", "");
		}
		if(path.contains("|measure"))
		{
			path = path.replaceAll("\\|measure", "");
		}
		}
		return path;
	}
	
	public static void main(String args[]) {
		logger.info("Test");
		BoReportConfigDTO config = new BoReportConfigDTO();
//		config.setHostname("172.21.102.84");
//		config.setUsername("10663796");
//		config.setPassword("4pplemUffin");
		config.setHostname("172.21.106.66");
		config.setUsername("user1");
		config.setPassword("user1");
//		String path = "/Universes/Samples/eFashion.unx";
		String path = "/Universes/eFashion_New2.unx";
		BOUniverse universe = getBOUniverseData(config, path, new BOUniverse());
		System.out.println(universe.getTablesJSON());
		System.out.println(universe.getJoinsJSON());
		System.out.println(universe.getBOItemsJSON());
//		getUsedTables(universe);
//		String ip = "IIf(@Select(Measures\\Sales revenue)>0, \r\n   IIf(@Select(Measures\\Quantity sold)>=0,\r\n       @Select(Measures\\Sales revenue)/@Select(Measures\\Quantity sold)))";
//		processNestedQuery(ip);
//		String ip = "select EDW.FORECAST_ORDSHIP_V.ORDER_SITE, EDW.SITE_D.SITE_DESC, substr(EDW.PRODUCT_SKU_D.SHIP_BATRY,3,4), substr(EDW.PRODUCT_D.SAP_PRODCT_ID,12,7) sap_no, CASE when EDW.PRODUCT_D.PRODCT_DESC1 like '%GRY%' then 'GREY' else 'BLACK' end color, max(EDW.PRODUCT_SKU_D.GRP_SZ) grp_sz, max(STG.DAILY_DEMAND_PLANNING_S.TEN_DAYS_OPEN) ten_days_open, max(STG.DAILY_DEMAND_PLANNING_S.DMND_4WK) four_week, max(STG.DAILY_DEMAND_PLANNING_S.DMND_8WK) eight_week, max(STG.DAILY_DEMAND_PLANNING_S.FIRM_SCHD) Firm_sch, max(STG.DAILY_DEMAND_PLANNING_S.RLSD_SCHD) Rlse_sch, max(STG.DAILY_DEMAND_PLANNING_S.DECO_QTY) Deco, max(STG.DAILY_DEMAND_PLANNING_S.IBT_ORDR_QTY) ibt, max(STG.DAILY_DEMAND_PLANNING_S.IN_TRANST_QTY) transit, max(STG.DAILY_DEMAND_PLANNING_S.WET_QTY) wet, max(STG.DAILY_DEMAND_PLANNING_S.DUF_QTY) duf, sum(EDW.FORECAST_ORDSHIP_V.FORECAST_QUANTITY) forecast, sum(EDW.FORECAST_ORDSHIP_V.LINE_ORDER_QUANTITY) order_qty, sum(EDW.FORECAST_ORDSHIP_V.LINE_SHIP_QUANTITY) ship_qty  from EDW.FORECAST_ORDSHIP_V, EDW.PRODUCT_SKU_D, STG.DAILY_DEMAND_PLANNING_S, EDW.PRODUCT_D, EDW.SITE_D where EDW.FORECAST_ORDSHIP_V.PRODUCT_KEY = EDW.PRODUCT_SKU_D.PRODCT_KEY and EDW.PRODUCT_SKU_D.SHIP_BATRY = EDW.PRODUCT_D.PRODCT_ID and EDW.FORECAST_ORDSHIP_V.ORDER_SITE = EDW.PRODUCT_SKU_D.SITE_CD and EDW.FORECAST_ORDSHIP_V.ORDER_SITE = EDW.SITE_D.SITE_CD and EDW.PRODUCT_D.SOURCE_COUNTRY = 'USA'  and EDW.FORECAST_ORDSHIP_V.ORDER_REQUIRED_PERIOD = to_char(trunc(sysdate-1),'YYYYMM')  --and STG.DAILY_DEMAND_PLANNING_S.SITE_CD = EDW.FORECAST_ORDSHIP_V.ORDER_SITE and( CASE when STG.DAILY_DEMAND_PLANNING_S.SITE_CD = '1110' then '916'          when STG.DAILY_DEMAND_PLANNING_S.SITE_CD = '1114' then '914' else  STG.DAILY_DEMAND_PLANNING_S.SITE_CD end ) = EDW.FORECAST_ORDSHIP_V.ORDER_SITE and substr(STG.DAILY_DEMAND_PLANNING_S.JCI_PN,3,4) = substr(EDW.PRODUCT_SKU_D.SHIP_BATRY,3,4) --and substr(EDW.PRODUCT_SKU_D.SHIP_BATRY,3,4) = '1206' group by EDW.FORECAST_ORDSHIP_V.ORDER_SITE, EDW.SITE_D.SITE_DESC, EDW.PRODUCT_SKU_D.SHIP_BATRY, substr(EDW.PRODUCT_D.SAP_PRODCT_ID,12,7), CASE when EDW.PRODUCT_D.PRODCT_DESC1 like '%GRY%' then 'GREY' else 'BLACK' end, STG.DAILY_DEMAND_PLANNING_S.TEN_DAYS_OPEN, STG.DAILY_DEMAND_PLANNING_S.DMND_4WK, STG.DAILY_DEMAND_PLANNING_S.DMND_8WK, STG.DAILY_DEMAND_PLANNING_S.FIRM_SCHD, STG.DAILY_DEMAND_PLANNING_S.RLSD_SCHD, STG.DAILY_DEMAND_PLANNING_S.DECO_QTY, STG.DAILY_DEMAND_PLANNING_S.IBT_ORDR_QTY, STG.DAILY_DEMAND_PLANNING_S.IN_TRANST_QTY, STG.DAILY_DEMAND_PLANNING_S.WET_QTY, STG.DAILY_DEMAND_PLANNING_S.DUF_QTY order by EDW.PRODUCT_SKU_D.SHIP_BATRY";
//		String ip = "@Aggregate_Aware(\r\nsum(Shop_facts.Quantity_sold * Article_lookup.Sale_price - Shop_facts.Amount_sold),\r\nsum(Shop_facts.Quantity_sold * Article_Color_Lookup.Sale_price - Shop_facts.Amount_sold))";
//		processQuery(ip, 2);
		
	}
}
