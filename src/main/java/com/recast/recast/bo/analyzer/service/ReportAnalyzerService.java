package com.recast.recast.bo.analyzer.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.recast.recast.bo.analyzer.dto.BoReportConfigDTO;
import com.recast.recast.bo.analyzer.model.BOAnalyzerModel;
import com.recast.recast.bo.analyzer.model.BOChartElement;
import com.recast.recast.bo.analyzer.model.BOCommonalityModel;
import com.recast.recast.bo.analyzer.model.BOQueryCommonalityVO;
import com.recast.recast.bo.analyzer.model.BOQueryDetailsModel;
import com.recast.recast.bo.analyzer.model.BOReportQuery;
import com.recast.recast.bo.analyzer.model.BOReportQueryColumn;
import com.recast.recast.bo.analyzer.model.BOReportTab;
import com.recast.recast.bo.analyzer.model.BOTableElement;
import com.recast.recast.bo.analyzer.model.QueryFilter;
import com.recast.recast.bo.analyzer.model.ReportData;
import com.recast.recast.bo.analyzer.model.TablesNamesFinderExt;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.TablesNamesFinder;

public class ReportAnalyzerService {
	private static Logger logger = Logger.getLogger(ReportAnalyzerService.class);
	
	public static Map<String, Object> extractMetadata(BoReportConfigDTO config) throws Exception {
		logger.info("Extract metadata called for BO");
		BOAnalyzerAPI boAnalyzerAPI = new BOAnalyzerAPI();
		Map<String, Object> boAnalyzerModelList = boAnalyzerAPI.fetchBOAnalyzerModelList(config);
		return boAnalyzerModelList;
	}
	
	public static List<BOCommonalityModel> computeMeanCommonality(List<BOAnalyzerModel> boReportModelList) {
		
		List<BOCommonalityModel> output = new ArrayList<BOCommonalityModel>();
		try {
			for (int i = 0; i < boReportModelList.size(); i++) {
				for (int j = i + 1; j < boReportModelList.size(); j++) {

					BOAnalyzerModel sourceBoReportModel = boReportModelList.get(i);
					BOAnalyzerModel destinationBoReportModel = boReportModelList.get(j);
					
					double queryCommonality = computeQueryCommonality(sourceBoReportModel, destinationBoReportModel);
					double tableCommonality = computeTableCommonality(sourceBoReportModel, destinationBoReportModel);
					double chartCommonality = computeChartCommonality(sourceBoReportModel, destinationBoReportModel);
					double filterCommonality = computeFilterCommonality(sourceBoReportModel, destinationBoReportModel);
					
					int denominator = sourceBoReportModel.getCommonalityFactor() > destinationBoReportModel.getCommonalityFactor() 
							? sourceBoReportModel.getCommonalityFactor():destinationBoReportModel.getCommonalityFactor();
	
					double totalCommonality = queryCommonality + tableCommonality + chartCommonality + filterCommonality;
					
					int meanCommonality = (int) Math.round(((totalCommonality/denominator))*100)/100;
					
					if(meanCommonality > 100){
						meanCommonality = 100;
					}
					boolean identical = false;
					if(meanCommonality == 100){
						if (sourceBoReportModel.getReportName().contains(".")&&!destinationBoReportModel.getReportName().contains(".")){
							String a = sourceBoReportModel.getReportName().substring(0,sourceBoReportModel.getReportName().indexOf("."));
							if (a.equalsIgnoreCase(destinationBoReportModel.getReportName())){
								 identical = true;
							}
						}
						else if (destinationBoReportModel.getReportName().contains(".")&&!sourceBoReportModel.getReportName().contains(".")){
							String b = destinationBoReportModel.getReportName().substring(0,destinationBoReportModel.getReportName().indexOf("."));
							if (b.equalsIgnoreCase(sourceBoReportModel.getReportName())){
								 identical = true;
							}
						}
						else if (sourceBoReportModel.getReportName().contains(".")&&destinationBoReportModel.getReportName().contains(".")){
							String a = sourceBoReportModel.getReportName().substring(0,sourceBoReportModel.getReportName().indexOf("."));
							String b = destinationBoReportModel.getReportName().substring(0,destinationBoReportModel.getReportName().indexOf("."));
							if(a.equalsIgnoreCase(b)){
								 identical = true;
							}
						}
						else if (sourceBoReportModel.getReportName().equalsIgnoreCase(destinationBoReportModel.getReportName())){
							identical = true;
						}
					}
					
					output.add(new BOCommonalityModel(sourceBoReportModel, destinationBoReportModel, meanCommonality, identical));


				}
			}


		}
		catch(Exception e){
			logger.error(e.getMessage());
		}
		return output;
	}

