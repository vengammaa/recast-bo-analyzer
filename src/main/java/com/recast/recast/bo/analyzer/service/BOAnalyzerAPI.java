package com.recast.recast.bo.analyzer.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQResultSequence;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.recast.recast.bo.analyzer.dto.BoReportConfigDTO;
import com.recast.recast.bo.analyzer.model.Alerters;
import com.recast.recast.bo.analyzer.model.Axes;
import com.recast.recast.bo.analyzer.model.AxisFormula;
import com.recast.recast.bo.analyzer.model.BOAnalyzerModel;
import com.recast.recast.bo.analyzer.model.BOChartElement;
import com.recast.recast.bo.analyzer.model.BOCommonalityParams;
import com.recast.recast.bo.analyzer.model.BOComplexityParameters;
import com.recast.recast.bo.analyzer.model.BOQueryFilter;
import com.recast.recast.bo.analyzer.model.BOReportColumn;
import com.recast.recast.bo.analyzer.model.BOReportObject;
import com.recast.recast.bo.analyzer.model.BOReportQuery;
import com.recast.recast.bo.analyzer.model.BOReportQueryColumn;
import com.recast.recast.bo.analyzer.model.BOReportTab;
import com.recast.recast.bo.analyzer.model.BOReportVisualizationTab;
import com.recast.recast.bo.analyzer.model.BOTableElement;
import com.recast.recast.bo.analyzer.model.BOUniverse;
import com.recast.recast.bo.analyzer.model.BOVariable;
import com.recast.recast.bo.analyzer.model.BOVisualElements;
import com.recast.recast.bo.analyzer.model.BOVisualizationModel;
import com.recast.recast.bo.analyzer.model.Child;
import com.recast.recast.bo.analyzer.model.DataFilter;
import com.recast.recast.bo.analyzer.model.InputControl;
import com.recast.recast.bo.analyzer.model.QueryChildren;
import com.recast.recast.bo.analyzer.model.QueryFilter;
import com.recast.recast.bo.analyzer.model.ReportData;
import com.recast.recast.bo.analyzer.model.Rule;
import com.recast.recast.bo.analyzer.model.Zones;
import com.recast.recast.bo.analyzer.util.AuthMode;
import com.recast.recast.bo.analyzer.util.BOWebClient;
import com.saxonica.xqj.SaxonXQDataSource;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SelectItemVisitorAdapter;
import net.sf.jsqlparser.util.TablesNamesFinder;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitorAdapter;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;



public class BOAnalyzerAPI {
	
	static final Logger logger = Logger.getLogger(BOAnalyzerAPI.class);
	
	private static String CMSSERVERIP = null;
	private static String BOUSERID = null;
	private static String BOPASSWORD = null;
	private static AuthMode authmode = AuthMode.ENTERPRISE;
	private static String BOAUTH = authmode.getAuthMode();
	private static String BO_WS_PORT = null; 
	private static String PATH = null;
	private static String logonToken;
	private static List<String> reportIdList;
	
	private static final String REQUEST_METHOD_POST = "POST";
	private static final String REQUEST_METHOD_GET = "GET";
	private static final String REQUEST_PROPERTY_ACCEPT = "Accept";
	private static final String REQUEST_PROPERTY_APPL_XML = "application/xml";
	private static final String REQUEST_PROPERTY_APPL_JSON = "application/json";
	private static final String REQUEST_PROPERTY_CONTENT_TYPE = "Content-Type";
	private static final String REQUEST_PROPERTY_X_SAP_LOGONTOKEN = "X-SAP-LogonToken";
	private static String REQUEST_PROPERTY_X_SAP_LOGONTOKEN_VALUE;
	private static final String WEBI_REPORT_TYPE = "Webi";
	
	private static BOWebClient webClient;
	
	private final double padding=0.50;
	
	
	
	public Map<String, Object> fetchBOAnalyzerModelList(BoReportConfigDTO config){
		Map<String, Object> analyzerData = new HashMap<String, Object>();
		List<BOAnalyzerModel> boReportModelList = new ArrayList<BOAnalyzerModel>();
		Set<Integer> universeIdSet = new HashSet<Integer>();
		List<BOUniverse> boUniverseList = new ArrayList<BOUniverse>();
		
		List<BOComplexityParameters> boReportComplexityList = new ArrayList<BOComplexityParameters>();
		
		List<BOVisualizationModel> boVisualizationList = new ArrayList<BOVisualizationModel>();
		
		List<BOCommonalityParams> boCommonalityParamsList = new ArrayList<BOCommonalityParams>();
		
		//Map<Integer,BOVisualizationModel> boVisualizationMap= new HashMap<Integer, BOVisualizationModel>();
		
		
		try {
			
			CMSSERVERIP = config.getHostname();
			BOUSERID = config.getUsername();
			BOPASSWORD = config.getPassword();
			BO_WS_PORT = config.getPort();
		//	PATH = config.getPath();
			reportIdList=config.getReports();
			logonToken = logonAndGetToken(CMSSERVERIP, BOUSERID, BOPASSWORD);
			REQUEST_PROPERTY_X_SAP_LOGONTOKEN_VALUE = "\""+logonToken+"\"";
			webClient = new BOWebClient(CMSSERVERIP, BO_WS_PORT, BOUSERID, BOPASSWORD);
			
			Map<Integer, String> documentIdNameMap = new HashMap<Integer, String>();
			
			documentIdNameMap = fetchListOfDocuments(50, 0, documentIdNameMap);
			
			for (Integer documentId : documentIdNameMap.keySet()) {
				
				
				if(!reportIdList.contains(documentId.toString()))
				{
					logger.info("Skipped");
					System.out.println("Skipped");
					continue;
				}
				
				BOAnalyzerModel boReportModel = null;
				
				boReportModel = new BOAnalyzerModel();
				int numberOfFailures = 0, numberOfRecurringInstances = 0, totalUniverseCount = 0, numberOfImages = 0, numberOfBlocks = 0, numberOfFormulas = 0, numberOfTabs = 0, numberOfFilters = 0, numberOfColumns = 0, numberOfQuery = 0, numberOfChartElements = 0, numberOfTableElements = 0;
				AtomicInteger numberOfEmbeddedElements = new AtomicInteger(0);
				List<String> columnNameList = new ArrayList<String>();
				boReportModel.setReportId(documentId);
				boReportModel.setNumberOfFailures(0);
				boReportModel.setReportName(documentIdNameMap.get(documentId));
				boReportModel.setReportType(WEBI_REPORT_TYPE);
				boReportModel = fetchDocumentData(documentId, boReportModel);
				logger.info("Currently processing: " + documentIdNameMap.get(documentId) + "\nReport Path: " + boReportModel.getReportFolderPath());
				//if (!boReportModel.getReportFolderPath().startsWith(PATH)) {
				//	logger.info("Skipped");
				//	continue;
			//	}
				
			//	boReportModel = fetchStyleElement(documentId,boReportModel);
				
				
				boReportModel.setBoReportTabList(fetchListOfReports(documentId, boReportModel));
				

				
				if(boReportModel.getIsReportScheduled()) {
					boReportModel = fetchNumberOfSchedules(documentId, boReportModel);
				}
				else {
					boReportModel.setNumberOfInstances(0);
				}
				
				
				boReportModel.setNumberOfRecurringInstances(numberOfRecurringInstances);
				if(numberOfFailures > 0) {
					boReportModel.setIsReportFailed(Boolean.TRUE);
				}else{
					boReportModel.setIsReportFailed(Boolean.FALSE);
				}

				numberOfImages = 0;
				int numberOfReportElements = 0;
				int countOfReportElements =0;
				int numberOfObjects=0;
				
				List<String> hasDataFilterList = checkDataFilterExist(documentId);

				for(BOReportTab boReportTab : boReportModel.getBoReportTabList()){
					numberOfTabs++;
					fetchReportDrill(documentId, boReportTab.getReportTabId(), numberOfEmbeddedElements, boReportModel);

					if(hasDataFilterList.contains(Integer.toString(boReportTab.getReportTabId())))
					{
					boReportTab.setQueryFilters(fetchReportDataFilter(documentId, boReportTab.getReportTabId(), boReportModel));
					}
					
					if(boReportTab.getQueryFilters() != null && boReportTab.getQueryFilters().size() > 0)
						numberOfFilters += boReportTab.getQueryFilters().size();

					List<BOTableElement> boTableElements = new ArrayList<BOTableElement>();
					List<BOChartElement> boChartElements = new ArrayList<BOChartElement>();
					List<Integer> boReportElementIdList = fetchListOfReportElements(documentId, boReportTab.getReportTabId(), numberOfEmbeddedElements, boReportModel);
					
					
					countOfReportElements = fetchReportElementsSize(documentId,boReportTab.getReportTabId(),numberOfEmbeddedElements, boReportModel);
					numberOfReportElements += countOfReportElements;
					
					
					for(Integer boReportElementId : boReportElementIdList){
						Object boElement = fetchReportElementData(documentId, boReportTab.getReportTabId(), boReportElementId, columnNameList, boReportModel);
						if(boElement != null){
							String className = boElement.getClass().getName();
							if(className.equalsIgnoreCase("com.lti.data.recast.bo.model.BOTableElement")){
								BOTableElement boTableElement = (BOTableElement) boElement;
								numberOfColumns += boTableElement.getNumberOfColumns();
								boTableElements.add(boTableElement);
							}
							else if(className.equalsIgnoreCase("com.lti.data.recast.bo.model.BOChartElement")){
								BOChartElement boChartElement = (BOChartElement) boElement;
								numberOfColumns += boChartElement.getNumberOfColumns();
								boChartElements.add(boChartElement);
							}
						}
					}
					if(boChartElements != null && boChartElements.size() > 0){
						boReportTab.setBoChartElements(boChartElements);
						numberOfBlocks += boChartElements.size();
						numberOfChartElements += boChartElements.size();
					}
					if(boTableElements != null && boTableElements.size() > 0){
						boReportTab.setBoTableElements(boTableElements);
						numberOfBlocks += boTableElements.size();
						numberOfTableElements += boTableElements.size();
					}
				}
				boReportModel.setNumberOfImages(numberOfImages);
				
				Double reportElementsComplexity = fetchReportElementsComplexity(boReportModel);
				boReportModel.setColumnNameList(columnNameList);
				boReportModel.setNumberOfColumns(numberOfColumns);
				boReportModel.setNumberOfBlocks(numberOfBlocks);
				boReportModel.setNumberOfTabs(numberOfTabs);
				boReportModel.setNumberOfFilters(numberOfFilters);
				Map<String, String> dataProviderIdNameMap = fetchListOfDataProviders(documentId, universeIdSet, boReportModel);
				if (dataProviderIdNameMap != null) {
					totalUniverseCount = Integer.parseInt(dataProviderIdNameMap.remove("totalUniverseCount"));
				}
				boReportModel.setTotalUniverseCount(totalUniverseCount);
					
				if(dataProviderIdNameMap != null && dataProviderIdNameMap.size() > 0) {				
					List<BOReportQuery> boReportQueries = new ArrayList<BOReportQuery>();
					List<BOQueryFilter> boQueryFilterList = new ArrayList<BOQueryFilter>();
					for(String dataProviderId : dataProviderIdNameMap.keySet()){
						BOReportQuery boReportQuery = fetchDataProviderData(documentId, dataProviderId, boReportModel);						
						if(boReportQuery != null){
							
							numberOfObjects+=fetchNumberOfObjects(documentId, dataProviderId, boReportModel);
							numberOfFormulas += boReportQuery.getNumberOfFormulas();
							numberOfQuery++;
							boReportQueries.add(boReportQuery);
						}
						BOQueryFilter boQueryFilter = new BOQueryFilter();
						boQueryFilter = fetchBoQueryFilterList(documentId,dataProviderId);
						if(boQueryFilter != null)
						{
						boQueryFilterList.add(boQueryFilter);	
						}
					}
					boReportModel.setBoQueryFilterList(boQueryFilterList);
					boReportModel.setBoReportQueries(boReportQueries);
					boReportModel.setBoVariableList(fetchVariables(documentId, boReportModel));
					
					List<String> dimensionMeasureList = fetchDimensionMeasureAttributeList(boReportQueries);
					
					String dimensionList = dimensionMeasureList.get(0);
					
					String measureList = dimensionMeasureList.get(1);
					
					String attributeList = dimensionMeasureList.get(2);
					
					String variableList = fetchCommonalityVariableList(boReportModel.getBoVariableList());
							
					for(int j = 0;j<boReportQueries.size();j++)
					{
						BOReportQuery boreportQuery = boReportQueries.get(j);
						List<String> queryStatements = boreportQuery.getQueryStatements();
						for(int k = 0;k<queryStatements.size();k++)
						{
							if(queryStatements.get(k)!=null)
							{
								String boQuery = queryStatements.get(k);
								HashMap<String,ArrayList<String>> selecttableMap = fetchSelectColumns(boQuery);
								HashMap<String,String> columnAliasMap = fetchColumnAlias(boQuery);
								
								for (String tablename : selecttableMap.keySet())
						        {
						            
						            ArrayList<String> columnList = selecttableMap.get(tablename);
						            
						            for(int l =0;l<columnList.size();l++)
						            {
						            	BOCommonalityParams boCommon = new BOCommonalityParams();
						            	boCommon.setReportId(documentId);
										boCommon.setReportName(documentIdNameMap.get(documentId));
										boCommon.setDataSourceName(boreportQuery.getDataSourceName());
										boCommon.setTableName(tablename);
										boCommon.setColumnName(columnList.get(l));
										boCommon.setColumnType("select");
										boCommon.setDimensionList(dimensionList);
										boCommon.setMeasureList(measureList);
										boCommon.setVariableList(variableList);
										boCommon.setAttributeList(attributeList);
										if(columnAliasMap.containsKey(columnList.get(l).trim()))
										{
											boCommon.setColumnAlias(columnAliasMap.get(columnList.get(l)));
										}
										boCommonalityParamsList.add(boCommon);
						            }
									
						           
						        }
								
								HashMap<String,ArrayList<String>> wheretableMap = fetchWhereColumns(boQuery);
								
								for (String tablename : wheretableMap.keySet())
						        {
						            
						            ArrayList<String> wherecolumnList = wheretableMap.get(tablename);
						            
						            for(int l =0;l<wherecolumnList.size();l++)
						            {
						            	BOCommonalityParams bowhereCommon = new BOCommonalityParams();
						            	bowhereCommon.setReportId(documentId);
						            	bowhereCommon.setReportName(documentIdNameMap.get(documentId));
						            	bowhereCommon.setDataSourceName(boreportQuery.getDataSourceName());
						            	bowhereCommon.setTableName(tablename);
						            	bowhereCommon.setColumnName(wherecolumnList.get(l));
						            	bowhereCommon.setColumnType("where");
						            	bowhereCommon.setDimensionList(dimensionList);
										bowhereCommon.setMeasureList(measureList);
										bowhereCommon.setVariableList(variableList);
										bowhereCommon.setAttributeList(attributeList);
										boCommonalityParamsList.add(bowhereCommon);
						            }
						           
						        }
								
								HashMap<String,ArrayList<String>> groupBytableMap = fetchGroupByColumns(boQuery);
								
								for (String tablename : groupBytableMap.keySet())
						        {
						            
						            ArrayList<String> groupBycolumnList = groupBytableMap.get(tablename);
						            
						            for(int l =0;l<groupBycolumnList.size();l++)
						            {
						            	BOCommonalityParams bowhereCommon = new BOCommonalityParams();
						            	bowhereCommon.setReportId(documentId);
						            	bowhereCommon.setReportName(documentIdNameMap.get(documentId));
						            	bowhereCommon.setDataSourceName(boreportQuery.getDataSourceName());
						            	bowhereCommon.setTableName(tablename);
						            	bowhereCommon.setColumnName(groupBycolumnList.get(l));
						            	bowhereCommon.setColumnType("groupby");
						            	bowhereCommon.setDimensionList(dimensionList);
										bowhereCommon.setMeasureList(measureList);
										bowhereCommon.setVariableList(variableList);
										bowhereCommon.setAttributeList(attributeList);
										boCommonalityParamsList.add(bowhereCommon);
						            }
									
						           
						           
						        }
							}
							
						}
					}
					
				}
				
				
				boReportModel.setNumberOfEmbeddedElements(numberOfEmbeddedElements.get());
				Double queryComplexity = fetchQueryComplexity(boReportModel);
				Double embeddedElementsComplexity = new Double(((0.3 * boReportModel.getNumberOfEmbeddedElements()) + (0.1*numberOfImages))/2);
				Double complexity = new Double(((0.3*queryComplexity) + (0.3*reportElementsComplexity) + (0.4*embeddedElementsComplexity))*100);
				boReportModel.setReportComplexity(complexity);
				boReportModel.setNumberOfFormulas(numberOfFormulas);
				boReportModel.setNumberOfQuery(numberOfQuery);
				Integer commonalityFactor = 0;
				if(numberOfFilters > 0)
					commonalityFactor++;
				if(numberOfQuery > 0)
					commonalityFactor++;
				if(numberOfChartElements > 0)
					commonalityFactor++;
				if(numberOfTableElements > 0)
					commonalityFactor++;
				boReportModel.setCommonalityFactor(commonalityFactor);
				
				
				
				System.out.println("BO Report Data::"+boReportModel.getBoReportTabList());
				System.out.println("BO Report Tab List JSON::"+boReportModel.getBOReportTabListJSON());
				
				List<BOVariable> boVariableList = fetchVariables(documentId, boReportModel);
				boReportModel.setInputControlList(fetchInputControlList(documentId,boReportModel));
				boReportModel.setAlertersList(fetchAlertersList(documentId,boReportModel));
				boReportModel.setDataFiltersList(fetchDataFiltersList(documentId,boReportModel));
				
				List<BOComplexityParameters> boComplexityList = new ArrayList<BOComplexityParameters>();
				
				BOComplexityParameters boReportComplexity = new BOComplexityParameters();
				boReportComplexity.setNumberOfDataSources(totalUniverseCount);
				boReportComplexity.setNumberOfQueries(numberOfQuery);
				boReportComplexity.setNumberOfVariables(boVariableList.size());
				boReportComplexity.setNumberOfReportTabs(numberOfTabs);
				boReportComplexity.setNumberOfReportElements(numberOfReportElements);
				boReportComplexity.setNumberOfObjects(numberOfObjects);
				boComplexityList.add(boReportComplexity);
				boReportModel.setComplexityList(boComplexityList);
				boReportComplexityList.add(boReportComplexity);	

				
				boReportModelList.add(boReportModel);
				
				
				//Fetch the data for Visualization
				
				BOVisualizationModel boVisualizationModel = fetchVisualizationDetailsForReport(documentId);
				boVisualizationModel.setReportId(documentId);
				boVisualizationModel.setReportName(boReportModel.getReportName());
				
				boVisualizationList.add(boVisualizationModel);
				
				
				
				//boVisualizationMap.put(documentId, boVisualizationModel);
				
				//boVisualizationList.add(boVisualizationMap);
			}
			for (int id: universeIdSet) {
				BOUniverse universe = fetchUniverseData(id, config);
				
					boUniverseList.add(universe);		
			}
			
			CheckUseService.checkUse(boReportModelList, boUniverseList);
			analyzerData.put("reports", boReportModelList);
			analyzerData.put("universes", boUniverseList);
			analyzerData.put("complexity", boReportComplexityList);
			analyzerData.put("visualization", boVisualizationList);
			analyzerData.put("commonality",boCommonalityParamsList);
		}
		catch(Exception ex){
			ex.printStackTrace();
			logger.error(ex);
		}
		finally{
			logOut();
			webClient.logOut();
		}
		return analyzerData;
	}
	
	
	



	public void fetchListOfUniverses(Integer limit, Integer offset){
		try {
			StringBuilder output = new StringBuilder();
			URL url = new URL("http://"+ CMSSERVERIP +":"+ BO_WS_PORT + "/biprws/raylight/v1/universes?offset=" + offset + "&limit=" + limit);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(REQUEST_METHOD_GET);
			conn.setRequestProperty(REQUEST_PROPERTY_CONTENT_TYPE, REQUEST_PROPERTY_APPL_XML);
			conn.setRequestProperty(REQUEST_PROPERTY_ACCEPT, REQUEST_PROPERTY_APPL_XML);
			conn.setRequestProperty(REQUEST_PROPERTY_X_SAP_LOGONTOKEN, REQUEST_PROPERTY_X_SAP_LOGONTOKEN_VALUE);
			
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			
			String temp;
			while ((temp = br.readLine()) != null) {
				System.out.println(temp);
				output.append(temp);
			}
			if(conn != null){
				conn.disconnect();
				br.close();
			}
		} catch(Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
		}
	}

