package com.recast.recast.bo.analyzer.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.recast.recast.bo.analyzer.dto.ReportConfigDTO;
import com.recast.recast.bo.analyzer.dto.TableauReportConfigDTO;
import com.recast.recast.bo.analyzer.service.ReportAnalyzer;
import com.recast.recast.bo.analyzer.service.TableauReportAnalyzerService;
import com.recast.recast.bo.analyzer.tableau.model.ReportData;

public class TableauReportAnalyzer implements ReportAnalyzer{

	private static Logger LOG = Logger.getLogger(TableauReportAnalyzer.class);
	private  TableauReportConfigDTO config;
	
	public TableauReportAnalyzer(ReportConfigDTO config) {
		LOG.info("Creating TableauReportAnalyzer instance....");
		this.config=(TableauReportConfigDTO)config;
	}
	
	public Map<String, Object> analyze() throws Exception {
		
		return TableauReportAnalyzerService.extractMetadata(this.config);
				
	}
	
	public String testTableauAnalyzerConnection() throws Exception
	{
		String res = TableauReportAnalyzerService.testTableauConnection(this.config);
		//System.out.println("RESULT FROM SERVER::"+res);
		return res;
	}
	
	public Map<String,List<ReportData>> reportPathExtraction() throws MalformedURLException, IOException
	{
		Map<String,List<ReportData>> reportPath =  TableauReportAnalyzerService.extractReportFolderPath(config);
		return reportPath;
	}
	
	public static void main(String[] args) throws Exception {
		
		TableauReportConfigDTO config = new TableauReportConfigDTO();
		
		config.setHostname("172.21.106.66");
		config.setUsername("user1");
		config.setPassword("user1");
//		config.setPath("D:\\Projects\\Tableau\\Split Into 3\\Split Into 3 Pages Example VP.twb");
//		config.setPath("D:\\Projects\\ReCast-latest\\Tableau-Recast\\LTT product Dashboard migration - Metadata[1].xml");
		//config.setPath("D:\\Recast 2.0\\Tableau\\Report XML Path\\Shop Wise Amount Sold.xml");
//		config.setPath("D:\\Projects\\Tableau\\Account Tracking.twb");
//		config.setPath("D:\\Projects\\Tableau\\PL Tableau 10.5 Test Report\\PL Tableau 10.5 Test.twb");
		config.setPath("C:\\Users\\10675932\\Documents\\LTI Workspace\\Docs\\Tableau Files\\Account Tracking.xml");
		config.setPort("8080");
		
		//String path ="D:\\Recast 2.0\\Tableau\\Report XML Path\\LTT product Dashboard migration - Metadata[1].xml";
		Map<String, Object> map = TableauReportAnalyzerService.extractMetadata(config);
		System.out.println("Reports::::"+map.get("reports"));
		System.out.println("Datasources:::" + map.get("datasources"));
//		
//		Map<String, Object> datasourceMap = ReportAnalyzerService.extractSemanticData(config);
//		System.out.println("Datasources:::" + datasourceMap.get("datasources"));
		
//		ReportAnalyzerService.logonToTableau();
		
//		ReportAnalyzerService.getWorkbooksInfo(config);
		
//		ReportAnalyzerService.getWorkbookDetails();
		
		TableauReportAnalyzerService.extractReportPathData();

	}

}
