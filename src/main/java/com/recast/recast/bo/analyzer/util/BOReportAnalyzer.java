package com.recast.recast.bo.analyzer.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.recast.recast.bo.analyzer.dto.BoReportConfigDTO;
import com.recast.recast.bo.analyzer.dto.ReportConfigDTO;
import com.recast.recast.bo.analyzer.model.BOAnalyzerModel;
import com.recast.recast.bo.analyzer.model.BOCommonalityModel;
import com.recast.recast.bo.analyzer.model.BOQueryDetailsModel;
import com.recast.recast.bo.analyzer.model.ReportData;
import com.recast.recast.bo.analyzer.service.ReportAnalyzer;
import com.recast.recast.bo.analyzer.service.ReportAnalyzerService;

public class BOReportAnalyzer implements ReportAnalyzer{

	private static Logger LOG = LoggerFactory.getLogger(BOReportAnalyzer.class);
	private  BoReportConfigDTO config;
	
	public BOReportAnalyzer(ReportConfigDTO config) {
		LOG.info("Creating BoReportAnalyzer instance....");
		this.config=(BoReportConfigDTO)config;
	}
	
	public Map<String, Object> analyze() throws Exception {
		return ReportAnalyzerService.extractMetadata(this.config);

	}
	
	public List<BOCommonalityModel> computeCommonality(List<BOAnalyzerModel> BOModelList) {
		return ReportAnalyzerService.computeMeanCommonality(BOModelList);
	}
	
	public String testBOAnalyzerConnection() throws Exception
	{
		String res = ReportAnalyzerService.testBOConnection(this.config);
		//System.out.println("RESULT FROM SERVER::"+res);
		return res;
	}
	
	public Map<String,List<ReportData>> reportPathExtraction()
	{
		Map<String,List<ReportData>> reportPath =  ReportAnalyzerService.extractReportFolderPath(config);
		
		return reportPath;
	}
	
	/*
	 * public Map<String,BONewReportComplexity> complexityDetailsExtraction() {
	 * Map<String,BONewReportComplexity> complexityData =
	 * ReportAnalyzerService.extractComplexityDetails(this.config); return
	 * complexityData; }
	 */
	
	
	public List<BOQueryDetailsModel> queryParser(List<BOAnalyzerModel> BOModelList) {
		// TODO Auto-generated method stub
		return ReportAnalyzerService.fetchQueryDetails(BOModelList);
	}

	public static void main(String[] args) throws Exception {
		
		BoReportConfigDTO config = new BoReportConfigDTO();
		
		config.setHostname("10.200.249.19");
		config.setUsername("user1");
		config.setPassword("user1");
		//config.setPath("Public Folders/Recast_Reports/Webi_Folder3");
		//config.setPath("Public Folders/Recast_Reports/Demo1");
		config.setPort("8080");
		
		List<String> reportList = new ArrayList<String>();
		reportList.add("35401");
//		reportList.add("45069");
//		reportList.add("6080");
//		reportList.add("5490");
//		reportList.add("57893");
//		reportList.add("41577");
//		reportList.add("35425");
		


		
		config.setReports(reportList);
		Map<String, Object> map = ReportAnalyzerService.extractMetadata(config);
		
		System.out.println("Reports::::"+map.get("visualization"));
//		String res = ReportAnalyzerService.testBOConnection(config);
//		System.out.println("Reports::::"+res);
		

	
	}

	

}