	public void fetchNumberOfLinkGroups(Integer universeId){
		try {
			StringBuilder output = new StringBuilder();
			URL url = new URL("http://"+ CMSSERVERIP +":"+ BO_WS_PORT + "/biprws/raylight/v1/universes/" + universeId + "/linkgroups");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(REQUEST_METHOD_GET);
			conn.setRequestProperty(REQUEST_PROPERTY_CONTENT_TYPE, REQUEST_PROPERTY_APPL_XML);
			conn.setRequestProperty(REQUEST_PROPERTY_ACCEPT, REQUEST_PROPERTY_APPL_XML);
			conn.setRequestProperty(REQUEST_PROPERTY_X_SAP_LOGONTOKEN, REQUEST_PROPERTY_X_SAP_LOGONTOKEN_VALUE);
			
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			
			String temp;
			while ((temp = br.readLine()) != null) {
				System.out.println(temp);
				output.append(temp);
			}
			if(conn != null){
				conn.disconnect();
				br.close();
			}
		} catch(Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
		}
	}
	
	public void fetchNumberOfPrompts(Integer universeId){
		try {
			StringBuilder output = new StringBuilder();
			URL url = new URL("http://"+ CMSSERVERIP +":"+ BO_WS_PORT + "/biprws/raylight/v1/universes/" + universeId + "/prompts");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(REQUEST_METHOD_GET);
			conn.setRequestProperty(REQUEST_PROPERTY_CONTENT_TYPE, REQUEST_PROPERTY_APPL_XML);
			conn.setRequestProperty(REQUEST_PROPERTY_ACCEPT, REQUEST_PROPERTY_APPL_XML);
			conn.setRequestProperty(REQUEST_PROPERTY_X_SAP_LOGONTOKEN, REQUEST_PROPERTY_X_SAP_LOGONTOKEN_VALUE);
			
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			
			String temp;
			while ((temp = br.readLine()) != null) {
				System.out.println(temp);
				output.append(temp);
			}
			if(conn != null){
				conn.disconnect();
				br.close();
			}
		} catch(Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
		}
	}
	
	public BOAnalyzerModel fetchNumberOfSchedules(Integer documentId, BOAnalyzerModel boReportModel) {
		
		int numberOfSchedules = 0;
		int numberOfRecurringInstances = 0;
		try {
			StringBuilder output = new StringBuilder();
			URL url = new URL("http://"+ CMSSERVERIP +":"+ BO_WS_PORT + "/biprws/raylight/v1/documents/" + documentId + "/schedules");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(REQUEST_METHOD_GET);
			conn.setRequestProperty(REQUEST_PROPERTY_CONTENT_TYPE, REQUEST_PROPERTY_APPL_XML);
			conn.setRequestProperty(REQUEST_PROPERTY_ACCEPT, REQUEST_PROPERTY_APPL_JSON);
			conn.setRequestProperty(REQUEST_PROPERTY_X_SAP_LOGONTOKEN, REQUEST_PROPERTY_X_SAP_LOGONTOKEN_VALUE);
			
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			
			String temp;
			while ((temp = br.readLine()) != null) {
				output.append(temp);
//				System.out.println(temp);
			}
			if(output != null && !output.toString().trim().isEmpty()){
				byte[] jsonOutput = output.toString().getBytes();
				//create ObjectMapper instance
				ObjectMapper objectMapper = new ObjectMapper();
	
				//read JSON like DOM Parser
				JsonNode rootNode = objectMapper.readTree(jsonOutput);
				JsonNode schedulesNode = rootNode.path("schedules");
				JsonNode scheduleNode = schedulesNode.path("schedule");
				if(!scheduleNode.isMissingNode()){
					if(scheduleNode.isArray()){
						Iterator<JsonNode> elements = scheduleNode.elements();
						while(elements.hasNext()){
							JsonNode schedule = elements.next();
							String scheduleId = schedule.get("id").asText();
							numberOfRecurringInstances = fetchNumberOfRecurringInstances(documentId, scheduleId, boReportModel);
							JsonNode statusNode = schedule.path("status");
							if(!statusNode.isMissingNode()){
								//status 0-Running, 1-Completed, 3-Failed, 8-Paused, 9-Pending
								if(statusNode.get("@id") != null && statusNode.get("@id").asText().equalsIgnoreCase("3")){
									boReportModel.setNumberOfFailures(boReportModel.getNumberOfFailures() + 1);
								}
							}
							numberOfSchedules++;
						}
					}
					else{
						numberOfRecurringInstances = fetchNumberOfRecurringInstances(documentId, scheduleNode.get("id").asText(), boReportModel);
						JsonNode statusNode = scheduleNode.path("status");
						if(!statusNode.isMissingNode()){
							if(statusNode.get("@id") != null && statusNode.get("@id").asText().equalsIgnoreCase("3")){
								boReportModel.setNumberOfFailures(boReportModel.getNumberOfFailures() + 1);
							}
						}
						numberOfSchedules++;
					}
				}
			}
			if(conn != null){
				conn.disconnect();
				br.close();
			}
		} catch(Exception ex) {
			boReportModel.setExceptionReport(boReportModel.getExceptionReport().concat("\n" + ex + " in " + ex.getStackTrace()[0].getMethodName()) );
			logger.error(ex);
			ex.printStackTrace();
		}
		boReportModel.setNumberOfInstances(numberOfSchedules);
		boReportModel.setNumberOfRecurringInstances(numberOfRecurringInstances);
		return boReportModel;
	}

	public Integer fetchNumberOfRecurringInstances(Integer documentId, String scheduleId, BOAnalyzerModel boReportModel) {
		int numberOfRecurringInstances = 0;
		try {
			StringBuilder output = new StringBuilder();
			URL url = new URL("http://"+ CMSSERVERIP +":"+ BO_WS_PORT + "/biprws/raylight/v1/documents/" + documentId + "/schedules/" + scheduleId);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(REQUEST_METHOD_GET);
			conn.setRequestProperty(REQUEST_PROPERTY_CONTENT_TYPE, REQUEST_PROPERTY_APPL_XML);
			conn.setRequestProperty(REQUEST_PROPERTY_ACCEPT, REQUEST_PROPERTY_APPL_JSON);
			conn.setRequestProperty(REQUEST_PROPERTY_X_SAP_LOGONTOKEN, REQUEST_PROPERTY_X_SAP_LOGONTOKEN_VALUE);
			
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			
			String temp;
			while ((temp = br.readLine()) != null) {
				output.append(temp);
				System.out.println(temp);
			}
			if(output != null && !output.toString().trim().isEmpty()){
				byte[] jsonOutput = output.toString().getBytes();
				//create ObjectMapper instance
				ObjectMapper objectMapper = new ObjectMapper();
	
				//read JSON like DOM Parser
				JsonNode rootNode = objectMapper.readTree(jsonOutput);
				JsonNode scheduleNode = rootNode.path("schedule");
				if(!scheduleNode.isMissingNode()){
					List<String> recurringExpressionTags = new ArrayList<String>();
					recurringExpressionTags.add("once");
					recurringExpressionTags.add("daily");
					recurringExpressionTags.add("hourly");
					recurringExpressionTags.add("weekly");
					recurringExpressionTags.add("monthly");
					recurringExpressionTags.add("nthDayOfMonth");
					recurringExpressionTags.add("firstMondayOfMonth");
					recurringExpressionTags.add("lastDayOfMonth");
					recurringExpressionTags.add("xDayOfNthWeekOfTheMonth");
					recurringExpressionTags.add("calendar");
					Iterator<String> iterator = scheduleNode.fieldNames(); 
					while(iterator.hasNext()){
						String key = iterator.next();
						if(recurringExpressionTags.contains(key.trim())) {
							numberOfRecurringInstances++;
						}
					}
				}
			}
			if(conn != null){
				conn.disconnect();
				br.close();
			}
		} catch(Exception ex) {
			boReportModel.setExceptionReport(boReportModel.getExceptionReport().concat("\n" + ex + " in " + ex.getStackTrace()[0].getMethodName()) );
			logger.error(ex);
			ex.printStackTrace();
		}
		
		return numberOfRecurringInstances;
	}

	public Double fetchQueryComplexity(BOAnalyzerModel boReportModel) {
		
		Double queryComplexity = new Double(0);
		try {
		Integer numberOfColumns = 0;
		Integer numberOfFilters = 0;
		Integer numberOfPrompts = 0;
		Integer numberOfComputedColumns = 0;
		for(BOReportTab boReportTab : boReportModel.getBoReportTabList()){
			if(boReportTab.getQueryFilters() != null && boReportTab.getQueryFilters().size() > 0){
				numberOfFilters += boReportTab.getQueryFilters().size();
			}
		}
		for(BOReportQuery boReportQuery : boReportModel.getBoReportQueries()){
			if(boReportQuery.getBoReportQueryColumns() != null && boReportQuery.getBoReportQueryColumns().size() > 0){
				numberOfColumns += boReportQuery.getBoReportQueryColumns().size();
			}
			numberOfPrompts += boReportQuery.getNumberOfPrompts();
			numberOfComputedColumns += boReportQuery.getNumberOfFormulas();
		}
		queryComplexity = new Double(((0.03*numberOfColumns) + (0.03*numberOfFilters) + (0.04*numberOfPrompts) + (0.1*numberOfComputedColumns))/4);
		}
		catch(Exception ex)
		{
			logger.error(ex);
		}
		return queryComplexity;
	}

	public Double fetchReportElementsComplexity(BOAnalyzerModel boReportModel) {
		Double reportElementsComplexity = new Double(0);
		try {
		Integer numberOfVTables = 0;
		Integer numberOfHTables = 0;
		Integer numberOfXTables = 0;
		Integer numberOfLineChart = 0;
		Integer numberOfBarChart = 0;
		Integer numberOfPieChart = 0;
		Integer numberOfCombinationalGraphs = 0;
		for(BOReportTab boReportTab : boReportModel.getBoReportTabList()){
			if(boReportTab.getBoTableElements() != null && boReportTab.getBoTableElements().size() > 0){
				for(BOTableElement boTableElement : boReportTab.getBoTableElements()){
					if("VTable".equalsIgnoreCase(boTableElement.getTableType())){
						numberOfVTables++;
					}else if("HTable".equalsIgnoreCase(boTableElement.getTableType()) || "Section".equalsIgnoreCase(boTableElement.getTableType())){
						numberOfHTables++;
					}else if("XTable".equalsIgnoreCase(boTableElement.getTableType())){
						numberOfXTables++;
					}
				}
			}
			if(boReportTab.getBoChartElements() != null && boReportTab.getBoChartElements().size() > 0){
				for(BOChartElement boChartElement : boReportTab.getBoChartElements()){
					if(boChartElement.getChartType().contains("Combined")){
						numberOfCombinationalGraphs++;
					}else if(boChartElement.getChartType().contains("Bar")){
						numberOfBarChart++;
					}else if(boChartElement.getChartType().contains("Line")){
						numberOfLineChart++;
					}else if(boChartElement.getChartType().contains("Pie") || boChartElement.getChartType().contains("Doughnut")){
						numberOfPieChart++;
					}
				}
			}
		}
		reportElementsComplexity = new Double(((0.03*numberOfVTables) + (0.03*numberOfHTables) + (0.04*numberOfXTables) + 
				(0.03*numberOfLineChart) + (0.04*numberOfBarChart) + (0.03*numberOfPieChart) + (0.1*numberOfCombinationalGraphs))/7);
		}
		catch(Exception ex)
		{
			logger.error(ex);
		}
		return reportElementsComplexity;
	}