	public static double computeFilterCommonality(BOAnalyzerModel sourceBoReportModel,
			BOAnalyzerModel destinationBoReportModel) {
		double filterCommonality = 0;
		Set<QueryFilter> sourceFilterSet = new LinkedHashSet<QueryFilter>();
		Set <QueryFilter> destinationFilterSet = new LinkedHashSet<QueryFilter>();
		for(BOReportTab boReportTab : sourceBoReportModel.getBoReportTabList()){
			if(boReportTab != null && boReportTab.getQueryFilters() != null && boReportTab.getQueryFilters().size() > 0){
				sourceFilterSet.addAll(boReportTab.getQueryFilters());
			}
		}
		for(BOReportTab boReportTab : destinationBoReportModel.getBoReportTabList()){
			if(boReportTab != null && boReportTab.getQueryFilters() != null && boReportTab.getQueryFilters().size() > 0){
				destinationFilterSet.addAll(boReportTab.getQueryFilters());
			}
		}
		if (!(sourceFilterSet.isEmpty() || destinationFilterSet.isEmpty())){
			double denominator = destinationFilterSet.size() + sourceFilterSet.size();
			destinationFilterSet.retainAll(sourceFilterSet);
			denominator -= destinationFilterSet.size();
			filterCommonality = (double)(destinationFilterSet.size()/denominator)*100;
		}
		return filterCommonality;
	}

	public static double computeTableCommonality(BOAnalyzerModel sourceDocument, BOAnalyzerModel destinationDocument){
		double tableCommonality = 0;
		List<BOTableElement> sourceTableElementList = new ArrayList<BOTableElement>();
		List<BOTableElement> destinationTableElementList = new ArrayList<BOTableElement>();
		for(BOReportTab boReportTab : sourceDocument.getBoReportTabList()){
			if(boReportTab != null && boReportTab.getBoTableElements() != null && boReportTab.getBoTableElements().size() > 0){
				sourceTableElementList.addAll(boReportTab.getBoTableElements());
			}
		}
		for(BOReportTab boReportTab : destinationDocument.getBoReportTabList()){
			if(boReportTab != null && boReportTab.getBoTableElements() != null && boReportTab.getBoTableElements().size() > 0){
				destinationTableElementList.addAll(boReportTab.getBoTableElements());
			}
		}
		if (!(sourceTableElementList.isEmpty() || destinationTableElementList.isEmpty())){
			double denominator = sourceTableElementList.size() + destinationTableElementList.size();
			destinationTableElementList.retainAll(sourceTableElementList);
			denominator -= destinationTableElementList.size();
			tableCommonality = (double)(destinationTableElementList.size()/denominator)*100;
		}
		return tableCommonality;
	}
	
	public static double computeChartCommonality(BOAnalyzerModel sourceBoReportModel, BOAnalyzerModel destinationBoReportModel){
		double chartCommonality = 0;
		List<BOChartElement> sourceChartElementList = new ArrayList<BOChartElement>();
		List<BOChartElement> destinationChartElementList = new ArrayList<BOChartElement>();
		for(BOReportTab boReportTab : sourceBoReportModel.getBoReportTabList()){
			if(boReportTab != null && boReportTab.getBoChartElements() != null && boReportTab.getBoChartElements().size() > 0){
				sourceChartElementList.addAll(boReportTab.getBoChartElements());
			}
		}
		for(BOReportTab boReportTab : destinationBoReportModel.getBoReportTabList()){
			if(boReportTab != null && boReportTab.getBoChartElements() != null && boReportTab.getBoChartElements().size() > 0){
				destinationChartElementList.addAll(boReportTab.getBoChartElements());
			}
		}
		if (!(sourceChartElementList.isEmpty() || destinationChartElementList.isEmpty())){
			double denominator = sourceChartElementList.size() + destinationChartElementList.size();
			destinationChartElementList.retainAll(sourceChartElementList);
			denominator -= destinationChartElementList.size();
			chartCommonality = (double)(destinationChartElementList.size()/denominator)*100;
		}
		return chartCommonality;
	}
	
