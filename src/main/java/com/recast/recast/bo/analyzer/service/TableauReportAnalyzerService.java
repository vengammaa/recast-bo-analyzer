package com.recast.recast.bo.analyzer.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.recast.recast.bo.analyzer.dto.TableauReportConfigDTO;
import com.recast.recast.bo.analyzer.tableau.model.ReportData;


public class TableauReportAnalyzerService {

	private static Logger logger = Logger.getLogger(TableauReportAnalyzerService.class);
	
	public static Map<String, Object> extractMetadata(TableauReportConfigDTO config) throws Exception {
		logger.info("Extract metadata called for Tableau"); 
		TableauAnalyzerAPI tabAnalyzerAPI = new TableauAnalyzerAPI();
		Map<String, Object> tabAnalyzerModelList = tabAnalyzerAPI.fetchTabAnalyzerModelList(config);
		return tabAnalyzerModelList;
	}
	
//	public static void logonToTableau() throws MalformedURLException, IOException {
//		TableauAnalyzerAPI.logonAndGetTokenAndSiteID("prod-apnortheast-a.online.tableau.com", "kpra16is@cmrit.ac.in", "Kpra16is@cmr", "recastinternal");
//	}
	
	public static void getWorkbooksInfo(TableauReportConfigDTO config) {
		TableauAnalyzerAPI.fetchTableauWorkbookInfo(config);
	}
	
	public static void getWorkbookDetails() {
		TableauAnalyzerAPI.fetchTableauWorkbookTwbAsString("82e3bc02-4457-4de3-a2a1-9df9ee012227");
	}
	
	public static void extractReportPathData() throws MalformedURLException, IOException {
//		TableauAnalyzerAPI.extractReportPathData();
	}
	
	public static String testTableauConnection(TableauReportConfigDTO config) {
		TableauAnalyzerAPI tabAnalyzerAPI = new TableauAnalyzerAPI();
		String result = tabAnalyzerAPI.testConnection(config);
		return result;
	}
	
	public static Map<String,List<ReportData>> extractReportFolderPath(TableauReportConfigDTO config) throws MalformedURLException, IOException {
		// TODO Auto-generated method stub
		TableauAnalyzerAPI tabAnalyzerAPI = new TableauAnalyzerAPI();
		Map<String,List<ReportData>> reportPathData = tabAnalyzerAPI.extractReportPathData(config);
		
		return reportPathData;
	}
	
}