	public String logonAndGetToken(String host, String username, String password) throws MalformedURLException,IOException {
		
		StringBuilder output = new StringBuilder("");
		
		try {
			URL url = new URL("http://"+ host +":"+ BO_WS_PORT +"/biprws/logon/long");
			logger.info("URL:"+url);
			System.out.println("URL:"+url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod(REQUEST_METHOD_POST);
			conn.setRequestProperty(REQUEST_PROPERTY_CONTENT_TYPE, REQUEST_PROPERTY_APPL_XML);
			conn.setRequestProperty(REQUEST_PROPERTY_ACCEPT,REQUEST_PROPERTY_APPL_XML);
			String input = "<attrs xmlns=\"http://www.sap.com/rws/bip\">"
					+ "<attr name=\"userName\" type=\"string\">" + username
							+ "</attr><attr name=\"password\" type=\"string\">" + password
									+ "</attr><attr name=\"auth\" type=\"string\" possibilities=\"secEnterprise,secLDAP,secWinAD,secSAPR3\">"
											+ BOAUTH
											+ "</attr>"
											+ "</attrs>";
			
			System.out.println("Input:"+input);
			
			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();
	
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
	
			String tempReadLine = br.readLine();
			while ( tempReadLine != null) {
				output.append(tempReadLine);
				tempReadLine = br.readLine();
			}
			if(output.toString().equals("")){
				return null;
			}
			String strToken = output.toString().substring(output.toString().indexOf("logonToken") + 26, output.toString().indexOf("</attr>"));
			String strOutput = strToken.toString().replaceAll("amp;", "");
			output.setLength(0);
			output.append(strOutput);
			
			if(conn != null){
				conn.disconnect();
				br.close();
			}
		} catch (Exception ex) {
			logger.error(ex);
		}
		System.out.println("token:"+output.toString());
		return output.toString();
	//	return "\""+output.toString()+"\"";
	}
	
	public void logOut(){
		System.out.println("Log off called");
		try {
			
			URL url = new URL("http://"+ CMSSERVERIP +":"+ BO_WS_PORT + "/biprws/logoff");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(REQUEST_METHOD_POST);
			conn.setRequestProperty(REQUEST_PROPERTY_CONTENT_TYPE, REQUEST_PROPERTY_APPL_XML);
			conn.setRequestProperty(REQUEST_PROPERTY_ACCEPT, REQUEST_PROPERTY_APPL_XML);
			conn.setRequestProperty(REQUEST_PROPERTY_X_SAP_LOGONTOKEN, REQUEST_PROPERTY_X_SAP_LOGONTOKEN_VALUE);
			
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			
			String output;
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
			if(conn != null){
				conn.disconnect();
				br.close();
			}
		} catch(Exception ex) {
			logger.error(ex);
		}
	}
	
	public Map<Integer, String> fetchListOfDocuments(Integer limit, Integer offset, Map<Integer, String> documentIdNameMap){
		try {
			String uri = "/documents?offset=" + offset + "&limit=" + limit;
			String output = webClient.get(uri, REQUEST_PROPERTY_APPL_XML, REQUEST_PROPERTY_APPL_JSON);
			byte[] jsonOutput = output.getBytes();
			//create ObjectMapper instance
			ObjectMapper objectMapper = new ObjectMapper();

			//read JSON like DOM Parser
			JsonNode rootNode = objectMapper.readTree(jsonOutput);
			JsonNode documentsNode = rootNode.path("documents");
			JsonNode documentNode = documentsNode.path("document");
			if(documentNode.isArray()){
				Iterator<JsonNode> elements = documentNode.elements();
				Map<Integer, String> documentIdNameMapTemp = new HashMap<Integer, String>();
				while(elements.hasNext()){
					JsonNode document = elements.next();
					documentIdNameMapTemp.put(document.get("id").asInt(), document.get("name").asText());
	//				System.out.println(document.get("id").asInt());
	//				System.out.println(document.get("name").asText());
				}
				documentIdNameMap.putAll(documentIdNameMapTemp);
				if(documentIdNameMapTemp.size() == limit){
					offset += limit;
					documentIdNameMap = fetchListOfDocuments(limit, offset, documentIdNameMap);
				}
			}
			else{
				System.out.println("key: "+documentNode.get("id").asInt());
				System.out.println("value:"+ documentNode.get("name").asText());
				documentIdNameMap.put(documentNode.get("id").asInt(), documentNode.get("name").asText());
			}

		} catch(Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
		}
		System.out.println("Document Id Map::"+documentIdNameMap);
		return documentIdNameMap;
	}
	
	public BOAnalyzerModel fetchDocumentData(Integer documentId, BOAnalyzerModel boReportModel){
		try {
			StringBuilder output = new StringBuilder();
			URL url = new URL("http://"+ CMSSERVERIP +":"+ BO_WS_PORT + "/biprws/raylight/v1/documents/" + documentId);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(REQUEST_METHOD_GET);
			conn.setRequestProperty(REQUEST_PROPERTY_CONTENT_TYPE, REQUEST_PROPERTY_APPL_XML);
			conn.setRequestProperty(REQUEST_PROPERTY_ACCEPT, REQUEST_PROPERTY_APPL_JSON);
			conn.setRequestProperty(REQUEST_PROPERTY_X_SAP_LOGONTOKEN, REQUEST_PROPERTY_X_SAP_LOGONTOKEN_VALUE);
			
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			
			String temp;
			while ((temp = br.readLine()) != null) {
//				System.out.println(temp);
				output.append(temp);
			}
			byte[] jsonOutput = output.toString().getBytes();
			//create ObjectMapper instance
			ObjectMapper objectMapper = new ObjectMapper();

			//read JSON like DOM Parser
			JsonNode rootNode = objectMapper.readTree(jsonOutput);
			JsonNode documentNode = rootNode.path("document");
			
			
			String path = documentNode.get("path") != null ? documentNode.get("path").asText() + "/" + boReportModel.getReportName() : "NOT AVAILABLE";
			double size = documentNode.get("size") != null ?  documentNode.get("size").asDouble() : -1;
			String date = documentNode.get("updated") != null ? documentNode.get("updated").asText() : "1900-01-01";
			boolean scheduled = documentNode.get("scheduled") != null ? documentNode.get("scheduled").asBoolean() : false;
			String createdBy = documentNode.get("createdBy") != null ? documentNode.get("createdBy").asText() : "NOT AVAILABLE";
			
			
			boReportModel.setReportFolderPath(path);
			boReportModel.setReportSize(size);
			String updatedDate = convertReportDate(date);
			
			boReportModel.setReportUpdatedDate(updatedDate);
			boReportModel.setIsReportScheduled(scheduled);
			boReportModel.setReportUser(createdBy);
			
			String dateInString = date;
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
			Date dateOld = simpleDateFormat.parse(dateInString);
			
			long diff =  new Date().getTime()- dateOld.getTime();
		    long diffInDays = diff / (24 * 60 * 60 * 1000);
		    if(diffInDays > 30.0){
		    	boReportModel.setActivelyUsed(false);
			}else{
				boReportModel.setActivelyUsed(true);
			}
			
			if(conn != null){
				conn.disconnect();
				br.close();
			}
		} 
		catch(Exception ex) {
			boReportModel.setExceptionReport(boReportModel.getExceptionReport().concat("\n" + ex + " in " + ex.getStackTrace()[0].getMethodName() + " in " + ex.getStackTrace()[0].getMethodName()));
			logger.error(ex);
		}
		return boReportModel;
	}
	
	public List<BOReportTab> fetchListOfReports(Integer documentId, BOAnalyzerModel boReportModel){
		List<BOReportTab> boReportTabList = new ArrayList<BOReportTab>();
		try {
			System.out.println("Document id:"+documentId);
			String uri = "/documents/" + documentId + "/reports";
			String output = webClient.get(uri, REQUEST_PROPERTY_APPL_XML, REQUEST_PROPERTY_APPL_JSON);
			byte[] jsonOutput = output.getBytes();
			//create ObjectMapper instance
			ObjectMapper objectMapper = new ObjectMapper();

			//read JSON like DOM Parser
			JsonNode rootNode = objectMapper.readTree(jsonOutput);
			JsonNode reportsNode = rootNode.path("reports");
			JsonNode reportNode = reportsNode.path("report");
			if(reportNode.isArray()){
				Iterator<JsonNode> elements = reportNode.elements();
				while(elements.hasNext()){
					JsonNode report = elements.next();
					BOReportTab boReportTab = new BOReportTab();
					boReportTab.setReportTabId(report.get("id").asInt());
					boReportTab.setReportTabName(report.get("name").asText());
					boReportTabList.add(boReportTab);
				}
			}
			else {
				BOReportTab boReportTab = new BOReportTab();
				boReportTab.setReportTabId(reportNode.get("id").asInt());
				boReportTab.setReportTabName(reportNode.get("name").asText());
				boReportTabList.add(boReportTab);
			}
		} catch(Exception ex) {
			boReportModel.setExceptionReport(boReportModel.getExceptionReport().concat("\n" + ex + " in " + ex.getStackTrace()[0].getMethodName()) );
			logger.error(ex);
			ex.printStackTrace();
		}
		return boReportTabList;
	}
	
	public Map<String, String> fetchListOfDataProviders(Integer documentId, Set<Integer> universeIdSet, BOAnalyzerModel boReportModel){
		Map<String, String> dataProviderIdNameMap = new HashMap<String, String>();
		
		System.out.println("Document Id::"+documentId);
		try {
			String uri = "/documents/" + documentId + "/dataproviders";
			String output = webClient.get(uri, REQUEST_PROPERTY_APPL_XML, REQUEST_PROPERTY_APPL_JSON);
			
		//	System.out.println("Data Providers::"+output);
			
			byte[] jsonOutput = output.toString().getBytes();
			//create ObjectMapper instance
			ObjectMapper objectMapper = new ObjectMapper();

			//read JSON like DOM Parser
			JsonNode rootNode = objectMapper.readTree(jsonOutput);
			JsonNode dataprovidersNode = rootNode.path("dataproviders");
			JsonNode dataproviderNode = dataprovidersNode.path("dataprovider");
			Set<String> dataSourceIdList = new HashSet<String>();
			if(!dataproviderNode.isMissingNode()){
				if(dataproviderNode.isArray()){
					Iterator<JsonNode> elements = dataproviderNode.elements();
					while(elements.hasNext()){
						JsonNode dataProvider = elements.next();
						dataProviderIdNameMap.put(dataProvider.get("id").asText(), dataProvider.get("name").asText());
						if(dataProvider.get("dataSourceType") != null){
							String dataSourceType = dataProvider.get("dataSourceType").asText();
							if(dataSourceType.equalsIgnoreCase("unx") || dataSourceType.equalsIgnoreCase("unv")){
								if(dataProvider.get("dataSourceId") != null) {
									dataSourceIdList.add(dataProvider.get("dataSourceId").asText());
									universeIdSet.add(Integer.parseInt(dataProvider.get("dataSourceId").asText()));
									
								}						
							}
						}
					}
				}
				else {
					dataProviderIdNameMap.put(dataproviderNode.get("id").asText(), dataproviderNode.get("name").asText());
					if(dataproviderNode.get("dataSourceType") != null) {
						String dataSourceType = dataproviderNode.get("dataSourceType").asText();
						if(dataSourceType.equalsIgnoreCase("unx") || dataSourceType.equalsIgnoreCase("unv")){
							if(dataproviderNode.get("dataSourceId") != null) {
								dataSourceIdList.add(dataproviderNode.get("dataSourceId").asText());
								universeIdSet.add(Integer.parseInt(dataproviderNode.get("dataSourceId").asText()));
								logger.info("Found");
							}
								
						}
					}
				}
			}
			dataProviderIdNameMap.put("totalUniverseCount", Integer.toString(dataSourceIdList.size()));
		} catch(Exception ex) {
			boReportModel.setExceptionReport(boReportModel.getExceptionReport().concat("\n" + ex + " in " + ex.getStackTrace()[0].getMethodName()) );
			logger.error(ex);
			ex.printStackTrace();
		}
		return dataProviderIdNameMap;
	}
	
	public BOReportQuery fetchDataProviderData(Integer documentId, String dataProviderId, BOAnalyzerModel boReportModel){
		
		System.out.println("Data provider ID:"+dataProviderId);
		BOReportQuery boReportQuery = null;
		try {
			String uri = "/documents/" + documentId + "/dataproviders/" + dataProviderId;
			String output = webClient.get(uri, REQUEST_PROPERTY_APPL_XML, REQUEST_PROPERTY_APPL_JSON);
			System.out.println("Data PRovider Query Details::"+output);
			byte[] jsonOutput = output.getBytes();
			//create ObjectMapper instance
			ObjectMapper objectMapper = new ObjectMapper();

			//read JSON like DOM Parser
			JsonNode rootNode = objectMapper.readTree(jsonOutput);
			JsonNode dataproviderNode = rootNode.path("dataprovider");
			boReportModel.getUniverses().add(dataproviderNode.get("dataSourceName").asText());
			//boReportModel.setTotalUniverseCount(boReportModel.getTotalUniverseCount() + 1);
			boReportQuery = new BOReportQuery();
			boReportQuery.setQueryId(dataProviderId);
			boReportQuery.setQueryName(dataproviderNode.get("name").asText());
			
			boReportQuery.setDataSourceId(dataproviderNode.get("dataSourceId").asText());
			boReportQuery.setDataSourceName(dataproviderNode.get("dataSourceName").asText());
			boReportQuery.setDataSourceType(dataproviderNode.get("dataSourceType").asText());
			
			if(dataproviderNode.get("query") != null){
				
				System.out.println("Query:::"+dataproviderNode.get("query").asText());
				
				boReportQuery.setQuery(dataproviderNode.get("query").asText());
				boReportQuery.setNumberOfPrompts(StringUtils.countMatches(boReportQuery.getQuery(), "@Prompt("));
			}else{
				boReportQuery.setNumberOfPrompts(0);
			}
			JsonNode dictionaryNode = dataproviderNode.path("dictionary");
			JsonNode expressionNode = dictionaryNode.path("expression");
			List<BOReportQueryColumn> boReportQueryColumns = new ArrayList<BOReportQueryColumn>();
			List<BOReportObject> boReportObjectList = new ArrayList<BOReportObject>();
			Integer numberOfFormulas = 0;
			if(expressionNode.isArray()){
				Iterator<JsonNode> elements = expressionNode.elements();
				while(elements.hasNext()){
					JsonNode expression = elements.next();
					BOReportQueryColumn boReportQueryColumn = new BOReportQueryColumn();
					boReportQueryColumn.setColumnId(expression.get("id").asText());
					boReportQueryColumn.setColumnName(expression.get("name").asText());
					boReportQueryColumn.setColumnExpression(expression.get("formulaLanguageId").asText());
					if(expression.get("description") != null)
						boReportQueryColumn.setColumnDescription(expression.get("description").asText());
					if(expression.get("aggregationFunction") != null && !expression.get("aggregationFunction").asText().equalsIgnoreCase("None"))
						boReportQueryColumn.setAggregateFunction(expression.get("aggregationFunction").asText());
					if(boReportQueryColumn.getAggregateFunction() != null && !boReportQueryColumn.getAggregateFunction().trim().equalsIgnoreCase("None"))
						numberOfFormulas++;
					boReportQueryColumn.setColumnDataType(expression.get("@dataType").asText());
					boReportQueryColumn.setColumnQualification(expression.get("@qualification").asText());
					boReportQueryColumns.add(boReportQueryColumn);
					
					BOReportObject boReportObj = new BOReportObject();
					
					boReportObj.setDataProviderObjectId(expression.get("id").asText());
					boReportObj.setDataProviderObjectName(expression.get("name").asText());
					
					if(expression.get("dataSourceObjectId")!=null)
					{
						boReportObj.setDataSourceObjectId(expression.get("dataSourceObjectId").asText());
					}
					
					
					boReportObj.setFormulaLanguageId(expression.get("formulaLanguageId").asText());
					boReportObjectList.add(boReportObj);

				}
			}
			else {
				BOReportQueryColumn boReportQueryColumn = new BOReportQueryColumn();
				boReportQueryColumn.setColumnId(expressionNode.get("id").asText());
				boReportQueryColumn.setColumnName(expressionNode.get("name").asText());
				boReportQueryColumn.setColumnExpression(expressionNode.get("formulaLanguageId").asText());
				if(expressionNode.get("description") != null)
					boReportQueryColumn.setColumnDescription(expressionNode.get("description").asText());
				if(expressionNode.get("aggregationFunction") != null && !expressionNode.get("aggregationFunction").asText().equalsIgnoreCase("None"))
					boReportQueryColumn.setAggregateFunction(expressionNode.get("aggregationFunction").asText());
				if(boReportQueryColumn.getAggregateFunction() != null && !boReportQueryColumn.getAggregateFunction().trim().equalsIgnoreCase("None"))
					numberOfFormulas++;
				boReportQueryColumn.setColumnDataType(expressionNode.get("@dataType").asText());
				boReportQueryColumn.setColumnQualification(expressionNode.get("@qualification").asText());
				boReportQueryColumns.add(boReportQueryColumn);
				
				BOReportObject boReportObj = new BOReportObject();
				
				boReportObj.setDataProviderObjectId(expressionNode.get("id").asText());
				boReportObj.setDataProviderObjectName(expressionNode.get("name").asText());
				
				if(expressionNode.get("dataSourceObjectId")!=null)
				{
					boReportObj.setDataSourceObjectId(expressionNode.get("dataSourceObjectId").asText());
				}
				
				boReportObj.setFormulaLanguageId(expressionNode.get("formulaLanguageId").asText());
				boReportObjectList.add(boReportObj);
			}
			boReportQuery.setNumberOfFormulas(numberOfFormulas);
			boReportQuery.setBoReportQueryColumns(boReportQueryColumns);
			boReportQuery.setBoReportObject(boReportObjectList);
			boReportQuery.setQueryStatements(fetchQueries(documentId, dataProviderId, boReportQuery, boReportModel));
		}
		
		catch(Exception ex) {
			boReportModel.setExceptionReport(boReportModel.getExceptionReport().concat("\n" + ex + " in " + ex.getStackTrace()[0].getMethodName()) );
			logger.error(ex);
			ex.printStackTrace();
		}
		return boReportQuery;
	}
	
	public List<String> fetchQueries(Integer documentId, String dataProviderId, BOReportQuery boReportQuery, BOAnalyzerModel boReportModel) {
		List<String> queries = new ArrayList<String>();
		String uri = "/documents/" + documentId + "/dataproviders/" + dataProviderId + "/queryplan";
		String out = webClient.get(uri, REQUEST_PROPERTY_APPL_XML, REQUEST_PROPERTY_APPL_XML);
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(out)));
			NodeList intersectionNode = doc.getElementsByTagName("intersect");
			NodeList minusNode = doc.getElementsByTagName("minus");
			NodeList nodeList = doc.getElementsByTagName("statement");
			for(int i = 0; i < nodeList.getLength(); i++) {
				Node n = nodeList.item(i);
				if(n.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) n;
					logger.info("Query: " + e.getChildNodes().item(0).getNodeValue());
					queries.add(e.getChildNodes().item(0).getNodeValue());
				}
			}
			if(intersectionNode.getLength()>0)
			{
				String intersection = "";
				for(int j=0;j<queries.size()-1;j++)
				{
					intersection += queries.get(j)+" INTERSECT ";
				}
				intersection += queries.get(queries.size()-1);
				queries.clear();
				queries.add(intersection);
			}
			else if(minusNode.getLength()>0)
			{
				String minus = "";
				for(int j=0;j<queries.size()-1;j++)
				{
					minus += queries.get(j)+" MINUS ";
				}
				minus += queries.get(queries.size()-1);
				queries.clear();
				queries.add(minus);
			}
			return queries;
		}
		catch (Exception ex) {
			boReportModel.setExceptionReport(boReportModel.getExceptionReport().concat("\n" + ex + " in " + ex.getStackTrace()[0].getMethodName()) );
			ex.printStackTrace();
			logger.error(ex);
			return queries;
		}
	}
	
	
	public void fetchReportDrill(int documentId, int reportId, AtomicInteger numberOfEmbeddedElements, BOAnalyzerModel boReportModel) {
		try {
			String uri = "/documents/" + documentId + "/reports/" + reportId + "/driller/hierarchies";
			String output = webClient.get(uri, REQUEST_PROPERTY_APPL_XML, REQUEST_PROPERTY_APPL_JSON);
			
			byte[] jsonOutput = output.getBytes();
			//create ObjectMapper instance
			ObjectMapper objectMapper = new ObjectMapper();

			//read JSON like DOM Parser
			JsonNode rootNode = objectMapper.readTree(jsonOutput);
			JsonNode hierarchiesNode = rootNode.path("hierarchies");
			JsonNode hierarchyNode = hierarchiesNode.path("hierarchy");
			if(hierarchyNode.isArray()){
				Iterator<JsonNode> hierarchyElements = hierarchyNode.elements();
				while(hierarchyElements.hasNext()){
					JsonNode drillelementsNode = hierarchyElements.next().path("drillelements");
					JsonNode drillelementNode = drillelementsNode.path("drillelement");
					if(drillelementNode.isArray()){
						Iterator<JsonNode> elements = drillelementNode.elements();
						while(elements.hasNext()){
							elements.next();
							numberOfEmbeddedElements.getAndIncrement();
						}
					}
					else{
						numberOfEmbeddedElements.getAndIncrement();
					}
				}
			}else{
				JsonNode drillelementsNode = hierarchyNode.path("drillelements");
				JsonNode drillelementNode = drillelementsNode.path("drillelement");
				if(drillelementNode.isArray()){
					Iterator<JsonNode> elements = drillelementNode.elements();
					while(elements.hasNext()){
						elements.next();
						numberOfEmbeddedElements.getAndIncrement();
					}
				}else{
					numberOfEmbeddedElements.getAndIncrement();
				}
			}

		}
		catch(Exception ex) {
			boReportModel.setExceptionReport(boReportModel.getExceptionReport().concat("\n" + ex + " in " + ex.getStackTrace()[0].getMethodName()) );
			logger.error(ex);
		}
	}
	public List<QueryFilter> fetchReportDataFilter(Integer documentId, Integer reportId, BOAnalyzerModel boReportModel){
		List<QueryFilter> queryFilters = new ArrayList<QueryFilter>();
		try {
			String uri = "/documents/" + documentId + "/reports/" + reportId + "/datafilter";
			String output = webClient.get(uri, REQUEST_PROPERTY_APPL_XML, REQUEST_PROPERTY_APPL_JSON);
			byte[] jsonOutput = output.getBytes();
			//create ObjectMapper instance
			ObjectMapper objectMapper = new ObjectMapper();

			//read JSON like DOM Parser
			JsonNode rootNode = objectMapper.readTree(jsonOutput);
			JsonNode elementsNode = rootNode.path("datafilter");
			JsonNode elementNode = elementsNode.path("condition");
			if(elementNode.isMissingNode()){
				Iterator<String> iterator = elementsNode.fieldNames(); 
				while(iterator.hasNext()){
					String key = iterator.next();
					elementNode = elementsNode.path(key).path("condition");
				}
			}
			if(elementNode.isArray()){
				Iterator<JsonNode> elements = elementNode.elements();
				while(elements.hasNext()){
					JsonNode element = elements.next();
					QueryFilter queryFilter = new QueryFilter();
					queryFilter.setFilterName(element.get("@key").asText());
					queryFilter.setFilterOperator(element.get("@operator").asText());
					queryFilter.setFilterValue(element.get("value").asText());
					queryFilters.add(queryFilter);
				}
			}else{
				QueryFilter queryFilter = new QueryFilter();
				queryFilter.setFilterName(elementNode.get("@key").asText());
				queryFilter.setFilterOperator(elementNode.get("@operator").asText());
				queryFilter.setFilterValue(elementNode.get("value").asText());
				queryFilters.add(queryFilter);
			}
		}
		catch(Exception ex) {
			boReportModel.setExceptionReport(boReportModel.getExceptionReport().concat("\n" + ex + " in " + ex.getStackTrace()[0].getMethodName()) );
			logger.error(ex);
			//ex.printStackTrace();
		}
		return queryFilters;
	}
	
	public List<Integer> fetchListOfReportElements(Integer documentId, Integer reportId, AtomicInteger numberOfEmbeddedElements, BOAnalyzerModel boReportModel){
		List<Integer> boReportElementIdList = new ArrayList<Integer>();
		try {
			String uri = "/documents/" + documentId + "/reports/" + reportId + "/elements";
			String output = webClient.get(uri, REQUEST_PROPERTY_APPL_XML, REQUEST_PROPERTY_APPL_JSON);
			
			byte[] jsonOutput = output.getBytes();
			//create ObjectMapper instance
			ObjectMapper objectMapper = new ObjectMapper();

			//read JSON like DOM Parser
			JsonNode rootNode = objectMapper.readTree(jsonOutput);
			JsonNode elementsNode = rootNode.path("elements");
			JsonNode elementNode = elementsNode.path("element");
			if(elementNode.isArray()){
				Iterator<JsonNode> elements = elementNode.elements();
				while(elements.hasNext()){
					JsonNode element = elements.next();
					if(EnumUtils.isValidEnum(BOReportTab.ReportElementType.class, element.get("@type").asText())){
						boReportElementIdList.add(element.get("id").asInt());
					}else if(element.get("@type").asText().equalsIgnoreCase("PageZone")){
						fetchNumberOfImages(documentId, reportId, element.get("id").asInt(), boReportModel);
					}else if(element.get("@type").asText().equalsIgnoreCase("Cell")){
						fetchNumberOfEmbeddedElements(documentId, reportId, element.get("id").asInt(), numberOfEmbeddedElements, boReportModel);
					}else if(!element.get("@type").asText().equalsIgnoreCase("Cell") && !element.get("@type").asText().equalsIgnoreCase("PageZone")){
						System.out.println("111111 " + element.get("@type").asText());
					}
				}
			}
			else{
				if(EnumUtils.isValidEnum(BOReportTab.ReportElementType.class, elementNode.get("@type").asText())){
					boReportElementIdList.add(elementNode.get("id").asInt());
				}else if(elementNode.get("@type").asText().equalsIgnoreCase("PageZone")){
					fetchNumberOfImages(documentId, reportId, elementNode.get("id").asInt(), boReportModel);
				}else if(elementNode.get("@type").asText().equalsIgnoreCase("Cell")){
					fetchNumberOfEmbeddedElements(documentId, reportId, elementNode.get("id").asInt(), numberOfEmbeddedElements, boReportModel);
				}else if(!elementNode.get("@type").asText().equalsIgnoreCase("Cell") && !elementNode.get("@type").asText().equalsIgnoreCase("PageZone")){
					System.out.println("111111 " + elementNode.get("@type").asText());
				}
			}
		} 
		catch(Exception ex) {
			boReportModel.setExceptionReport(boReportModel.getExceptionReport().concat("\n" + ex + " in " + ex.getStackTrace()[0].getMethodName()) );
			logger.error(ex);
			ex.printStackTrace();
		}
		return boReportElementIdList;
	}
	
	public int fetchNumberOfImages(Integer documentId, Integer reportId, Integer reportElementId, BOAnalyzerModel boReportModel) {
		int numberOfImages = 0;
		try {
			
			String uri = "/documents/" + documentId + "/reports/" + reportId + "/elements/" + reportElementId;
			String output = webClient.get(uri, REQUEST_PROPERTY_APPL_XML, REQUEST_PROPERTY_APPL_JSON);
			
			byte[] jsonOutput = output.getBytes();
			ObjectMapper objectMapper = new ObjectMapper();

			JsonNode rootNode = objectMapper.readTree(jsonOutput);
			JsonNode elementNode = rootNode.path("element");
			JsonNode imageNode = elementNode.path("style").path("background").path("image");
			if(!imageNode.isMissingNode()){
				numberOfImages++;
			}
		
		}
		catch(Exception ex) {
			boReportModel.setExceptionReport(boReportModel.getExceptionReport().concat("\n" + ex + " in " + ex.getStackTrace()[0].getMethodName()) );
			logger.error(ex);
			ex.printStackTrace();
		}
		return numberOfImages;
	}

	public void fetchNumberOfEmbeddedElements(Integer documentId, Integer reportId, Integer reportElementId, AtomicInteger numberOfEmbeddedElements, BOAnalyzerModel boReportModel) {
		try {
			String uri = "/documents/" + documentId + "/reports/" + reportId + "/elements/" + reportElementId;
			String output = webClient.get(uri, REQUEST_PROPERTY_APPL_XML, REQUEST_PROPERTY_APPL_JSON);
			
			byte[] jsonOutput = output.getBytes();
			ObjectMapper objectMapper = new ObjectMapper();

			JsonNode rootNode = objectMapper.readTree(jsonOutput);
			JsonNode elementNode = rootNode.path("element");
			JsonNode contentNode = elementNode.path("content");
			if(!contentNode.isMissingNode()){
				JsonNode expressionNode = contentNode.path("expression");
				if(!expressionNode.isMissingNode()){
					JsonNode formulaNode = expressionNode.path("formula");
					if(!formulaNode.isMissingNode()){
						if(formulaNode.get("@type") != null && formulaNode.get("@type").asText().trim().equalsIgnoreCase("HyperLink"))
							numberOfEmbeddedElements.getAndIncrement();
					}
				}
			}
		} catch(Exception ex) {
			boReportModel.setExceptionReport(boReportModel.getExceptionReport().concat("\n" + ex + " in " + ex.getStackTrace()[0].getMethodName()) );
			logger.error(ex);
			ex.printStackTrace();
		}
	}
	
	public Object fetchReportElementData(Integer documentId, Integer reportId, Integer reportElementId, List<String> columnNameList, BOAnalyzerModel boReportModel){
		Object object = null;
		try {

			String uri = "/documents/" + documentId + "/reports/" + reportId + "/elements/" + reportElementId;
			String output = webClient.get(uri, REQUEST_PROPERTY_APPL_XML, REQUEST_PROPERTY_APPL_JSON);
		
			byte[] jsonOutput = output.getBytes();
			//create ObjectMapper instance
			ObjectMapper objectMapper = new ObjectMapper();

			//read JSON like DOM Parser
			JsonNode rootNode = objectMapper.readTree(jsonOutput);
			JsonNode elementNode = rootNode.path("element");
			if(!elementNode.get("@type").asText().equalsIgnoreCase("Visualization")){
				BOTableElement boTableElement = new BOTableElement();
				if(elementNode.get("name") != null)
					boTableElement.setTableName(elementNode.get("name").asText());
				boTableElement.setTableType(elementNode.get("@type").asText());
				Integer numberOfColumns = 0;
				JsonNode contentNode = elementNode.path("content");
				JsonNode axesNode = contentNode.path("axes");
				JsonNode axisNode = axesNode.path("axis");
				if(axisNode.isArray()){
					Iterator<JsonNode> elements = axisNode.elements();
					while(elements.hasNext()){
						JsonNode axisElement = elements.next();
						JsonNode axisExpressions = axisElement.path("expressions").path("formula");
						if(!axisExpressions.isMissingNode()){
							List<BOReportColumn> columnList = new ArrayList<BOReportColumn>();
							if(axisExpressions.isArray()){
								Iterator<JsonNode> elementsTemp = axisExpressions.elements();
								while(elementsTemp.hasNext()){
									JsonNode axisExpression = elementsTemp.next();
									BOReportColumn boReportColumn = new BOReportColumn();
									boReportColumn.setColumnDataType(axisExpression.get("@dataType").asText());
									boReportColumn.setColumnExpression(axisExpression.get("$").asText());
									numberOfColumns++;
									columnNameList.add(boReportColumn.getColumnExpression());
									columnList.add(boReportColumn);
								}
							}else{
								BOReportColumn boReportColumn = new BOReportColumn();
								boReportColumn.setColumnDataType(axisExpressions.get("@dataType").asText());
								boReportColumn.setColumnExpression(axisExpressions.get("$").asText());
								numberOfColumns++;
								columnNameList.add(boReportColumn.getColumnExpression());
								columnList.add(boReportColumn);
							}
							String role = axisElement.get("@role").asText();
							if(role.equalsIgnoreCase("Row")){
								boTableElement.setRowAxis(columnList);
							}else if(role.equalsIgnoreCase("Column")){
								boTableElement.setColumnAxis(columnList);
							}else if(role.equalsIgnoreCase("Body")){
								boTableElement.setCornerAxis(columnList);
							}
						}
					}
				}else{
					JsonNode axisExpressions = axisNode.path("expressions").path("formula");
					List<BOReportColumn> columnList = new ArrayList<BOReportColumn>();
					if(!axisExpressions.isMissingNode()){
						if(axisExpressions.isArray()){
							Iterator<JsonNode> elementsTemp = axisExpressions.elements();
							while(elementsTemp.hasNext()){
								JsonNode axisExpression = elementsTemp.next();
								BOReportColumn boReportColumn = new BOReportColumn();
								boReportColumn.setColumnDataType(axisExpression.get("@dataType").asText());
								boReportColumn.setColumnExpression(axisExpression.get("$").asText());
								numberOfColumns++;
								columnNameList.add(boReportColumn.getColumnExpression());
								columnList.add(boReportColumn);
							}
						}else{
							BOReportColumn boReportColumn = new BOReportColumn();
							boReportColumn.setColumnDataType(axisExpressions.get("@dataType").asText());
							boReportColumn.setColumnExpression(axisExpressions.get("$").asText());
							numberOfColumns++;
							columnNameList.add(boReportColumn.getColumnExpression());
							columnList.add(boReportColumn);
						}
					}
					String role = axisNode.get("@role").asText();
					if(role.equalsIgnoreCase("Row")){
						boTableElement.setRowAxis(columnList);
					}else if(role.equalsIgnoreCase("Column")){
						boTableElement.setColumnAxis(columnList);
					}else if(role.equalsIgnoreCase("Body")){
						boTableElement.setCornerAxis(columnList);
					}
				}
				boTableElement.setNumberOfColumns(numberOfColumns);
				object = boTableElement;
			}else{
				JsonNode contentNode = elementNode.path("content");
				JsonNode contentAxesNode = elementNode.path("axes");
				Integer numberOfColumns = 0;
				Map<String, List<BOReportColumn>> roleAxisColumns = new HashMap<String, List<BOReportColumn>>();
				if(!contentAxesNode.isMissingNode()){
					JsonNode axisNode = contentAxesNode.path("axis");
					if(axisNode.isArray()){
						Iterator<JsonNode> elements = axisNode.elements();
						while(elements.hasNext()){
							JsonNode axisElement = elements.next();
							JsonNode axisExpressions = axisElement.path("expressions").path("formula");
							if(!axisExpressions.isMissingNode()){
								List<BOReportColumn> columnList = new ArrayList<BOReportColumn>();
								if(axisExpressions.isArray()){
									Iterator<JsonNode> elementsTemp = axisExpressions.elements();
									while(elementsTemp.hasNext()){
										JsonNode axisExpression = elementsTemp.next();
										BOReportColumn boReportColumn = new BOReportColumn();
										boReportColumn.setColumnDataType(axisExpression.get("@dataType").asText());
										boReportColumn.setColumnExpression(axisExpression.get("$").asText());
										numberOfColumns++;
										columnNameList.add(boReportColumn.getColumnExpression());
										columnList.add(boReportColumn);
									}
								}else{
									BOReportColumn boReportColumn = new BOReportColumn();
									boReportColumn.setColumnDataType(axisExpressions.get("@dataType").asText());
									boReportColumn.setColumnExpression(axisExpressions.get("$").asText());
									numberOfColumns++;
									columnNameList.add(boReportColumn.getColumnExpression());
									columnList.add(boReportColumn);
								}
//								String role = axisElement.get("@role").asText();
								roleAxisColumns.put(axisElement.get("@role").asText(), columnList);
							}
						}
					}else{
						JsonNode axisExpressions = axisNode.path("expressions").path("formula");
						List<BOReportColumn> columnList = new ArrayList<BOReportColumn>();
						if(!axisExpressions.isMissingNode()){
							if(axisExpressions.isArray()){
								Iterator<JsonNode> elementsTemp = axisExpressions.elements();
								while(elementsTemp.hasNext()){
									JsonNode axisExpression = elementsTemp.next();
									BOReportColumn boReportColumn = new BOReportColumn();
									boReportColumn.setColumnDataType(axisExpression.get("@dataType").asText());
									boReportColumn.setColumnExpression(axisExpression.get("$").asText());
									numberOfColumns++;
									columnNameList.add(boReportColumn.getColumnExpression());
									columnList.add(boReportColumn);
								}
							}else{
								BOReportColumn boReportColumn = new BOReportColumn();
								boReportColumn.setColumnDataType(axisExpressions.get("@dataType").asText());
								boReportColumn.setColumnExpression(axisExpressions.get("$").asText());
								numberOfColumns++;
								columnNameList.add(boReportColumn.getColumnExpression());
								columnList.add(boReportColumn);
							}
						}
//						String role = axisNode.get("@role").asText();
						roleAxisColumns.put(axisNode.get("@role").asText(), columnList);
					}
				}
				JsonNode chartNode = contentNode.path("chart");
				if(chartNode.isArray()){
					System.out.println("22222 visualization is array");
				}else{
//					System.out.println(chartNode.get("@type").asText());
					BOChartElement boChartElement = new BOChartElement();
					boChartElement.setChartName(elementNode.get("name").asText());
					boChartElement.setChartType(chartNode.get("@type").asText());
					JsonNode axesNode = chartNode.path("axes");
					JsonNode axisNode;
					Boolean hasAxis = Boolean.TRUE;
					if (axesNode.isMissingNode()) {
						hasAxis = Boolean.FALSE;
					}else if(!axesNode.path("axis").isArray() && axesNode.path("axis").path("expressions").isMissingNode()){
						hasAxis = Boolean.FALSE;
					}
					if(!hasAxis){
						axesNode = chartNode.path("feeds");
						axisNode = axesNode.path("feed");
					}else{
						axisNode = axesNode.path("axis");
					}
					if(axisNode.isArray()){
						Iterator<JsonNode> elements = axisNode.elements();
						while(elements.hasNext()){
							JsonNode axisElement = elements.next();
							JsonNode axisExpressions = axisElement.path("expressions").path("formula");
							if(!axisExpressions.isMissingNode()){
								List<BOReportColumn> columnList = new ArrayList<BOReportColumn>();
								if(axisExpressions.isArray()){
									Iterator<JsonNode> elementsTemp = axisExpressions.elements();
									while(elementsTemp.hasNext()){
										JsonNode axisExpression = elementsTemp.next();
										BOReportColumn boReportColumn = new BOReportColumn();
										boReportColumn.setColumnDataType(axisExpression.get("@dataType").asText());
										boReportColumn.setColumnExpression(axisExpression.get("$").asText());
										numberOfColumns++;
										columnNameList.add(boReportColumn.getColumnExpression());
										columnList.add(boReportColumn);
									}
								}else{
									BOReportColumn boReportColumn = new BOReportColumn();
									boReportColumn.setColumnDataType(axisExpressions.get("@dataType").asText());
									boReportColumn.setColumnExpression(axisExpressions.get("$").asText());
									numberOfColumns++;
									columnNameList.add(boReportColumn.getColumnExpression());
									columnList.add(boReportColumn);
								}
								roleAxisColumns.put(axisElement.get("@role").asText(), columnList);
							}
						}
					}else{
						JsonNode axisExpressions = axisNode.path("expressions").path("formula");
						List<BOReportColumn> columnList = new ArrayList<BOReportColumn>();
						if(axisExpressions.isArray()){
							Iterator<JsonNode> elementsTemp = axisExpressions.elements();
							while(elementsTemp.hasNext()){
								JsonNode axisExpression = elementsTemp.next();
								BOReportColumn boReportColumn = new BOReportColumn();
								boReportColumn.setColumnDataType(axisExpression.get("@dataType").asText());
								boReportColumn.setColumnExpression(axisExpression.get("$").asText());
								numberOfColumns++;
								columnNameList.add(boReportColumn.getColumnExpression());
								columnList.add(boReportColumn);
							}
						}else{
							BOReportColumn boReportColumn = new BOReportColumn();
							boReportColumn.setColumnDataType(axisExpressions.get("@dataType").asText());
							boReportColumn.setColumnExpression(axisExpressions.get("$").asText());
							numberOfColumns++;
							columnNameList.add(boReportColumn.getColumnExpression());
							columnList.add(boReportColumn);
						}
//						System.out.println(axisNode.get("@role").asText());
						roleAxisColumns.put(axisNode.get("@role").asText(), columnList);
					}
					boChartElement.setRoleAxisColumns(roleAxisColumns);
					boChartElement.setNumberOfColumns(numberOfColumns);
					object = boChartElement;
				}
			}
		} catch(Exception ex) {
			boReportModel.setExceptionReport(boReportModel.getExceptionReport().concat("\n" + ex + " in " + ex.getStackTrace()[0].getMethodName()) );
			logger.error(ex);
			ex.printStackTrace();
		}
		return object;
	}
	
	public List<BOVariable> fetchVariables(int documentId, BOAnalyzerModel boReportModel) {
		List<BOVariable> variableList = new ArrayList<BOVariable>();
		String uri = "/documents/" + documentId + "/variables";
		String ip = webClient.get(uri, REQUEST_PROPERTY_APPL_XML, REQUEST_PROPERTY_APPL_XML);
		Document doc;
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			doc = builder.parse(new InputSource(new StringReader(ip)));
		
		NodeList list = doc.getElementsByTagName("variable");
		for(int i = 0; i < list.getLength(); i ++) {
			Node n = list.item(i);
			if(n.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) n;
				String variableId = e.getElementsByTagName("id").item(0).getChildNodes().item(0).getNodeValue();
				try {
					variableList.add(fetchVariableData(documentId, variableId, boReportModel));	
				}
				catch (Exception ex) {
					boReportModel.setExceptionReport(boReportModel.getExceptionReport().concat("\n" + ex + " in " + ex.getStackTrace()[0].getMethodName()) );
					logger.error(ex.getStackTrace());
					
				}
						
			}
		}
		} 
		catch (Exception ex) {
			boReportModel.setExceptionReport(boReportModel.getExceptionReport().concat("\n" + ex + " in " + ex.getStackTrace()[0].getMethodName()) );
			ex.printStackTrace();
			logger.error(ex);
		}
		return variableList;
	}
	
	public BOVariable fetchVariableData(int documentId, String variableId, BOAnalyzerModel boReportModel) {
		BOVariable boVariable = new BOVariable();
		String uri = "/documents/" + documentId + "/variables/" + variableId;
		String ip = webClient.get(uri, REQUEST_PROPERTY_APPL_XML, REQUEST_PROPERTY_APPL_XML);
		Document doc;
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			doc = builder.parse(new InputSource(new StringReader(ip)));
		
		Node n = doc.getElementsByTagName("variable").item(0);
		if(n.getNodeType() == Node.ELEMENT_NODE) {
			Element e = (Element) n;
			boVariable.setId(e.getElementsByTagName("id").item(0).getChildNodes().item(0).getNodeValue());
			boVariable.setName(e.getElementsByTagName("name").item(0).getChildNodes().item(0).getNodeValue());
			boVariable.setQualification(e.getAttribute("qualification"));
			boVariable.setDataType(e.getAttribute("dataType"));
			boVariable.setFormulaLanguageId(e.getElementsByTagName("formulaLanguageId").item(0).getChildNodes().item(0).getNodeValue());
			boVariable.setDefinition(e.getElementsByTagName("definition").item(0).getChildNodes().item(0).getNodeValue());
		}
		} 
		catch (Exception ex) {
			boReportModel.setExceptionReport(boReportModel.getExceptionReport().concat("\n" + ex + " in " + ex.getStackTrace()[0].getMethodName()));
			ex.printStackTrace();
			logger.error(ex);
	
		}
		return boVariable;
	}
	
	public BOUniverse fetchUniverseData(int id, BoReportConfigDTO config) {
		BOUniverse boUniverse = new BOUniverse();
		try {
			String uri = "/universes/" + id + "?aggregated=true";
			System.out.println("URI:"+uri);
			String out = webClient.get(uri, REQUEST_PROPERTY_APPL_XML, REQUEST_PROPERTY_APPL_XML);
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(out)));
			Element e = (Element) doc.getElementsByTagName("universe").item(0);
			boUniverse.setId(Integer.parseInt(e.getElementsByTagName("id").item(0).getChildNodes().item(0).getNodeValue()));
			boUniverse.setName(e.getElementsByTagName("name").item(0).getChildNodes().item(0).getNodeValue());
			try {
				boUniverse.setDescription(e.getElementsByTagName("description").item(0).getChildNodes().item(0).getNodeValue());
			}
			catch (NullPointerException nullPointerException) {
				boUniverse.setDescription("Not Available");
				logger.error(nullPointerException);
			}
			
			//boUniverse.setItems(fetchDetails(doc));
			StringBuilder path = new StringBuilder();
			path.append("/");
			path.append(e.getElementsByTagName("path").item(0).getChildNodes().item(0).getNodeValue());
			path.append("/");
			path.append(e.getElementsByTagName("name").item(0).getChildNodes().item(0).getNodeValue());
			logger.info("Universe Id: " + id + "\nUniverse Path: " + path.toString());
			UniverseAnalyzerService.getBOUniverseData(config, path.toString(), boUniverse);

		} 
		catch(Exception ex) {
			
			logger.error(ex);
			ex.printStackTrace();
		}
		return boUniverse;
	}
	
	
	private static String fetchUniversePath(int id) {
	StringBuilder path = new StringBuilder();
	try {
		String uri = "/universes/" + id + "?aggregated=true";
		BOWebClient webClient = new BOWebClient("dl5802.ltisap.com", "8080", "10663796", "4pplemUffin");
		String out = webClient.get(uri, REQUEST_PROPERTY_APPL_XML, REQUEST_PROPERTY_APPL_XML);
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = builder.parse(new InputSource(new StringReader(out)));
		Element e = (Element) doc.getElementsByTagName("universe").item(0);
		path.append(e.getElementsByTagName("path").item(0).getChildNodes().item(0).getNodeValue());
		path.append("/");
		path.append(e.getElementsByTagName("name").item(0).getChildNodes().item(0).getNodeValue());
	}
	catch (Exception e) {
		logger.error(e);
		e.printStackTrace();
	}
	return path.toString();
	}
	
	public String testConnection(BoReportConfigDTO config) {
		// TODO Auto-generated method stub
		try
		{
			CMSSERVERIP = config.getHostname();
			BOUSERID = config.getUsername();
			BOPASSWORD = config.getPassword();
			BO_WS_PORT = config.getPort();
		//	PATH = config.getPath();
			
			System.out.println("CMS SERVER IP:"+CMSSERVERIP);
			System.out.println("USER:"+BOUSERID);
			System.out.println("PASSWORD:"+BOPASSWORD);
			
			logonToken = logonAndGetToken(CMSSERVERIP, BOUSERID, BOPASSWORD);
			REQUEST_PROPERTY_X_SAP_LOGONTOKEN_VALUE = "\""+logonToken+"\"";
			
			System.out.println("Logon Token::::"+logonToken);
			if(logonToken==null ||logonToken.equalsIgnoreCase(""))
			{
				return "Fail";
			}
		}
		catch(Exception e)
		{
			logger.error(e);
			return "Fail";
		}
		finally{
			logOut();
		}
		return "Success";		
	}
	
	
	public Map<String,List<ReportData>> extractReportPathData(BoReportConfigDTO config) {
		// TODO Auto-generated method stub
		
		Map<String,List<ReportData>> reportPathMap = new LinkedHashMap<String,List<ReportData>>(); 
	//	List<String> resList = new LinkedList<String>();
		try
		{
			CMSSERVERIP = config.getHostname();
			BOUSERID = config.getUsername();
			BOPASSWORD = config.getPassword();
			BO_WS_PORT = config.getPort();
			//PATH = config.getPath();
			logonToken = logonAndGetToken(CMSSERVERIP, BOUSERID, BOPASSWORD);
			
			System.out.println("Logon Token for API:::"+logonToken);
			
			REQUEST_PROPERTY_X_SAP_LOGONTOKEN_VALUE = "\""+logonToken+"\"";
			
			
			
			webClient = new BOWebClient(CMSSERVERIP, BO_WS_PORT, BOUSERID, BOPASSWORD);
			
			Map<Integer, String> documentIdNameMap = new HashMap<Integer, String>();
			
			documentIdNameMap = fetchListOfDocuments(50, 0, documentIdNameMap);
			
			for (Integer documentId : documentIdNameMap.keySet()) {
				
				String reportName = documentIdNameMap.get(documentId);
			//	System.out.println("Report Name::"+reportName);
				try {
					StringBuilder output = new StringBuilder();
					URL url = new URL("http://"+ CMSSERVERIP +":"+ BO_WS_PORT + "/biprws/raylight/v1/documents/" + documentId);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod(REQUEST_METHOD_GET);
					conn.setRequestProperty(REQUEST_PROPERTY_CONTENT_TYPE, REQUEST_PROPERTY_APPL_XML);
					conn.setRequestProperty(REQUEST_PROPERTY_ACCEPT, REQUEST_PROPERTY_APPL_JSON);
					conn.setRequestProperty(REQUEST_PROPERTY_X_SAP_LOGONTOKEN, REQUEST_PROPERTY_X_SAP_LOGONTOKEN_VALUE);
					
					BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
					
					String temp;
					while ((temp = br.readLine()) != null) {
//						System.out.println(temp);
						output.append(temp);
					}
					byte[] jsonOutput = output.toString().getBytes();
					//create ObjectMapper instance
					ObjectMapper objectMapper = new ObjectMapper();

					//read JSON like DOM Parser
					JsonNode rootNode = objectMapper.readTree(jsonOutput);
					JsonNode documentNode = rootNode.path("document");
					
					String path = documentNode.get("path") != null ? documentNode.get("path").asText():"NOT AVAILABLE";
					
				//	String path = documentNode.get("path") != null ? documentNode.get("path").asText() + "/" + reportName: "NOT AVAILABLE";
					
					double size = documentNode.get("size") != null ?  documentNode.get("size").asDouble() : -1;
					String date = documentNode.get("updated") != null ? documentNode.get("updated").asText() : "1900-01-01";
					
					String updatedDate = convertReportDate(date);
					
					
					//Fetching universe details
					
					System.out.println("Report Name:"+reportName);
				//	String universeId = fetchUniverseId(documentId);
				//	System.out.println("Universe Id"+universeId);
					
					Map<String, String> dataProviderIdNameMap = fetchListOfUniverseProviders(documentId);
				//	System.out.println("Data provider Map:"+dataProviderIdNameMap);
					
					Set<String> universeNamesSet = new LinkedHashSet<String>();
					
					if(dataProviderIdNameMap != null && dataProviderIdNameMap.size() > 0) {				
						//List<BOReportQuery> boReportQueries = new ArrayList<BOReportQuery>();
						for(String dataProviderId : dataProviderIdNameMap.keySet()){
							String uniName= fetchDataProviderUniData(documentId, dataProviderId);						
						//	System.out.println("Universe name::"+uniName);
							universeNamesSet.add(uniName);
						}
					}

					if(reportPathMap.containsKey(path))
					{
						List<ReportData> valueReportData = reportPathMap.get(path);
						ReportData tempReport=new ReportData();
						tempReport.setReportName(reportName);
						tempReport.setSize(Double.toString(size));
						tempReport.setDate(updatedDate);
						tempReport.setUniverses(universeNamesSet);
						tempReport.setReportId(Integer.toString(documentId));
						valueReportData.add(tempReport);
						
						reportPathMap.put(path,valueReportData);
					}
					else
					{
						List<ReportData> reportDataList = new LinkedList<ReportData>();
						ReportData tempReport=new ReportData();
						tempReport.setReportName(reportName);
						tempReport.setSize(Double.toString(size));
						tempReport.setDate(updatedDate);
						tempReport.setUniverses(universeNamesSet);
						tempReport.setReportId(Integer.toString(documentId));
						reportDataList.add(tempReport);
						
						reportPathMap.put(path,reportDataList);
					}

					//resList.add(path);
					
					if(conn != null){
						conn.disconnect();
						br.close();
					}
				} 
				catch(Exception ex) {
					//boReportModel.setExceptionReport(boReportModel.getExceptionReport().concat("\n" + ex + " in " + ex.getStackTrace()[0].getMethodName() + " in " + ex.getStackTrace()[0].getMethodName()));
					logger.error(ex);
				}
				
			}
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error(e);
		}
		finally {
			logOut();
			webClient.logOut();
			
		}
		
		
		return reportPathMap;
	}

	private String fetchDataProviderUniData(Integer documentId, String dataProviderId) {
		// TODO Auto-generated method stub
		
		System.out.println("doument id::"+documentId);
		System.out.println("Data provider ID::"+dataProviderId);
		
		String universeName="";
		try {
			String uri = "/documents/" + documentId + "/dataproviders/" + dataProviderId;
			String output = webClient.get(uri, REQUEST_PROPERTY_APPL_XML, REQUEST_PROPERTY_APPL_JSON);
			byte[] jsonOutput = output.getBytes();
			//create ObjectMapper instance
			ObjectMapper objectMapper = new ObjectMapper();

			//read JSON like DOM Parser
			JsonNode rootNode = objectMapper.readTree(jsonOutput);
			JsonNode dataproviderNode = rootNode.path("dataprovider");
			//boReportModel.getUniverses().add(dataproviderNode.get("dataSourceName").asText());
			universeName = dataproviderNode.get("dataSourceName").asText();
			universeName += "."+dataproviderNode.get("dataSourceType").asText();
			
		}
		
		catch(Exception ex) {
			//boReportModel.setExceptionReport(boReportModel.getExceptionReport().concat("\n" + ex + " in " + ex.getStackTrace()[0].getMethodName()) );
			logger.error(ex);
			ex.printStackTrace();
		}
		return universeName;
		
		
		
	}

	private Map<String, String> fetchListOfUniverseProviders(Integer documentId) {
		
		Map<String, String> dataProviderIdNameMap = new HashMap<String, String>();
		try {
			String uri = "/documents/" + documentId + "/dataproviders";
			String output = webClient.get(uri, REQUEST_PROPERTY_APPL_XML, REQUEST_PROPERTY_APPL_JSON);
			byte[] jsonOutput = output.toString().getBytes();
			//create ObjectMapper instance
			ObjectMapper objectMapper = new ObjectMapper();

			//read JSON like DOM Parser
			JsonNode rootNode = objectMapper.readTree(jsonOutput);
			JsonNode dataprovidersNode = rootNode.path("dataproviders");
			JsonNode dataproviderNode = dataprovidersNode.path("dataprovider");
			Set<String> dataSourceIdList = new HashSet<String>();
			if(!dataproviderNode.isMissingNode()){
				if(dataproviderNode.isArray()){
					Iterator<JsonNode> elements = dataproviderNode.elements();
					while(elements.hasNext()){
						JsonNode dataProvider = elements.next();
						dataProviderIdNameMap.put(dataProvider.get("id").asText(), dataProvider.get("name").asText());
						if(dataProvider.get("dataSourceType") != null){
							String dataSourceType = dataProvider.get("dataSourceType").asText();
							if(dataSourceType.equalsIgnoreCase("unx") || dataSourceType.equalsIgnoreCase("unv")){
								if(dataProvider.get("dataSourceId") != null) {
									dataSourceIdList.add(dataProvider.get("dataSourceId").asText());
									//universeIdSet.add(Integer.parseInt(dataProvider.get("dataSourceId").asText()));
									//universeId=dataProvider.get("dataSourceId").asText();
								}						
							}
						}
					}
				}
				else {
					dataProviderIdNameMap.put(dataproviderNode.get("id").asText(), dataproviderNode.get("name").asText());
					if(dataproviderNode.get("dataSourceType") != null) {
						String dataSourceType = dataproviderNode.get("dataSourceType").asText();
						if(dataSourceType.equalsIgnoreCase("unx") || dataSourceType.equalsIgnoreCase("unv")){
							if(dataproviderNode.get("dataSourceId") != null) {
								dataSourceIdList.add(dataproviderNode.get("dataSourceId").asText());
								//universeIdSet.add(Integer.parseInt(dataproviderNode.get("dataSourceId").asText()));
								//universeId=dataproviderNode.get("dataSourceId").asText();
								System.out.println("Found");
							}
								
						}
					}
				}
			}
			//dataProviderIdNameMap.put("totalUniverseCount", Integer.toString(dataSourceIdList.size()));
		} catch(Exception ex) {
			//boReportModel.setExceptionReport(boReportModel.getExceptionReport().concat("\n" + ex + " in " + ex.getStackTrace()[0].getMethodName()) );
			logger.error(ex);
			ex.printStackTrace();
		}
		return dataProviderIdNameMap;
	}
	

	private BOVisualizationModel fetchVisualizationDetailsForReport(int documentId) {
		// TODO Auto-generated method stub
		
		BOVisualizationModel boVisualizationModelObj = new BOVisualizationModel();
		
		List<BOReportVisualizationTab> reportTabList = new ArrayList<BOReportVisualizationTab>();
		try
		{
			//int documentId = boReportModel.getReportId();
			
			String uri = "/documents/" + documentId + "/reports";
			System.out.println("URI for Style elements::"+uri);
			String output = webClient.get(uri, REQUEST_PROPERTY_APPL_XML, REQUEST_PROPERTY_APPL_JSON);
			System.out.println("Output Style Element::"+output);
			
			byte[] jsonOutput = output.getBytes();
			//create ObjectMapper instance
			ObjectMapper objectMapper = new ObjectMapper();

			//read JSON like DOM Parser
			JsonNode rootNode = objectMapper.readTree(jsonOutput);
			JsonNode reportsNode = rootNode.path("reports");
			JsonNode reportNode = reportsNode.path("report");
			if(reportNode.isArray()){
				Iterator<JsonNode> elements = reportNode.elements();
				while(elements.hasNext()){
					JsonNode report = elements.next();
				
					BOReportVisualizationTab boReportTab = new BOReportVisualizationTab();
					boReportTab.setReportTabId(report.get("id").asText());
					boReportTab.setReportTabName(report.get("name").asText());
					
					List<BOVisualElements> boVisualElementList = fetchVisualStyleDetails(documentId,boReportTab.getReportTabId()); 
					System.out.println("List before::::"+boVisualElementList);
					
					boVisualElementList = calculatePageWidthHeight(boVisualElementList);
					System.out.println("List After::::"+boVisualElementList);
					
					boReportTab.setBoVisualElements(boVisualElementList);

					reportTabList.add(boReportTab);
				}
			}
			else {
				BOReportVisualizationTab boReportTab = new BOReportVisualizationTab();
				boReportTab.setReportTabId(reportNode.get("id").asText());
				boReportTab.setReportTabName(reportNode.get("name").asText());
				
				List<BOVisualElements> boVisualElementList = fetchVisualStyleDetails(documentId,boReportTab.getReportTabId()); 
				System.out.println("List before::::"+boVisualElementList);
				boVisualElementList = calculatePageWidthHeight(boVisualElementList);
				System.out.println("List After::::"+boVisualElementList);
				boReportTab.setBoVisualElements(boVisualElementList);
				
				reportTabList.add(boReportTab);
			}
			boVisualizationModelObj.setBoVisualizationTab(reportTabList);
			
		}
		catch(Exception ex) {
			//boReportModel.setExceptionReport(boReportModel.getExceptionReport().concat("\n" + ex + " in " + ex.getStackTrace()[0].getMethodName()) );
			logger.error(ex);
			ex.printStackTrace();
		}

		return boVisualizationModelObj;
	}
	


	private List<BOVisualElements> fetchVisualStyleDetails(int documentId, String reportTabId) {
		// TODO Auto-generated method stub
		
		List<BOVisualElements> visualElementList = new LinkedList<BOVisualElements>();
		
		try {
			String uri = "/documents/" + documentId + "/reports/" + reportTabId + "/elements?allInfo=true";
			String output = webClient.get(uri, REQUEST_PROPERTY_APPL_XML, REQUEST_PROPERTY_APPL_XML);
			System.out.println("Style xml output::"+output);
			//String xml = convert(output);
			
			//Create a folder for reportid and report tab id
			//Path path = Paths.get("src/main/resources/report/"+documentId+"/"+reportTabId);
			//Path path = Paths.get("src/main/resources/report/"+documentId);
			
			Path path = Paths.get(getClass().getClassLoader().getResource("").getPath().substring(1).replaceAll("%20", " ")+"report/"+documentId+"/"+reportTabId);
			
			System.out.println("Path :::"+path.toString());
				
			//String path = path.toString().replaceAll("%20", "");
			
			Files.createDirectories(path);
			System.out.println("Directory is created!");
			
			
			//Create file named style.xml
			
		//	File fileName = new File("src/main/resources/report/"+documentId+"/"+reportTabId+"/style.xml");
			
			File fileName = new File(getClass().getClassLoader().getResource("").getPath().substring(1).replaceAll("%20", " ")+"report/"+documentId+"/"+reportTabId+"/style.xml");
			
			System.out.println("FileNAme::"+fileName.getAbsolutePath());
			
				try
				{
					 if (fileName.createNewFile()) {
			                System.out.println("File is created!");
			            } else {
			                System.out.println("File already exists.");
			            }
				}
				catch(Exception e) {
		            System.err.println(e);
		            logger.error(e);
		        }
			 
			  try (FileWriter writer = new FileWriter(fileName)) {
	                writer.write(output);
	            }

			  //Copy pagezone.xqy to specific report id and report tab id folder
				
				CopyFiles(documentId,reportTabId,"pagezone.xqy");
				CopyFiles(documentId,reportTabId,"cell.xqy");
				CopyFiles(documentId,reportTabId,"visualization.xqy");
				CopyFiles(documentId,reportTabId,"hTable.xqy");
				CopyFiles(documentId,reportTabId,"vTable.xqy");
				CopyFiles(documentId,reportTabId,"section.xqy");
				CopyFiles(documentId,reportTabId,"unknown.xqy");
				CopyFiles(documentId,reportTabId,"XTable.xqy");
				
				//Modify the content of pagezone.xqy
				
				ModifyXQYFileContent(documentId,reportTabId,"pagezone.xqy");
				ModifyXQYFileContent(documentId,reportTabId,"cell.xqy");
				ModifyXQYFileContent(documentId, reportTabId, "visualization.xqy");
				ModifyXQYFileContent(documentId, reportTabId, "hTable.xqy");
				ModifyXQYFileContent(documentId, reportTabId, "vTable.xqy");
				ModifyXQYFileContent(documentId, reportTabId, "section.xqy");
				ModifyXQYFileContent(documentId, reportTabId, "unknown.xqy");
				ModifyXQYFileContent(documentId, reportTabId, "XTable.xqy");
				
			      //XQuery execution  
				
				//visualElementList = xQueryExecution(documentId,reportTabId,"pagezone.xqy");
				
				//PageZone extraction
				
				
		//		 InputStream inputStream = new FileInputStream(new File("src/main/resources/report/"+documentId+"/"+reportTabId+"/pagezone.xqy"));
		  
				InputStream inputStream = new FileInputStream(new File(getClass().getClassLoader().getResource("").getPath().substring(1).replaceAll("%20", " ")+"report/"+documentId+"/"+reportTabId+"/pagezone.xqy"));
				
				
				  XQDataSource ds = new SaxonXQDataSource();
			      XQConnection conn = ds.getConnection();
			      XQPreparedExpression exp = conn.prepareExpression(inputStream);
			      	

			      XQResultSequence result = exp.executeQuery();
			      
			      while (result.next()) {
			       
			         BOVisualElements boVisualElementObj = new BOVisualElements();
			         
			         String pageZoneResult = result.getItemAsString(null);
			        // System.out.println("CSV For page zone::"+pageZoneResult);
			         
			        	 if(!pageZoneResult.isEmpty())
				         {
				        	 String[] arrOfStr = pageZoneResult.split("~");
					        
				        	 boVisualElementObj.setType("Page Zone");
				        	 boVisualElementObj.setCategory(arrOfStr[0]);
				        	 
				        	 String elementName = arrOfStr[0];
				        	 
				        	 if(!elementName.isEmpty())
					        		boVisualElementObj.setElementName(elementName);
				        	 else
					        		boVisualElementObj.setElementName("<< Not Applicable >>");
				        	 
				        	 String elementId = arrOfStr[1];
				        	 boVisualElementObj.setElementId(elementId);
				        	 boVisualElementObj.setAlwaysFlag(arrOfStr[2]);
				        	 boVisualElementObj.setMinimalHeight(arrOfStr[3]);
				        	 boVisualElementObj.setBackgroundColor(arrOfStr[4]);
				        	 boVisualElementObj.setBorder(arrOfStr[5]);
				        	 
					        visualElementList.add(boVisualElementObj);
				         }
			        
			      }
			      inputStream.close();
			      
			      //vTable Element Extraction
			      
				  //    inputStream = new FileInputStream(new File("src/main/resources/report/"+documentId+"/"+reportTabId+"/vTable.xqy"));
				   
				      inputStream = new FileInputStream(new File(getClass().getClassLoader().getResource("").getPath().substring(1).replaceAll("%20", " ")+"report/"+documentId+"/"+reportTabId+"/vTable.xqy"));    
				      exp = conn.prepareExpression(inputStream);
				      
				      result = exp.executeQuery();
				      List<String> vTableElementId = new ArrayList<String>();
				      
				      while (result.next()) {
					       
					         BOVisualElements boVisualElementObj = new BOVisualElements();
					         
					         String vTableResult = result.getItemAsString(null);
					         System.out.println("CSV For VTable Element ::"+vTableResult);
					         
					        	 if(!vTableResult.isEmpty())
						         {
						        	 String[] arrOfStr = vTableResult.split("~");
						        	 vTableElementId.add(arrOfStr[1]);
							        
						        	 boVisualElementObj.setType("VTable");
						        	 boVisualElementObj.setCategory(arrOfStr[0]);
						        	 
						        	 String elementName = arrOfStr[0];
						        	 if(!elementName.isEmpty())
							        		boVisualElementObj.setElementName(elementName);
						        	 else
							        		boVisualElementObj.setElementName("<< Not Applicable >>");
						        	 boVisualElementObj.setElementId(arrOfStr[1]);
						        	 boVisualElementObj.setParentId(arrOfStr[2]);
						        	 boVisualElementObj.setAlwaysFlag(arrOfStr[4]);
						        	 boVisualElementObj.setMinimalWidth(arrOfStr[5]);
						        	 boVisualElementObj.setMinimalHeight(arrOfStr[6]);
						        	 boVisualElementObj.setxPosition(arrOfStr[7]);
						        	 boVisualElementObj.setyPosition(arrOfStr[8]);
						        //	 boVisualElementObj.setHorizontalAnchorType(arrOfStr[8]);
						        //	 boVisualElementObj.setHorizontalAnchorId(arrOfStr[9]);
						        //	 boVisualElementObj.setVerticalAnchorType(arrOfStr[10]);
						        //	 boVisualElementObj.setVerticalAnchorId(arrOfStr[11]);
						        	 
						        	// boVisualElementObj.setBackgroundColor(arrOfStr[12]);
						        	 boVisualElementObj.setBorder(arrOfStr[11]);
						        	 
						        	 String axesData = arrOfStr[12];
						        	 
						        	 List<Axes> axesList = getAxesData(axesData,"Table");
						        	 
						        	 boVisualElementObj.setChartAxes(axesList);
						        	 
						        	 String layoutData = arrOfStr[13];
						        	 
						        	 List<Zones> layoutList = getLayoutData(layoutData);
						        	  
						        	 boVisualElementObj.setZonesList(layoutList);
						    
						        	 
							        visualElementList.add(boVisualElementObj);
						         }
					        
					      }
					      inputStream.close();
					      
					    //hTable Element Extraction
					      
						     // inputStream = new FileInputStream(new File("src/main/resources/report/"+documentId+"/"+reportTabId+"/hTable.xqy"));
						      
						      inputStream = new FileInputStream(new File(getClass().getClassLoader().getResource("").getPath().substring(1).replaceAll("%20", " ")+"report/"+documentId+"/"+reportTabId+"/hTable.xqy"));
						      
						      exp = conn.prepareExpression(inputStream);
						      
						      result = exp.executeQuery();
						      
						      List<String> hTableElementId = new ArrayList<String>();
						      while (result.next()) {
							       
							         BOVisualElements boVisualElementObj = new BOVisualElements();
							         
							         String hTableResult = result.getItemAsString(null);
							         System.out.println("CSV For HTable Element ::"+hTableResult);
							         
							        	 if(!hTableResult.isEmpty())
								         {
								        	 String[] arrOfStr = hTableResult.split("~");
								        	 hTableElementId.add(arrOfStr[1]);
								        	 boVisualElementObj.setType("HTable");
								        	 boVisualElementObj.setCategory(arrOfStr[0]);
								        	 
								        	 String elementName = arrOfStr[0];
								        	 if(!elementName.isEmpty())
								        		boVisualElementObj.setElementName(elementName);
								        	 else
								        		 boVisualElementObj.setElementName("<< Not Applicable >>"); 
								        	 boVisualElementObj.setElementId(arrOfStr[1]);
								        	 boVisualElementObj.setParentId(arrOfStr[2]);
								        	 boVisualElementObj.setAlwaysFlag(arrOfStr[4]);
								        	 boVisualElementObj.setMinimalWidth(arrOfStr[5]);
								        	 boVisualElementObj.setMinimalHeight(arrOfStr[6]);
								        	 boVisualElementObj.setxPosition(arrOfStr[7]);
								        	 boVisualElementObj.setyPosition(arrOfStr[8]);
								        //	 boVisualElementObj.setHorizontalAnchorType(arrOfStr[8]);
								        //	 boVisualElementObj.setHorizontalAnchorId(arrOfStr[9]);
								        //	 boVisualElementObj.setVerticalAnchorType(arrOfStr[10]);
								        //	 boVisualElementObj.setVerticalAnchorId(arrOfStr[11]);
								        	 
								        	// boVisualElementObj.setBackgroundColor(arrOfStr[12]);
								        	 boVisualElementObj.setBorder(arrOfStr[11]);
								        	 
								        	 String axesData = arrOfStr[12];
								        	 
								        	 List<Axes> axesList = getAxesData(axesData,"Table");
								        	 
								        	 boVisualElementObj.setChartAxes(axesList);
								        	 
								        	 
									        visualElementList.add(boVisualElementObj);
								         }
							        
							      }
							      inputStream.close();
							      
							    //xTable Element Extraction
							      
								     // inputStream = new FileInputStream(new File("src/main/resources/report/"+documentId+"/"+reportTabId+"/hTable.xqy"));
								      
								      inputStream = new FileInputStream(new File(getClass().getClassLoader().getResource("").getPath().substring(1).replaceAll("%20", " ")+"report/"+documentId+"/"+reportTabId+"/XTable.xqy"));
								      
								      exp = conn.prepareExpression(inputStream);
								      
								      result = exp.executeQuery();
								      
								      List<String> xTableElementId = new ArrayList<String>();
								      while (result.next()) {
									       
									         BOVisualElements boVisualElementObj = new BOVisualElements();
									         
									         String xTableResult = result.getItemAsString(null);
									         System.out.println("CSV For XTable Element ::"+xTableResult);
									         
									        	 if(!xTableResult.isEmpty())
										         {
										        	 String[] arrOfStr = xTableResult.split("~");
										        	 xTableElementId.add(arrOfStr[1]);
										        	 boVisualElementObj.setType("XTable");
										        	 boVisualElementObj.setCategory(arrOfStr[0]);
										        	 
										        	 String elementName = arrOfStr[0];
										        	 if(!elementName.isEmpty())
										        		boVisualElementObj.setElementName(elementName);
										        	 else
										        		 boVisualElementObj.setElementName("<< Not Applicable >>"); 
										        	 boVisualElementObj.setElementId(arrOfStr[1]);
										        	 boVisualElementObj.setParentId(arrOfStr[2]);
										        	 boVisualElementObj.setAlwaysFlag(arrOfStr[4]);
										        	 boVisualElementObj.setMinimalWidth(arrOfStr[5]);
										        	 boVisualElementObj.setMinimalHeight(arrOfStr[6]);
										        	 boVisualElementObj.setxPosition(arrOfStr[7]);
										        	 boVisualElementObj.setyPosition(arrOfStr[8]);
										        //	 boVisualElementObj.setHorizontalAnchorType(arrOfStr[8]);
										        //	 boVisualElementObj.setHorizontalAnchorId(arrOfStr[9]);
										        //	 boVisualElementObj.setVerticalAnchorType(arrOfStr[10]);
										        //	 boVisualElementObj.setVerticalAnchorId(arrOfStr[11]);
										        	 
										        	// boVisualElementObj.setBackgroundColor(arrOfStr[12]);
										        	 boVisualElementObj.setBorder(arrOfStr[11]);
										        	 
										        	 String axesData = arrOfStr[12];
										        	 
										        	 List<Axes> axesList = getAxesData(axesData,"Table");
										        	 
										        	 boVisualElementObj.setChartAxes(axesList);
										        	 
										        	 
											        visualElementList.add(boVisualElementObj);
										         }
									        
									      }
									      inputStream.close();
			      
									    //Cell Element Extraction
									      
										   //   inputStream = new FileInputStream(new File("src/main/resources/report/"+documentId+"/"+reportTabId+"/cell.xqy"));
										    
										      inputStream = new FileInputStream(new File(getClass().getClassLoader().getResource("").getPath().substring(1).replaceAll("%20", " ")+"report/"+documentId+"/"+reportTabId+"/cell.xqy"));
										      
										      exp = conn.prepareExpression(inputStream);
										      
										      result = exp.executeQuery();
										      
										      while (result.next()) {
										       
										         BOVisualElements boVisualElementObj = new BOVisualElements();
										         
										         String cellResult = result.getItemAsString(null);
										         //System.out.println("CSV For Cell Element ::"+cellResult);
										         
										        	 if(!cellResult.isEmpty())
											         {
											        	 String[] arrOfStr = cellResult.split("~");
												         String parentId=arrOfStr[2];
												         
												         if(!vTableElementId.contains(parentId) && !hTableElementId.contains(parentId) && !xTableElementId.contains(parentId)) {
												        	 boVisualElementObj.setType("Cell");
												        	 boVisualElementObj.setCategory(arrOfStr[0]);
												        	 System.out.println("cellname "+arrOfStr[0]);
												        	 String elementName = arrOfStr[0];
												        	 if(!elementName.isEmpty())
												        		boVisualElementObj.setElementName(elementName);
												        	 else
												        		 boVisualElementObj.setElementName("<< Not Applicable >>"); 
												        	 boVisualElementObj.setElementId(arrOfStr[1]);
												        	 boVisualElementObj.setParentId(arrOfStr[2]);
												        	 boVisualElementObj.setAlwaysFlag(arrOfStr[3]);
												        	 boVisualElementObj.setMinimalWidth(arrOfStr[4]);
												        	 boVisualElementObj.setMinimalHeight(arrOfStr[5]);
												        	 boVisualElementObj.setxPosition(arrOfStr[6]);
												        	 boVisualElementObj.setyPosition(arrOfStr[7]);
												        	 boVisualElementObj.setHorizontalAnchorType(arrOfStr[8]);
												        	 boVisualElementObj.setHorizontalAnchorId(arrOfStr[9]);
												        	 boVisualElementObj.setVerticalAnchorType(arrOfStr[10]);
												        	 boVisualElementObj.setVerticalAnchorId(arrOfStr[11]);
												        	 boVisualElementObj.setBackgroundColor(arrOfStr[12]);
												        	 boVisualElementObj.setBorder(arrOfStr[13]);
												        	 boVisualElementObj.setFont(arrOfStr[14]);
												        	 boVisualElementObj.setAllignment(arrOfStr[15]);
												        	 
												        	 boVisualElementObj.setFormula(arrOfStr[16]);
												        	 
												        	 System.out.println("Formula for Total Testing::"+boVisualElementObj.getFormula());
												        	 
												        	 if(arrOfStr.length==18)
												        	 {
												        		 boVisualElementObj.setAlerterId(arrOfStr[17]);
												        	 }
												        	 
													        visualElementList.add(boVisualElementObj);
												         }
											        	 
											         }
										        
										      }
										      inputStream.close();
			      
			      
			      //Visualization Element Extraction
			      
			    //  inputStream = new FileInputStream(new File("src/main/resources/report/"+documentId+"/"+reportTabId+"/visualization.xqy"));
			     
			      inputStream = new FileInputStream(new File(getClass().getClassLoader().getResource("").getPath().substring(1).replaceAll("%20", " ")+"report/"+documentId+"/"+reportTabId+"/visualization.xqy"));
			      
			      exp = conn.prepareExpression(inputStream);
			      
			      result = exp.executeQuery();
			      
			      while (result.next()) {
			       
			         BOVisualElements boVisualElementObj = new BOVisualElements();
			         
			         String visualizationResult = result.getItemAsString(null);
			         System.out.println("CSV For Visualization Element ::"+visualizationResult);
			         
			        	 if(!visualizationResult.isEmpty())
				         {
				        	 String[] arrOfStr = visualizationResult.split("~");
					        
				        	 boVisualElementObj.setType("Visualization");
				        	// boVisualElementObj.setCategory(arrOfStr[0]);
				        	 
				        	 String elementName = arrOfStr[0];
				        	 if(!elementName.isEmpty())
					        		boVisualElementObj.setElementName(elementName);
				        	 else
					        		boVisualElementObj.setElementName("<< Not Applicable >>");
				        	 boVisualElementObj.setElementId(arrOfStr[1]);
				        	 boVisualElementObj.setParentId(arrOfStr[2]);
				        	 boVisualElementObj.setAlwaysFlag(arrOfStr[4]);
				        	 boVisualElementObj.setMinimalWidth(arrOfStr[5]);
				        	 boVisualElementObj.setMinimalHeight(arrOfStr[6]);
				        	 boVisualElementObj.setxPosition(arrOfStr[7]);
				        	 boVisualElementObj.setyPosition(arrOfStr[8]);
				        	 boVisualElementObj.setHorizontalAnchorType(arrOfStr[9]);
				        	 boVisualElementObj.setHorizontalAnchorId(arrOfStr[10]);
				        	 boVisualElementObj.setVerticalAnchorType(arrOfStr[11]);
				        	 boVisualElementObj.setVerticalAnchorId(arrOfStr[12]);
				        	 
				        	
				        	 boVisualElementObj.setBackgroundColor(arrOfStr[13]);
				        	 boVisualElementObj.setBackgroundColorAlpha(arrOfStr[14]);
				        	 boVisualElementObj.setBorder(arrOfStr[15]);
				        	 boVisualElementObj.setChartType(arrOfStr[16]);
				        	 boVisualElementObj.setChartName(arrOfStr[17]);
				        	 boVisualElementObj.setChartTitle(arrOfStr[18]);
				        	 boVisualElementObj.setChartLegend(arrOfStr[19]);
				        	 boVisualElementObj.setChartPlotArea(arrOfStr[20]);
				        	// boVisualElementObj.setChartAxes(arrOfStr[17]);
				        	 
				        	 
				        	 String axesData = arrOfStr[21];
				        	 
				        	 List<Axes> axesList = getAxesData(axesData,"Visualization");
				        	 
				        	 System.out.println("Axes List Visualization::"+axesList.toString());
				        	 
				        	 boVisualElementObj.setChartAxes(axesList);
				        	 
				        	 visualElementList.add(boVisualElementObj);
				         }
			        
			      }
			      inputStream.close();
			      
			      
			    
					      
                          //section element extraction
					      
					      inputStream = new FileInputStream(new File(getClass().getClassLoader().getResource("").getPath().substring(1).replaceAll("%20", " ")+"report/"+documentId+"/"+reportTabId+"/section.xqy"));
					      
					      exp = conn.prepareExpression(inputStream);
					      
					      result = exp.executeQuery();
					     // System.out.println("Printing Section elems"+result.getItemAsString(null));
					      
					      while(result.next()) {
					    	  BOVisualElements boVisualElementObj = new BOVisualElements();
						         
						      String sectionResult = result.getItemAsString(null);
						      if(!sectionResult.isEmpty()) {
						    	  String[] arrOfStr = sectionResult.split("~");
						    	  boVisualElementObj.setType("Section");
						    	  boVisualElementObj.setElementId(arrOfStr[0]);
						    	  boVisualElementObj.setElementName("<< Not Applicable >>");
						    	  boVisualElementObj.setParentId(arrOfStr[1]);
						    	  boVisualElementObj.setBackgroundColor(arrOfStr[3]);
						    	  
						    	  String axesData = arrOfStr[4];
						    	  List<Axes> axesList = getAxesData(axesData,"Section");
						    	  boVisualElementObj.setChartAxes(axesList);
						    	  
						    	  visualElementList.add(boVisualElementObj);					    	  
						    	  
						      }
						    }
					      inputStream.close();
					      
					    //Unknown Element Extraction
					      inputStream = new FileInputStream(new File(getClass().getClassLoader().getResource("").getPath().substring(1).replaceAll("%20", " ")+"report/"+documentId+"/"+reportTabId+"/unknown.xqy"));
					      
					      exp = conn.prepareExpression(inputStream);
					      
					      result = exp.executeQuery();
					      
					      while(result.next()) {
					    	  BOVisualElements boVisualElementObj = new BOVisualElements();
						         
						      String unknownResult = result.getItemAsString(null);
						      if(!unknownResult.isEmpty()) {
						    	  String[] arrOfStr = unknownResult.split("~");
						    	  System.out.println("Unknown Elements Found!! " +arrOfStr.toString());
						    	  boVisualElementObj.setType(arrOfStr[2]);
						    	  boVisualElementObj.setElementName("<< Unknown >>");
						    	  boVisualElementObj.setElementId(arrOfStr[0]);
						    	  
						    	  
						    	  
						    	  visualElementList.add(boVisualElementObj);
						    						    	  
						    	  
						      }
						    }
					      inputStream.close();
					      
					      
					      //FileUtils.forceDelete(new File("src/main/resources/report/"+documentId));
			
		}
		catch(Exception ex) {
			//boReportModel.setExceptionReport(boReportModel.getExceptionReport().concat("\n" + ex + " in " + ex.getStackTrace()[0].getMethodName()) );
			logger.error(ex);
			ex.printStackTrace();
		}
		return visualElementList;
	}
	
	



	private void CopyFiles(int documentId, String reportTabId, String xqyFileName) {
		// TODO Auto-generated method stub
		
		String currentDirectory = System.getProperty("user.dir");
		
		System.out.println("Current Directory::"+currentDirectory);
		
		//Path sourcePath = new File("src/main/resources/"+xqyFileName).toPath();
	//	Path destPath = new File("src/main/resources/report/"+documentId+"/"+reportTabId+"/"+xqyFileName).toPath();
		
		Path sourcePath = new File(getClass().getClassLoader().getResource("").getPath().substring(1).replaceAll("%20", " ")+xqyFileName).toPath();
		Path destPath = new File(getClass().getClassLoader().getResource("").getPath().substring(1).replaceAll("%20", " ")+"report/"+documentId+"/"+reportTabId+"/"+xqyFileName).toPath();
		
		
		System.out.println("Source Path::"+sourcePath);
		System.out.println("Destination Path::"+destPath);
		
		if(!destPath.toFile().exists())
		{
			//destPath.toFile().delete();
			try {
				Files.copy(sourcePath, destPath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error(e);
			}
		}
		
	}
	
	private void ModifyXQYFileContent(int documentId, String reportTabId, String xqyFileName) {
		// TODO Auto-generated method stub
	//	 File fileToBeModified = new File("src/main/resources/report/"+documentId+"/"+reportTabId+"/"+xqyFileName);
	  
		File fileToBeModified = new File(getClass().getClassLoader().getResource("").getPath().substring(1).replaceAll("%20", " ")+"report/"+documentId+"/"+reportTabId+"/"+xqyFileName);

		
		String oldContent = "";
	        BufferedReader reader = null;
	        
	        FileWriter writer = null;
	        try
	        {
	            reader = new BufferedReader(new FileReader(fileToBeModified));
	             
	            //Reading all the lines of input text file into oldContent
	             
	            String line = reader.readLine();
	             
	            while (line != null) 
	            {
	                oldContent = oldContent + line + System.lineSeparator();
	                 
	                line = reader.readLine();
	            }
	             
	            //Replacing oldString with newString in the oldContent
	            
	            
	         //   String newContent = oldContent.replaceAll("resources/style.xml", "resources/report/"+documentId+"/"+reportTabId+"/style.xml");
	           
	            String newContent = oldContent.replaceAll("src/main/resources/style.xml", getClass().getClassLoader().getResource("").getPath().substring(1).replaceAll("%20", " ")+"report/"+documentId+"/"+reportTabId+"/style.xml");
	            
	            
	            //Rewriting the input text file with newContent
	             
	            writer = new FileWriter(fileToBeModified);
	             
	            writer.write(newContent);
	        }
	        catch (IOException e)
	        {
	            e.printStackTrace();
	            logger.error(e);
	        }
	        finally
	        {
	            try
	            {
	                //Closing the resources
	                 
	                reader.close();
	                 
	                writer.close();
	            } 
	            catch (IOException e) 
	            {
	                e.printStackTrace();
	                logger.error(e);
	            }
	        }

		
	}

	public int fetchReportElementsSize(Integer documentId, Integer reportId, AtomicInteger numberOfEmbeddedElements, BOAnalyzerModel boReportModel){
		int boReportElementSize = 0;
		try {
			String uri = "/documents/" + documentId + "/reports/" + reportId + "/elements";
			String output = webClient.get(uri, REQUEST_PROPERTY_APPL_XML, REQUEST_PROPERTY_APPL_JSON);
			
			byte[] jsonOutput = output.getBytes();
			//create ObjectMapper instance
			ObjectMapper objectMapper = new ObjectMapper();

			//read JSON like DOM Parser
			JsonNode rootNode = objectMapper.readTree(jsonOutput);
			JsonNode elementsNode = rootNode.path("elements");
			JsonNode elementNode = elementsNode.path("element");
			boReportElementSize = elementNode.size();
			int fetchVtableSize=0;
			String parenId = "";
			if(elementNode.isArray())
			{
				Iterator<JsonNode> elements = elementNode.elements();
				while(elements.hasNext())
				{
					JsonNode element = elements.next();
					if(element.get("@type").asText().equalsIgnoreCase("VTable"))
					{
						parenId = element.get("id").asText();
						continue;
					}
					if(element.get("@type").asText().equalsIgnoreCase("Cell") && parenId.equalsIgnoreCase(element.get("parentId").asText()))
					{
						fetchVtableSize++;
					}
				}
			}

			boReportElementSize -= fetchVtableSize; 
			
		} 
		catch(Exception ex) {
			boReportModel.setExceptionReport(boReportModel.getExceptionReport().concat("\n" + ex + " in " + ex.getStackTrace()[0].getMethodName()) );
			logger.error(ex);
			ex.printStackTrace();
		}
		return boReportElementSize;
	}
	
	
	public Integer fetchNumberOfObjects(Integer documentId, String dataProviderId, BOAnalyzerModel boReportModel){
		int numberOfObjects =0;
		try {
			String uri = "/documents/" + documentId + "/dataproviders/" + dataProviderId;
			
			String output = webClient.get(uri, REQUEST_PROPERTY_APPL_XML, REQUEST_PROPERTY_APPL_JSON);
			byte[] jsonOutput = output.getBytes();
			//create ObjectMapper instance
			ObjectMapper objectMapper = new ObjectMapper();

			//read JSON like DOM Parser
			JsonNode rootNode = objectMapper.readTree(jsonOutput);
			JsonNode dataproviderNode = rootNode.path("dataprovider");
			boReportModel.getUniverses().add(dataproviderNode.get("dataSourceName").asText());
			//boReportModel.setTotalUniverseCount(boReportModel.getTotalUniverseCount() + 1);
			

			JsonNode dictionaryNode = dataproviderNode.path("dictionary");
			JsonNode expressionNode = dictionaryNode.path("expression");		
			numberOfObjects = expressionNode.size();	
		}
		
		catch(Exception ex) {
			boReportModel.setExceptionReport(boReportModel.getExceptionReport().concat("\n" + ex + " in " + ex.getStackTrace()[0].getMethodName()) );
			logger.error(ex);
			ex.printStackTrace();
		}
		return numberOfObjects;
	}
	
	
	private List<BOVisualElements> calculatePageWidthHeight(List<BOVisualElements> boVisualElementList) {
		
		try {
		// TODO Auto-generated method stub
		double maxWidth=0.0;
		double headerHeight = 0.0;
		double footerHeight = 0.0;
		
		HashMap<String,String> selfJoinMap = selfJoin(boVisualElementList);
		
		String headerId = selfJoinMap.get("Header");
		String footerId = selfJoinMap.get("Footer");
		String bodyId = selfJoinMap.get("Body");
		
		Iterator<BOVisualElements> iter = boVisualElementList.iterator();
		
		while(iter.hasNext())
		{
			BOVisualElements bOVisualElementsObj = iter.next();
			
			String xPositionVal = bOVisualElementsObj.getxPosition();
			String yPositionVal = bOVisualElementsObj.getyPosition();
			String minimalWidth = bOVisualElementsObj.getMinimalWidth();
			String elementId = bOVisualElementsObj.getElementId();
			
			if(elementId.equals(headerId))
			{
				headerHeight = Double.parseDouble(bOVisualElementsObj.getMinimalHeight());
			}
			if(elementId.equals(footerId))
			{
				footerHeight = Double.parseDouble(bOVisualElementsObj.getMinimalHeight());
			}
			
			if(xPositionVal ==null || xPositionVal.isEmpty())
			{
				xPositionVal="0.00";
			}
			if(yPositionVal ==null || yPositionVal.isEmpty())  
			{
				yPositionVal="0.00";
			}
			
			if(minimalWidth ==null || minimalWidth.isEmpty())
			{
				minimalWidth="0.00";
			}
			
			System.out.println("X Position::"+xPositionVal);
			System.out.println("Y Position::"+yPositionVal);
			System.out.println("Minimal Width ::"+minimalWidth);
			//System.out.println("Minimal Height::"+minimalHeight);
			
			double xPosition = Double.parseDouble(xPositionVal);
			double width = Double.parseDouble(minimalWidth); 
			
			double sumWidth = width+xPosition;
			
			if(maxWidth<sumWidth)
			{
				maxWidth=sumWidth;
			}
		}
		
		maxWidth=maxWidth/3600;
		
		BigDecimal bd1=new BigDecimal(maxWidth).setScale(2,RoundingMode.HALF_DOWN);
		
		System.out.println("Padding::"+padding);
		
		maxWidth=bd1.doubleValue()+padding;
		
		
		System.out.println("Maximum Width::"+maxWidth);
		
		
		double pageHeight = calculatePageHeight(boVisualElementList,bodyId,headerHeight,footerHeight);
		
		Iterator<BOVisualElements> iter1 = boVisualElementList.iterator();
		
		while(iter1.hasNext())
		{
			BOVisualElements x = iter1.next();
			x.setMaxWidth(String.valueOf(maxWidth));
			x.setMaxHeight(String.valueOf(pageHeight));
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error(e);
		}
		
		return boVisualElementList;
	}
	private double calculatePageHeight(List<BOVisualElements> boVisualElementList,String bodyId,double headerHeight,double footerHeight) {
		// TODO Auto-generated method stub
		double pageHeight = 0.0;
		double maxHeight=0.0;
		
		try {
		for(int i=0;i<boVisualElementList.size();i++)
		{
			if(!(boVisualElementList.get(i).getParentId()==null) && boVisualElementList.get(i).getParentId().equals(bodyId))
			{
				BOVisualElements bOVisualElementsObj = boVisualElementList.get(i);
				String xPositionVal = bOVisualElementsObj.getxPosition();
				String yPositionVal = bOVisualElementsObj.getyPosition();
				String minimalHeight= bOVisualElementsObj.getMinimalHeight();
				
				
				if(xPositionVal ==null || xPositionVal.isEmpty())
				{
					xPositionVal="0.00";
				}
				if(yPositionVal ==null || yPositionVal.isEmpty())  
				{
					yPositionVal="0.00";
				}
				if(minimalHeight ==null || minimalHeight.isEmpty())
				{
					minimalHeight="0.00";
				}
				
				
				System.out.println("X Position::"+xPositionVal);
				System.out.println("Y Position::"+yPositionVal);
			//	System.out.println("Minimal Width ::"+minimalWidth);
				System.out.println("Minimal Height::"+minimalHeight);
				
				double yPosition = Double.parseDouble(yPositionVal);
				
				double height = Double.parseDouble(minimalHeight); 
				
				double sumHeight = height+yPosition;
				
				if(maxHeight<sumHeight)
				{
					maxHeight=sumHeight;
				}
			}

		}
		
		maxHeight=maxHeight/3600;
		
		BigDecimal bd2=new BigDecimal(maxHeight).setScale(2,RoundingMode.HALF_DOWN);
		
		//maxHeight is Approximate Body Height
		maxHeight=bd2.doubleValue()+padding; 
		
		headerHeight = headerHeight/3600;
		BigDecimal bd3=new BigDecimal(headerHeight).setScale(2,RoundingMode.HALF_DOWN);
		headerHeight = bd3.doubleValue();
		
		
		footerHeight = footerHeight/3600;
		BigDecimal bd4=new BigDecimal(footerHeight).setScale(2,RoundingMode.HALF_DOWN);
		footerHeight = bd4.doubleValue();
		
		pageHeight = maxHeight + headerHeight + footerHeight;
		
		BigDecimal bd=new BigDecimal(pageHeight).setScale(2,RoundingMode.HALF_DOWN);
		pageHeight = bd.doubleValue();
		
		
		System.out.println("Approximate Page Height::"+pageHeight);
		}
		catch(Exception e)
		{
			logger.error(e);
			e.printStackTrace();
		}
		
		return pageHeight;
	}
	
	public HashMap<String,String> selfJoin(List<BOVisualElements> boVisualElementList)
	{
		HashMap<String,String> selfJoinMap = new HashMap<String,String>();
		try {
		for(int i=0;i<boVisualElementList.size();i++)
		{
			if(boVisualElementList.get(i).getType().equals("Page Zone"))
			{
				selfJoinMap.put(boVisualElementList.get(i).getElementName(),boVisualElementList.get(i).getElementId());
			}
		}
		}
		catch(Exception e)
		{
			logger.error(e);
			e.printStackTrace();
		}
		return selfJoinMap;
	}
	private List<Zones> getLayoutData(String layoutData)
	{
		List<Zones> zonesList = new LinkedList<Zones>();
		try {
		layoutData = layoutData.substring(7,layoutData.length());
		String[] zonesSplitArray = layoutData.split(";");
		for(int i=0;i<zonesSplitArray.length;i++)
		{
			List<Child> childList = getChildData(zonesSplitArray[i]);
			Zones zone = new Zones();
			zone.setChildList(childList);
			zonesList.add(zone);
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error(e);
		}
		
		
		return zonesList;
	}
	
	private List<Child> getChildData(String layoutData)
	{
		List<Child> layoutList = new LinkedList<Child>();
		
		try {
		
		String[] splitLayoutArray = layoutData.split("\\|");
		for(int i=0;i<splitLayoutArray.length;i++)
		{
			Child layoutObj = fetchLayoutData(splitLayoutArray[i]);
			layoutList.add(layoutObj);
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error(e);
		}
		return layoutList;
	}
	
	private Child fetchLayoutData(String layoutData)
	{
		Child layoutObj = new Child();
		try
		{
			layoutData = layoutData.substring(8,layoutData.length()-2);
			String[] layoutDataSplitArray = layoutData.split(",");
			
			String row = layoutDataSplitArray[0].split(":")[1];
			String rowSpan = layoutDataSplitArray[1].split(":")[1];
			String column = layoutDataSplitArray[2].split(":")[1];
			String columnSpan = layoutDataSplitArray[3].split(":")[1];
			String id = layoutDataSplitArray[4].split(":")[1];
			
			layoutObj.setRow(row);
			layoutObj.setRowSpan(rowSpan);
			layoutObj.setColumn(column);
			layoutObj.setColumnSpan(columnSpan);
			layoutObj.setId(id);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error(e);
		}
		return layoutObj;
	}

	private List<Axes> getAxesData(String axesData, String identifier) {
		// TODO Auto-generated method stub
		
		List<Axes> axesList = new LinkedList<Axes>();
		try {
		
		axesData = axesData.substring(5,axesData.length());
		
		Pattern pattern = Pattern.compile(Pattern.quote("#^"));
	//	String[] data = pattern.split("#^");
	//	String[] splitAxisArray = axesData.split(";");
		String[] splitAxisArray = pattern.split(axesData);
		
		
		
		for(int i=0;i<splitAxisArray.length;i++)
		{
			
			if(identifier.equalsIgnoreCase("Visualization"))
			{
				Axes axesObj = fetchAxisData(splitAxisArray[i]);
				axesList.add(axesObj);
			}
			else 
			{
				Axes axesObj = fetchAxisDataTable(splitAxisArray[i]);
				axesList.add(axesObj);
			}
			
			//setData(splitAxisArray[i]);
			
		}
		}
		catch(Exception e)
		{
			logger.error(e);
			e.printStackTrace();
		}

		return axesList;
	}



	private Axes fetchAxisData(String axisData) {
		// TODO Auto-generated method stub
		Axes axesObj = new Axes();
		try
		{
			axisData = axisData.substring(1,axisData.length()-1);
			
			String[] axisDataSplitArray = axisData.split("` ");
			
			String role = axisDataSplitArray[0].split(":")[1];
			System.out.println("role::"+role);
			axesObj.setRole(role);
			
			String name = axisDataSplitArray[1].split(":")[1];
			if(axisDataSplitArray.length==2)
			{
				System.out.println("Name::"+name);
				axesObj.setName(name.substring(0,name.length()-1));
			}

			
			if(axisDataSplitArray.length>2)
			{
				axesObj.setName(name);
				
				String formula = axisDataSplitArray[2];
				
				List<AxisFormula> axisFormulaList = new LinkedList<AxisFormula>();

				if(formula.contains("|"))
				{
					String [] formulaArray = formula.split("\\|");
					
					for(int i=0;i<formulaArray.length;i++)
					{
						System.out.println("Formula Array of visualization::"+formulaArray[i]);
						
						if(i==formulaArray.length-1)
						{
							formulaArray[i] = formulaArray[i].substring(10,formulaArray[i].length()-1);
						}
						else
						{
							formulaArray[i] = formulaArray[i].substring(10,formulaArray[i].length()-2);
						}
						
						System.out.println("Formula Array of visualization After::"+formulaArray[i]);
						AxisFormula axisFormula = setFormulaDetails(formulaArray[i]);
						axisFormulaList.add(axisFormula);
					}
				}
				else
				{
					formula = formula.substring(10,formula.length()-1);
					AxisFormula axisFormula =setFormulaDetails(formula);
					axisFormulaList.add(axisFormula);
				}
				axesObj.setFormulaList(axisFormulaList);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error(e);
		}
		

		return axesObj;
	}
	
	
	private Axes fetchAxisDataTable(String axisData) {
		// TODO Auto-generated method stub
		Axes axesObj = new Axes();
		
		try
		{
			axisData = axisData.substring(1,axisData.length()-1);
			
			String[] axisDataSplitArray = axisData.split("` ");
			
			String role = axisDataSplitArray[0].split(":")[1];
			if(axisDataSplitArray.length==1)
			{
				
				System.out.println("role::"+role);
				axesObj.setRole(role.substring(0,role.length()-1));
			}
			
		//	String name = axisDataSplitArray[1].split(":")[1];
		//	System.out.println("Name::"+name);
		//	axesObj.setName(name);
			
			
			else if(axisDataSplitArray.length>1)
			{
				axesObj.setRole(role);
				
				String formula = axisDataSplitArray[1];
				
				List<AxisFormula> axisFormulaList = new LinkedList<AxisFormula>();

				if(formula.contains("|"))
				{
					String [] formulaArray = formula.split("\\|");
					
					for(int i=0;i<formulaArray.length;i++)
					{
						System.out.println("Formula for Table before::"+formulaArray[i]);
						if(i==formulaArray.length-1)
						{
							formulaArray[i] = formulaArray[i].substring(10,formulaArray[i].length()-1);
						}
						else
						{
							formulaArray[i] = formulaArray[i].substring(10,formulaArray[i].length()-2);
						}
						System.out.println("Formula for Table Aftre::"+formulaArray[i]);
						AxisFormula axisFormula = setFormulaDetails(formulaArray[i]);
						axisFormulaList.add(axisFormula);
					}
				}
				else
				{
					formula = formula.substring(10,formula.length()-1);
					AxisFormula axisFormula =setFormulaDetails(formula);
					axisFormulaList.add(axisFormula);
				}
				axesObj.setFormulaList(axisFormulaList);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error(e);
		}
		

		return axesObj;
	}


	private AxisFormula setFormulaDetails(String formula) {
		// TODO Auto-generated method stub
		//AxisFormula axisFormula = new AxisFormula();
		
		
		AxisFormula axisFormulaObj = new AxisFormula();
		
		try {
		
		String formulaNameSplitVal = formula.split(", ")[0];
		
		String formulaName =null;
		if(formulaNameSplitVal.split(":").length>1)
		{
			formulaName = formulaNameSplitVal.split(":")[1];
			
		}
		axisFormulaObj.setName(formulaName);
		
		String hideSplitVal = formula.split(", ")[1];
		
		String hide =null;
		if(hideSplitVal.split(":").length>1)
		{
			hide = hideSplitVal.split(":")[1];	
		}
		axisFormulaObj.setHide(hide);
		
		String dataTypeSplit = formula.split(", ")[2];
		String dataType =null;
		if(dataTypeSplit.split(":").length>1)
		{
			dataType = dataTypeSplit.split(":")[1];
		
		}
		axisFormulaObj.setDataType(dataType);
		
		String dataObjectIdSplit = formula.split(", ")[3];
		String dataObjectId =null;
		if(dataObjectIdSplit.split(":").length>1)
		{
			dataObjectId = dataObjectIdSplit.split(":")[1];
		
		}
		axisFormulaObj.setDataObjectId(dataObjectId);
		
		String qualificationSplit = formula.split(", ")[4];
		String qualification =null;
		if(qualificationSplit.split(":").length>1)
		{
			qualification = qualificationSplit.split(":")[1];
		
		}
		axisFormulaObj.setQualification(qualification);
		}
		catch(Exception e)
		{
			logger.error(e);
			e.printStackTrace();
		}

		return axisFormulaObj;
		
	}
	
	private List<InputControl> fetchInputControlList(Integer documentId,BOAnalyzerModel boReportModel) throws Exception
	{
		List<InputControl> inputControlList =  new ArrayList<InputControl>();
		
		String uri = "/documents/" + documentId + "/inputcontrols";
		String ip = webClient.get(uri, REQUEST_PROPERTY_APPL_XML, REQUEST_PROPERTY_APPL_XML);
		Document doc;
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			doc = builder.parse(new InputSource(new StringReader(ip)));
			NodeList list = doc.getElementsByTagName("inputcontrol");
			for(int i = 0; i < list.getLength(); i ++) {
				Node n = list.item(i);
				if(n.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) n;
					String inputControlId = e.getElementsByTagName("id").item(0).getChildNodes().item(0).getNodeValue();
					
						
						inputControlList.add(fetchInputControlData(documentId, inputControlId, boReportModel));	
					
					
							
				}
			}
		} 
		catch (Exception ex) {
			boReportModel.setExceptionReport(boReportModel.getExceptionReport().concat("\n" + ex + " in " + ex.getStackTrace()[0].getMethodName()) );
			ex.printStackTrace();
			logger.error(ex);
			return inputControlList;
		}
		
		
		return inputControlList;
	}
	
	public InputControl fetchInputControlData(int documentId, String inputControlId, BOAnalyzerModel boReportModel) {
		InputControl boinputControl = new InputControl();
		String uri = "/documents/" + documentId + "/inputcontrols/" + inputControlId;
		String ip = webClient.get(uri, REQUEST_PROPERTY_APPL_XML, REQUEST_PROPERTY_APPL_XML);
		Document doc;
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			doc = builder.parse(new InputSource(new StringReader(ip)));
			Node n = doc.getElementsByTagName("inputcontrol").item(0);
			if(n.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) n;
				boinputControl.setInputControlId(e.getElementsByTagName("id").item(0).getChildNodes().item(0).getNodeValue());
				boinputControl.setInputControlName(e.getElementsByTagName("name").item(0).getChildNodes().item(0).getNodeValue());
				Node dataObjectNode = e.getElementsByTagName("assignedDataObject").item(0);
				Element dataObjectElement = (Element)dataObjectNode;
				String objectId = dataObjectElement.getAttribute("refId");
				String kind = dataObjectElement.getAttribute("kind");
				
				if(kind.equals("Expression"))
				{
					boinputControl.setObjectName(fetchDataProviderObjectName(documentId,objectId));
					
				}
				else if(kind.equals("Variable"))
				{
					boinputControl.setObjectName(fetchInputControlVariableObjectName(boReportModel.getBoVariableList(),objectId));
					
				}
				
				if(e.getElementsByTagName("radioButtons").getLength()!=0)
				{
				Node radioButtonNode = e.getElementsByTagName("radioButtons").item(0);
				Element radioButtonElement = (Element)radioButtonNode;
				String operator =  radioButtonElement.getAttribute("operator");
				boinputControl.setOperator(operator);
				
				List<String> customValues = new ArrayList<String>();
				String defaultval = "";
				if(radioButtonElement.getAttribute("useCustom").equals("true"))
				{
					
					Node customNode =  radioButtonElement.getElementsByTagName("custom").item(0);
					Element customElement = (Element)customNode;
					NodeList customValueList = customElement.getElementsByTagName("value");
					
					
					
					for(int i=0;i<customValueList.getLength();i++)
					{
						
						String customValue = customValueList.item(i).getChildNodes().item(0).getNodeValue();
		
						
						customValues.add(customValue);
					}
					boinputControl.setCustomListOfValues(customValues);	
				}
				if(radioButtonElement.getElementsByTagName("default").getLength()!=0)
				{
				Node defaultNode = radioButtonElement.getElementsByTagName("default").item(0);
				Element defaultElement = (Element)defaultNode;
				NodeList defaultValue = defaultElement.getElementsByTagName("value");

				defaultval = defaultValue.item(0).getChildNodes().item(0).getNodeValue();	
				boinputControl.setDefaultValue(defaultval);
				}
				
				}
				else if(e.getElementsByTagName("textField").getLength()!=0)
				{
					Node textFieldNode = e.getElementsByTagName("textField").item(0);
					Element textFieldElement = (Element)textFieldNode;
					String operator =  textFieldElement.getAttribute("operator");
					boinputControl.setOperator(operator);
					List<String> customValues = new ArrayList<String>();
					String defaultval = "";
					if(textFieldElement.getAttribute("useCustom").equals("true"))
					{
						
						Node customNode =  textFieldElement.getElementsByTagName("custom").item(0);
						Element customElement = (Element)customNode;
						NodeList customValueList = customElement.getElementsByTagName("value");
						
						
						for(int i=0;i<customValueList.getLength();i++)
						{
							
							String customValue = customValueList.item(i).getChildNodes().item(0).getNodeValue();
			
							
							customValues.add(customValue);
						}	
						boinputControl.setCustomListOfValues(customValues);	
						
					}
					if(textFieldElement.getElementsByTagName("default").getLength()!=0)
					{
					Node defaultNode = textFieldElement.getElementsByTagName("default").item(0);
					Element defaultElement = (Element)defaultNode;
					NodeList defaultValue = defaultElement.getElementsByTagName("value");
		
					defaultval = defaultValue.item(0).getChildNodes().item(0).getNodeValue();
					boinputControl.setDefaultValue(defaultval);
					}
					
				}
					
			}
		} 
		catch (Exception ex) {
			boReportModel.setExceptionReport(boReportModel.getExceptionReport().concat("\n" + ex + " in " + ex.getStackTrace()[0].getMethodName()));
			ex.printStackTrace();
			logger.error(ex);
			return boinputControl;
		}
		
		return boinputControl;
	}
	private String fetchInputControlVariableObjectName(List<BOVariable> boVariableList,String objectId)
    {
    	String objectName="";
    	try {
    	HashMap<String,String> idNameMap = new HashMap<String,String>();
    	for(int i=0;i<boVariableList.size();i++)
    	{
    		if(!idNameMap.containsKey(boVariableList.get(i).getId()))
    		{
    			idNameMap.put(boVariableList.get(i).getId(),boVariableList.get(i).getName());
    		}
    	}
    	objectName = idNameMap.get(objectId);
    	}
    	catch(Exception e)
    	{
    		logger.error(e);
    		e.printStackTrace();
    	}
    	return objectName;	
    }
	
private String fetchDataProviderObjectName(Integer documentId, String objectId){
		
		
		
		String objectName = "";
		
		HashMap<String,String> expressionMap = new HashMap<String,String>();
		try {
			String[] dataProviderArray = objectId.split("\\.");
			String dataProviderId = dataProviderArray[0];
			
			String uri = "/documents/" + documentId + "/dataproviders/" + dataProviderId;
			String output = webClient.get(uri, REQUEST_PROPERTY_APPL_XML, REQUEST_PROPERTY_APPL_JSON);
			
			byte[] jsonOutput = output.getBytes();
			//create ObjectMapper instance
			ObjectMapper objectMapper = new ObjectMapper();

			//read JSON like DOM Parser
			JsonNode rootNode = objectMapper.readTree(jsonOutput);
			JsonNode dataproviderNode = rootNode.path("dataprovider");
		
			JsonNode dictionaryNode = dataproviderNode.path("dictionary");
			JsonNode expressionNode = dictionaryNode.path("expression");
			
			if(expressionNode.isArray()){
				Iterator<JsonNode> elements = expressionNode.elements();
				while(elements.hasNext()){
					JsonNode expression = elements.next();
					
					expressionMap.put(expression.get("id").asText(),expression.get("name").asText());
					

				}
			}
			else {
				expressionMap.put(expressionNode.get("id").asText(),expressionNode.get("name").asText());
				
			}
			
			objectName = expressionMap.get(objectId);
			
		}
		
		catch(Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return objectName;
	}

private List<Alerters> fetchAlertersList(Integer documentId,BOAnalyzerModel boReportModel) throws Exception
{
	List<Alerters> alertersList =  new ArrayList<Alerters>();
	
	String uri = "/documents/" + documentId + "/alerters";
	String ip = webClient.get(uri, REQUEST_PROPERTY_APPL_XML, REQUEST_PROPERTY_APPL_XML);
	Document doc;
	try {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		doc = builder.parse(new InputSource(new StringReader(ip)));
		NodeList list = doc.getElementsByTagName("alerter");
		for(int i = 0; i < list.getLength(); i ++) {
			Node n = list.item(i);
			if(n.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) n;
				String alerterId = e.getElementsByTagName("id").item(0).getChildNodes().item(0).getNodeValue();
				
					
				  alertersList.add(fetchAlerterData(documentId,alerterId, boReportModel));	
				
				
						
			}
		}
	} 
	catch (Exception ex) {
		boReportModel.setExceptionReport(boReportModel.getExceptionReport().concat("\n" + ex + " in " + ex.getStackTrace()[0].getMethodName()) );
		ex.printStackTrace();
		logger.error(ex);
		return alertersList;
	}
	
	
	return alertersList;
}
public Alerters fetchAlerterData(int documentId, String alerterId, BOAnalyzerModel boReportModel) {
	Alerters boAlerters = new Alerters();
	String uri = "/documents/" + documentId + "/alerters/" + alerterId;
	String ip = webClient.get(uri, REQUEST_PROPERTY_APPL_XML, REQUEST_PROPERTY_APPL_XML);
	Document doc;
	try {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		doc = builder.parse(new InputSource(new StringReader(ip)));
		Node n = doc.getElementsByTagName("alerter").item(0);
		if(n.getNodeType() == Node.ELEMENT_NODE) {
			Element e = (Element) n;
			boAlerters.setAlerterId(e.getElementsByTagName("id").item(0).getChildNodes().item(0).getNodeValue());
			boAlerters.setAlerterName(e.getElementsByTagName("name").item(0).getChildNodes().item(0).getNodeValue());
		//	boAlerters.setRuleList(fetchRuleList(documentId,alerterId));
			List<Rule> rulesList = new ArrayList<Rule>();
			
			NodeList ruleNodeList = e.getElementsByTagName("rule");
			for(int i=0;i<ruleNodeList.getLength();i++)
			{		
				Rule rule = new Rule();
				Node ruleNode = ruleNodeList.item(i);
				Element ruleElement = (Element)ruleNode;
				rule.setRuleId(ruleElement.getElementsByTagName("id").item(0).getChildNodes().item(0).getNodeValue());
				Node conditionNode = ruleElement.getElementsByTagName("condition").item(0);
				Element conditionElement = (Element)conditionNode;
				if(conditionElement != null)
				{
				rule.setExpressionId(conditionElement.getAttribute("expressionId"));
				rule.setOperator(conditionElement.getAttribute("operator"));
				if(conditionElement.getElementsByTagName("operand").item(0)!=null)
				rule.setOperand(conditionElement.getElementsByTagName("operand").item(0).getChildNodes().item(0).getNodeValue());
				}
				Node colorNode = ruleElement.getElementsByTagName("color").item(0);
				Element colorElement = (Element) colorNode;
				if(colorElement != null)
				rule.setActionBackgroundColor(colorElement.getAttribute("rgb"));
				rulesList.add(rule);
			}
			boAlerters.setRuleList(rulesList);
				
		}
	} 
	catch (Exception ex) {
		boReportModel.setExceptionReport(boReportModel.getExceptionReport().concat("\n" + ex + " in " + ex.getStackTrace()[0].getMethodName()));
		ex.printStackTrace();
		logger.error(ex);
		return boAlerters;
	}
	
	return boAlerters;
}


private List<DataFilter> fetchDataFiltersList(Integer documentId,BOAnalyzerModel boReportModel) throws Exception
{
	List<DataFilter> dataFiltersList =  new ArrayList<DataFilter>();
	
	String uri = "/documents/" + documentId + "/reports";
	String ip = webClient.get(uri, REQUEST_PROPERTY_APPL_XML, REQUEST_PROPERTY_APPL_XML);
	Document doc;
	try {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		doc = builder.parse(new InputSource(new StringReader(ip)));
		NodeList list = doc.getElementsByTagName("report");
		for(int i = 0; i < list.getLength(); i ++) {
			Node n = list.item(i);
			if(n.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) n;
				DataFilter dataReportFilter = new DataFilter();
				String reportId = e.getElementsByTagName("id").item(0).getChildNodes().item(0).getNodeValue();
				String reportName = e.getElementsByTagName("name").item(0).getChildNodes().item(0).getNodeValue();
				String booleanDatafilter = e.getAttribute("hasDatafilter");
				if(booleanDatafilter.equals("true"))
				{
					
						dataReportFilter = fetchReportDataFilter(documentId,reportId,reportName);
						dataFiltersList.add(dataReportFilter);	
				}
				 List<DataFilter> elementList = fetchElementDataFilterList(documentId,reportId);
				 for(int j = 0;j<elementList.size();j++)
				 {
					 dataFiltersList.add(elementList.get(j));
				 }	
			}
		}
	} 
	catch (Exception ex) {
		boReportModel.setExceptionReport(boReportModel.getExceptionReport().concat("\n" + ex + " in " + ex.getStackTrace()[0].getMethodName()) );
		ex.printStackTrace();
		logger.error(ex);
		return dataFiltersList;
	}
	
	
	return dataFiltersList;
}
public DataFilter fetchReportDataFilter(int documentId, String reportId,String reportName) {
	DataFilter dataReportFilter = new DataFilter();
	String uri = "/documents/" + documentId + "/reports/" + reportId + "/datafilter";
	String ip = webClient.get(uri, REQUEST_PROPERTY_APPL_XML, REQUEST_PROPERTY_APPL_XML);
	Document doc;
	try {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		doc = builder.parse(new InputSource(new StringReader(ip)));
		Node n = doc.getElementsByTagName("condition").item(0);
		if(n.getNodeType() == Node.ELEMENT_NODE) {
			Element e = (Element) n;
			dataReportFilter.setId(reportId);
			dataReportFilter.setName(reportName);
			dataReportFilter.setFilterType("Report");
			dataReportFilter.setOperator(e.getAttribute("operator"));
			dataReportFilter.setConditionKey(e.getAttribute("key"));
			dataReportFilter.setConditionValue(e.getElementsByTagName("value").item(0).getChildNodes().item(0).getNodeValue());	
		}
	} 
	catch (Exception ex) {
		//boReportModel.setExceptionReport(boReportModel.getExceptionReport().concat("\n" + ex + " in " + ex.getStackTrace()[0].getMethodName()));
		ex.printStackTrace();
		logger.error(ex);
		return dataReportFilter;
	}
	
	return dataReportFilter;
}
public List<DataFilter> fetchElementDataFilterList(int documentId, String reportId) {
	List<DataFilter> dataElementFilterList = new ArrayList<DataFilter>();
	String uri = "/documents/" + documentId + "/reports/" + reportId + "/elements";
	String ip = webClient.get(uri, REQUEST_PROPERTY_APPL_XML, REQUEST_PROPERTY_APPL_XML);
	Document doc;
	try {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		doc = builder.parse(new InputSource(new StringReader(ip)));
		NodeList list = doc.getElementsByTagName("element");
		for(int i = 0; i < list.getLength(); i ++) {
			Node n = list.item(i);
			if(n.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) n;
				if(e.getAttribute("hasDatafilter")!=null && e.getAttribute("hasDatafilter").equals("true") && !e.getAttribute("hasDatafilter").isEmpty())
				{
					String elementId = e.getElementsByTagName("id").item(0).getChildNodes().item(0).getNodeValue();
					String elementName = e.getElementsByTagName("name").item(0).getChildNodes().item(0).getNodeValue();
					
					dataElementFilterList.add(fetchElementDataFilter(documentId,reportId,elementId,elementName));	
				}		
						
			}
		}
	} 
	catch (Exception ex) {
		//boReportModel.setExceptionReport(boReportModel.getExceptionReport().concat("\n" + ex + " in " + ex.getStackTrace()[0].getMethodName()));
		ex.printStackTrace();
		logger.error(ex);
		return dataElementFilterList;
	}
	
	return dataElementFilterList;
}
public DataFilter fetchElementDataFilter(int documentId, String reportId,String elementId,String elementName) {
	DataFilter dataElementFilter = new DataFilter();
	String uri = "/documents/" + documentId + "/reports/" + reportId + "/elements/" + elementId + "/datafilter";
	String ip = webClient.get(uri, REQUEST_PROPERTY_APPL_XML, REQUEST_PROPERTY_APPL_XML);
	Document doc;
	try {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		doc = builder.parse(new InputSource(new StringReader(ip)));
		Node n = doc.getElementsByTagName("condition").item(0);
		if(n.getNodeType() == Node.ELEMENT_NODE) {
			Element e = (Element) n;
			dataElementFilter.setId(elementId);
			dataElementFilter.setName(elementName);
			dataElementFilter.setReportTabId(reportId);
			dataElementFilter.setFilterType("Element");
			dataElementFilter.setOperator(e.getAttribute("operator"));
			dataElementFilter.setConditionKey(e.getAttribute("key"));
			dataElementFilter.setConditionValue(e.getElementsByTagName("value").item(0).getChildNodes().item(0).getNodeValue());	
		}
	} 
	catch (Exception ex) {
		//boReportModel.setExceptionReport(boReportModel.getExceptionReport().concat("\n" + ex + " in " + ex.getStackTrace()[0].getMethodName()));
		ex.printStackTrace();
		logger.error(ex);
		return dataElementFilter;
	}
	
	return dataElementFilter;
}