	public static double computeQueryCommonality(BOAnalyzerModel sourceBoReportModel, BOAnalyzerModel destinationBoReportModel){
		List <BOQueryCommonalityVO> finalQueryCommonalityList = new ArrayList<BOQueryCommonalityVO>();
		double overallMeanQueryCommonality = 0;
		if (!(sourceBoReportModel.getBoReportQueries().isEmpty() || destinationBoReportModel.getBoReportQueries().isEmpty())){
			List<BOReportQuery> sourceQueryList = null;
			List<BOReportQuery> destinationQueryList = null;
			if (sourceBoReportModel.getBoReportQueries().size() > destinationBoReportModel.getBoReportQueries().size()){
				sourceQueryList = sourceBoReportModel.getBoReportQueries();
				destinationQueryList = destinationBoReportModel.getBoReportQueries();
			}else {
				sourceQueryList = destinationBoReportModel.getBoReportQueries();
				destinationQueryList = sourceBoReportModel.getBoReportQueries();
			}
			for (int i = 0; i < sourceQueryList.size(); i++){
				List<BOQueryCommonalityVO> tempQueryCommonalityList = new ArrayList<BOQueryCommonalityVO>();
				for (int j = 0; j < destinationQueryList.size(); j++){
					BOReportQuery sourceQuery = sourceQueryList.get(i);
					BOReportQuery destinationQuery = destinationQueryList.get(j);
					double columnCommonality = 0; 
					List <BOReportQueryColumn> srcQueryColumnList = new ArrayList<BOReportQueryColumn>(sourceQuery.getBoReportQueryColumns());
					List <BOReportQueryColumn> destQueryColumnList = new ArrayList<BOReportQueryColumn>(destinationQuery.getBoReportQueryColumns());
					if (!srcQueryColumnList.isEmpty() && !destQueryColumnList.isEmpty()){
						double columnDenominator = srcQueryColumnList.size() + destQueryColumnList.size();
						destQueryColumnList.retainAll(srcQueryColumnList);
						double commonColumns = destQueryColumnList.size();
						columnDenominator -= commonColumns;
						columnCommonality = (commonColumns / columnDenominator) * 100;
					}
					double meanQueryCommonality = columnCommonality;
					tempQueryCommonalityList.add(new BOQueryCommonalityVO(sourceQuery, destinationQuery, meanQueryCommonality));
				}
				Collections.sort(tempQueryCommonalityList);
				if(!tempQueryCommonalityList.isEmpty())
					finalQueryCommonalityList.add(tempQueryCommonalityList.get(0));
			}
			double totalMeanCommonality = 0;
			for (BOQueryCommonalityVO queryCommonality : finalQueryCommonalityList)
				totalMeanCommonality += queryCommonality.getCommonality();
		
			overallMeanQueryCommonality = totalMeanCommonality/finalQueryCommonalityList.size();
		}
		return overallMeanQueryCommonality;
	}

	public static String testBOConnection(BoReportConfigDTO config) {
		// TODO Auto-generated method stub
		BOAnalyzerAPI boAnalyzerAPI = new BOAnalyzerAPI();
		String result = boAnalyzerAPI.testConnection(config);
		System.out.println("Resukt::"+result);
		return result;
	}
	
	

	public static Map<String,List<ReportData>> extractReportFolderPath(BoReportConfigDTO config) {
		// TODO Auto-generated method stub
		BOAnalyzerAPI boAnalyzerAPI = new BOAnalyzerAPI();
		
		Map<String,List<ReportData>> reportPathData = boAnalyzerAPI.extractReportPathData(config);
		
		return reportPathData;
	}

	public static List<BOQueryDetailsModel> fetchQueryDetails(List<BOAnalyzerModel> bOModelList) {
		// TODO Auto-generated method stub
		List<BOQueryDetailsModel> queryDetailsList = new ArrayList<BOQueryDetailsModel>();
		
		bOModelList.forEach(x->{

			String reportName = x.getReportName();
			int reportId = x.getReportId();

			List<BOReportQuery> reportQueryList = x.getBoReportQueries();
			
			reportQueryList.forEach(y->{
				
				String queryName = y.getQueryName();
				String dataSourceName = y.getDataSourceName();
				
				List<String> query = y.getQueryStatements();
				
				query.forEach(z->{
					
					HashMap<String,ArrayList<String>> tableMap = new HashMap<String,ArrayList<String>>();
					try {
				    Statement statement = CCJSqlParserUtil.parse(z);
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
				    
				    TablesNamesFinderExt finder = new TablesNamesFinderExt();
					
						Select select = (Select) CCJSqlParserUtil.parse(z);
				        finder = new TablesNamesFinderExt();
				        finder.getTableList(select);
				        List<String> tableList = finder.getSelectTableList();
				        List<String> aliasList = finder.getSelectTableAlliasList();
				        
				        
				        for(int i=0;i<tableList.size();i++) {
				        	BOQueryDetailsModel queryModel = new BOQueryDetailsModel();
				        	
				        	queryModel.setReportId(reportId);
				        	queryModel.setReportName(reportName);
				        	queryModel.setQueryName(queryName);
				        	queryModel.setDataSourceName(dataSourceName);
				        	queryModel.setTableName(tableList.get(i));
				        	queryModel.setTableAliasName(aliasList.get(i));
				        	if(tableMap.containsKey(tableList.get(i)))
				        	{
				        	ArrayList<String> columnList = tableMap.get(tableList.get(i));
				        	JSONObject convertJSON = new JSONObject();
				        	convertJSON.put("columns", columnList);
				        
				        	queryModel.setColumnList(convertJSON.toString());
				        	}
				        	queryDetailsList.add(queryModel);
				        	
				        }
				      
					
			        
			        
					} 
					
					
					catch(JSQLParserException e)
					{
						e.printStackTrace();
					}
					
				});
				
				
				
			});
			
		//	x.setBoQueryDetailsList(queryDetailsList);
		});

		
		System.out.println("Model List::"+queryDetailsList);
		
		return queryDetailsList;
		
	}
	
	
//	public static Map<String,BONewReportComplexity> extractComplexityDetails(BoReportConfigDTO config) {
//		// TODO Auto-generated method stub
//		BOAnalyzerAPI boAnalyzerAPI = new BOAnalyzerAPI();
//		
//		Map<String,BONewReportComplexity> complexityData = boAnalyzerAPI.fetchComplexityDetails(config);
//		
//		return complexityData;
//	}
	
	
}