private List<String> checkDataFilterExist(Integer documentId)
{
		List<String> hasDataFiltersList = new ArrayList<String>();
		String uri = "/documents/" + documentId + "/reports";
		String ip = webClient.get(uri, REQUEST_PROPERTY_APPL_XML, REQUEST_PROPERTY_APPL_XML);
		Document doc;
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			doc = builder.parse(new InputSource(new StringReader(ip)));
			NodeList list = doc.getElementsByTagName("report");
			for(int i = 0; i < list.getLength(); i ++) {
				Node n = list.item(i);
				if(n.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) n;
				String reportId = e.getElementsByTagName("id").item(0).getChildNodes().item(0).getNodeValue();
				String booleanDatafilter = e.getAttribute("hasDatafilter");
				if(booleanDatafilter.equals("true"))
				{
				hasDataFiltersList.add(reportId);
				}
			}
		}
		}
		catch (Exception ex) {
		// boReportModel.setExceptionReport(boReportModel.getExceptionReport().concat("\n" + ex + " in " + ex.getStackTrace()[0].getMethodName()) );
		ex.printStackTrace();
		logger.error(ex);
		return hasDataFiltersList;
		}
		
		return hasDataFiltersList;
}
private BOQueryFilter fetchBoQueryFilterList(Integer documentId,String dataProviderId) throws Exception
{
	BOQueryFilter boQueryFilter = new BOQueryFilter();
    String uri = "/documents/" + documentId + "/dataproviders/"+dataProviderId+"/specification";
	String ip = webClient.get(uri,"*/*","*/*");
	
	Document doc;
	try {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		doc = builder.parse(new InputSource(new StringReader(ip)));
		boQueryFilter.setDataProviderId(dataProviderId);
		
		Node operatorNode = doc.getElementsByTagName("conditionTree").item(0);
		if(operatorNode != null)
		{
		if(operatorNode.getNodeType() == Node.ELEMENT_NODE) {
			Element operatorElement = (Element) operatorNode;
			if(!operatorElement.getAttribute("logicalOperator").trim().isEmpty())
			{
				boQueryFilter.setLogicalOperator(operatorElement.getAttribute("logicalOperator"));
			}
			}
		List<QueryChildren> queryChildrenList = new ArrayList<QueryChildren>();
		NodeList list = doc.getElementsByTagName("children");
		for(int i = 0; i < list.getLength(); i ++) {
			Node n = list.item(i);
			if(n.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) n;
				QueryChildren queryChildren = new QueryChildren();
				Node conditionNode = e.getElementsByTagName("condition").item(0);
				
				Element conditionElement = (Element) conditionNode;
				if(conditionElement!=null)
				{
				
				if(conditionElement.getAttribute("itemIdentifier") != null)
				queryChildren.setItemIdentifier(conditionElement.getAttribute("itemIdentifier"));
				if(conditionElement.getAttribute("itemName")!= null)
				queryChildren.setItemName(conditionElement.getAttribute("itemName"));
				if(conditionElement.getAttribute("comparisonOperator")!=null)
				queryChildren.setComparisonOperator(conditionElement.getAttribute("comparisonOperator"));
				
				List<String> operandValuesList = new ArrayList<String>();
				
				Node operandNode = conditionElement.getElementsByTagName("operands").item(0);
				Element operandElement = (Element) operandNode;
				
				String operandType = operandElement.getAttribute("xsi:type");
				operandType = operandType.substring(10);
				queryChildren.setOperandType(operandType);
				
				if(operandType.equals("ConstantOperand"))
				{
					NodeList valuesList = operandElement.getElementsByTagName("members");
					for(int j = 0;j<valuesList.getLength();j++)
					{
						Node valueNode = valuesList.item(j);
						if(valueNode.getNodeType() == Node.ELEMENT_NODE)
						{
							Element valueElement = (Element) valueNode;
							Node captionNode = valueElement.getElementsByTagName("captionField").item(0);
							Element captionElement = (Element) captionNode;
							operandValuesList.add(captionElement.getAttribute("value"));
						}
					}
					queryChildren.setOperandValues(operandValuesList);
				}
				else if(operandType.equals("QueryPromptOperand"))
				{
					queryChildren.setQuestion(operandElement.getAttribute("question"));
				}
				queryChildrenList.add(queryChildren);
			}
			}
		}

		boQueryFilter.setQueryChildrenList(queryChildrenList);
		}
	} 
	catch (Exception ex) {
	//	boReportModel.setExceptionReport(boReportModel.getExceptionReport().concat("\n" + ex + " in " + ex.getStackTrace()[0].getMethodName()) );
		ex.printStackTrace();
		logger.error(ex);
		return boQueryFilter;
	}
	
	
	return boQueryFilter;
}
private HashMap<String,ArrayList<String>> fetchSelectColumns(String sql) throws JSQLParserException 
{
	HashMap<String,ArrayList<String>> tableMap = new HashMap<String,ArrayList<String>>();
	try {
    Statement statement = CCJSqlParserUtil.parse(sql);
    Select selectStatement = (Select) statement;
    TablesNamesFinder tablesNamesFinder = new TablesNamesFinder() {
        @Override
        public void visit(Column tableColumn) {
           if(tableMap.containsKey(tableColumn.getTable().toString()))
           {
        	  ArrayList<String> existingList = tableMap.get(tableColumn.getTable().toString());
        	  existingList.add(tableColumn.getColumnName().toString());
        	  tableMap.put(tableColumn.getTable().toString(),existingList);
           }
           else
           {
        	   ArrayList<String> columnList = new ArrayList<String>();
        	   columnList.add(tableColumn.getColumnName().toString());
        	   tableMap.put(tableColumn.getTable().toString(),columnList);
           }
    
        }
    };
    tablesNamesFinder.getTableList(selectStatement);
	}
	catch(Exception e)
	{
		logger.error(e);
		e.printStackTrace();
	}
           
    return tableMap;
}
private HashMap<String,String> fetchColumnAlias(String sql) throws JSQLParserException 
{
	HashMap<String,String> tableMap = new HashMap<String,String>();
	try {
    Statement statement = CCJSqlParserUtil.parse(sql);
    Select selectStatement = (Select) statement;
    for (SelectItem selectItem : ((PlainSelect)selectStatement.getSelectBody()).getSelectItems()) {
    selectItem.accept(new SelectItemVisitorAdapter() {
    @Override
    public void visit(SelectExpressionItem item) {
    if(item.getAlias()!=null)
    {
    	if(item.getExpression().toString().contains(","))
    	{
    		int index = item.getExpression().toString().indexOf(",");
	    	String columnName = item.getExpression().toString().substring(0,index);
	        tableMap.put(columnName.split("\\.")[1],item.getAlias().getName());
    	}
    	else
    	{
    		tableMap.put(item.getExpression().toString().split("\\.")[1],item.getAlias().getName());
    	}
    }
    }
    });
    }
	}
	catch(Exception e)
	{
		logger.error(e);
		e.printStackTrace();
	}
           
    return tableMap;
}

private HashMap<String,ArrayList<String>> fetchWhereColumns(String sql) throws JSQLParserException 
{
	HashMap<String,ArrayList<String>> tableMap = new HashMap<String,ArrayList<String>>();
	try {
    Statement statement = CCJSqlParserUtil.parse(sql);
    Select selectStatement = (Select) statement;
    if(((PlainSelect)selectStatement.getSelectBody()).getWhere()!=null)
    {
		   ((PlainSelect)selectStatement.getSelectBody()).getWhere().accept(new ExpressionVisitorAdapter() {
		   @Override
		   public void visit(Column column) {
		   if(tableMap.containsKey(column.getTable().toString()))
           {
        	  ArrayList<String> existingList = tableMap.get(column.getTable().toString());
        	  existingList.add(column.getColumnName().toString());
        	  tableMap.put(column.getTable().toString(),existingList);
           }
           else
           {
        	   ArrayList<String> columnList = new ArrayList<String>();
        	   columnList.add(column.getColumnName().toString());
        	   tableMap.put(column.getTable().toString(),columnList);
           }
		   }
		   });
    }
	}
	catch(Exception e)
	{
		logger.error(e);
		e.printStackTrace();
	}
    return tableMap;
}
private HashMap<String,ArrayList<String>> fetchGroupByColumns(String sql) throws JSQLParserException 
{
	HashMap<String,ArrayList<String>> groupByTableMap = new HashMap<String,ArrayList<String>>();
	try {
    Statement statement = CCJSqlParserUtil.parse(sql);
    Select selectStatement = (Select) statement;
    
    if(((PlainSelect)selectStatement.getSelectBody()).getGroupBy()!=null) 
    {
    	List<Expression> groupByList=new ArrayList<Expression>();
        groupByList=((PlainSelect)selectStatement.getSelectBody()).getGroupBy().getGroupByExpressions();
        for(int i=0;i<groupByList.size();i++) 
        {
        	String groupByColumnExpression=groupByList.get(i).toString();
        	if(groupByColumnExpression.contains("."))
        	{
        	if(groupByColumnExpression.contains("(") && groupByColumnExpression.contains(",")) {
        		int index1= groupByColumnExpression.indexOf("(");
        		int index2=groupByColumnExpression.indexOf(",");
        		groupByColumnExpression=groupByColumnExpression.substring(index1+1, index2);
        		}
        		else if(groupByColumnExpression.contains("(")) {
        		int index1=groupByColumnExpression.indexOf("(");
        		int index2=groupByColumnExpression.indexOf(")");
        		groupByColumnExpression=groupByColumnExpression.substring(index1+1, index2);
        	}
            String groupByTable = groupByColumnExpression.split("\\.")[0];
            if(groupByTable.contains("||"))
            groupByTable=groupByTable.substring(7);
            String groupByColumn = groupByColumnExpression.split("\\.")[1];

            if(!groupByTableMap.containsKey(groupByTable)) {
            ArrayList<String> tempList=new ArrayList<String>();
            tempList.add(groupByColumn);
            groupByTableMap.put(groupByTable,tempList);
            }
            else {
            groupByTableMap.get(groupByTable).add(groupByColumn);
            }
        }
     
        }
    }
	}
	catch(Exception e)
	{
		e.printStackTrace();
		logger.error(e);
	}
    return groupByTableMap;
}

private List<String> fetchDimensionMeasureAttributeList(List<BOReportQuery> boQueries)
{
		List<String> dList = new ArrayList<String>();
		List<String> mList = new ArrayList<String>();
		List<String> aList = new ArrayList<String>();
		List<String> dmList = new ArrayList<String>();
		try {
		for(int i=0;i<boQueries.size();i++)
		{
			BOReportQuery boQuery = boQueries.get(i);
			List<BOReportQueryColumn> columns = boQuery.getBoReportQueryColumns();
			for(int j=0;j<columns.size();j++)
			{
				BOReportQueryColumn boColumn = columns.get(j);
				if(boColumn.getColumnQualification().equals("Dimension"))
				{
					dList.add(boColumn.getColumnExpression());
				}
				else if(boColumn.getColumnQualification().equals("Measure"))
				{
					mList.add(boColumn.getColumnExpression());
				}
				
				else if(boColumn.getColumnQualification().equals("Attribute"))
				{
					aList.add(boColumn.getColumnExpression());
				}
			}
			
		}
		
		dmList.add(dList.toString());
		dmList.add(mList.toString());
		dmList.add(aList.toString());
		}
		catch(Exception e)
		{
			logger.error(e);
			e.printStackTrace();
		}
		return dmList;
}


private String fetchCommonalityVariableList(List<BOVariable> boVariablesList)
{
		List<String> variableList = new ArrayList<String>();
		try {
		
		for(int i=0;i<boVariablesList.size();i++)
		{
			BOVariable v = boVariablesList.get(i);
			variableList.add(v.getFormulaLanguageId());
		}	
		}
		catch(Exception e)
		{
			logger.error(e);
			e.printStackTrace();
		}
		return variableList.toString();
}

private String convertReportDate(String date)
{
	String convertedDate = "";
	try {
	String dateWithoutTimestamp = date.trim().split("T")[0];
	String year = dateWithoutTimestamp.split("-")[0];
	String month = Month.of(Integer.parseInt(dateWithoutTimestamp.split("-")[1])).name().substring(0, 3);
	String day = dateWithoutTimestamp.split("-")[2];
	convertedDate += day+" "+month+" "+year+" ";
	
	String timestamp = date.trim().split("T")[1];
	String hours = timestamp.split(":")[0];
	String minutes = timestamp.split(":")[1];
	String seconds = timestamp.split(":")[2].split("\\.")[0];
	
	convertedDate += hours+":"+minutes+":"+seconds;
	}
	catch(Exception e)
	{
		e.printStackTrace();
		logger.error(e);
	}
	
	return convertedDate;
}



}
