package com.recast.recast.bo.analyzer.controller;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recast.recast.bo.analyzer.model.AnalysisReportModel;
import com.recast.recast.bo.analyzer.model.CommonalityReportModel;
import com.recast.recast.bo.analyzer.model.ComplexityReportModel;
import com.recast.recast.bo.analyzer.model.PrjRptAnalysisModel;
import com.recast.recast.bo.analyzer.model.ReportTypeUniverseModel;
import com.recast.recast.bo.analyzer.model.ReportUniverseModel;
import com.recast.recast.bo.analyzer.model.ReportUserModel;
import com.recast.recast.bo.analyzer.model.TableauDatasourceMap;
import com.recast.recast.bo.analyzer.model.UniverseReportModel;
import com.recast.recast.bo.analyzer.service.CommonalityParamsService;
import com.recast.recast.bo.analyzer.service.PrjReportAnalysisService;
import com.recast.recast.bo.analyzer.util.ReportConstants;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/analyzer/")
public class PrjReportAnalysisController {
	private static Logger logger = LoggerFactory.getLogger(PrjReportAnalysisController.class);
	
	@Autowired(required = false)
	PrjReportAnalysisService projAnalysisService;
	
	
	/**
	 * <p>
	 * When sending an HTTP Request, we will need to call a method in the service
	 * that will apply the desired action. Hence, the service is autowired.
	 * </p>
	 */
	@Autowired(required = false)
	private CommonalityParamsService commonalityParamsService;
	

	@PostMapping("/addTask")
	@Async
	public String addTask(@RequestBody PrjRptAnalysisModel pm){
		//System.out.println("Project Model::"+pm.getSelectedReportsList());
		//PrjRptAnalysisModel p = projAnalysisService.save(pm);
		//System.out.println("Project Model ppp::"+p.getSelectedReportsList());
		PrjRptAnalysisModel p = null;
		
		if(pm.getReportTypeCode().equalsIgnoreCase(ReportConstants.REPORT_TYPE_BO) || pm.getReportTypeCode().equalsIgnoreCase(ReportConstants.REPORT_TYPE_TABLEAU))
		{
			 p = projAnalysisService.save(pm);
		}
		
		
		
		String op;
		try {
			op = projAnalysisService.analyzeConnection(p).get();
			if(pm.getReportTypeCode().equalsIgnoreCase(ReportConstants.REPORT_TYPE_BO))
			{
				int taskId = p.getId();
				
				List<List<String>> list = new ArrayList<List<String>>();
				list = commonalityParamsService.similarityEngineProcess(taskId);
			
				
			}
			
		} 
		catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			op = "Failed";
		}
		return op;
	}
	
	@GetMapping("/{id}")
	public List<AnalysisReportModel> getAnalysis(@PathVariable int id) {
		logger.info("Inside get Analysis for: " + id); 
		return projAnalysisService.getAnalysis(id);
	}
	
	@GetMapping("/{id}/Commonality")
	public List<CommonalityReportModel> getCommonality(@PathVariable int id) {
		logger.info("Inside get Commonality for: " + id);
		return projAnalysisService.getCommonality(id);
	}
	
	@GetMapping("/{id}/universe")
	public List<UniverseReportModel> getUniverse(@PathVariable int id) {
		logger.info("Inside get Universe for: " + id);
		return projAnalysisService.getUniverse(id);
	}
	
	@GetMapping("/{id}/Complexity")
	public List<ComplexityReportModel> getComplexity(@PathVariable int id) {
		logger.info("Inside get Complexity for: " + id);
		
		return projAnalysisService.getComplexity(id);
	}
	
	@DeleteMapping("removeTasks/{id}")
	public int removeTasks(@PathVariable int id) {
		projAnalysisService.deleteTask(id);
		return id;
	}
	
	
	
	
	@GetMapping("/reportUser/count/{id}")
	public List<ReportUserModel> getReportUserCount(@PathVariable int id)
	{
		System.out.println("inside Report USer Count");
		
		Map<String,Integer> reportUserMap = new LinkedHashMap<String, Integer>();
		List<AnalysisReportModel> reportModelData = projAnalysisService.getAnalysis(id);

		List<ReportUserModel> list = new LinkedList<ReportUserModel>();
		Iterator<AnalysisReportModel> iter = reportModelData.iterator();
		
		while(iter.hasNext())
		{
			AnalysisReportModel reportModel = iter.next();
			
			String key = reportModel.getReportUser();
			
			if(reportUserMap.containsKey(key))
			{
				reportUserMap.put(key, reportUserMap.get(key)+1);
			}
			else
			{
				reportUserMap.put(key, 1);
			}
			
		}
		
		System.out.println(reportUserMap);
		
		 for (Map.Entry<String,Integer> entry : reportUserMap.entrySet())
		 {
			 ReportUserModel userModel = new ReportUserModel();
			 userModel.setReportUser(entry.getKey());
			 userModel.setCount(entry.getValue());
			 list.add(userModel);
		 }
		
		
		return list;
	}
	
	@GetMapping("/universe/count/{id}")
	public List<ReportUniverseModel> getReportUniverseCount(@PathVariable int id)
	{
		System.out.println("inside Report Universe Count");
		
		Map<String,Integer> reportUniMap = new LinkedHashMap<String, Integer>();
		List<AnalysisReportModel> reportModelData = projAnalysisService.getAnalysis(id);

		List<ReportUniverseModel> list = new LinkedList<ReportUniverseModel>();
		Iterator<AnalysisReportModel> iter = reportModelData.iterator();
		
		while(iter.hasNext())
		{
			AnalysisReportModel reportModel = iter.next();
			
			String key = reportModel.getUniverseName();

			if(reportUniMap.containsKey(key))
			{
				reportUniMap.put(key, reportUniMap.get(key)+1);
			}
			else
			{
				reportUniMap.put(key, 1);
			}
			
		}
		
		System.out.println(reportUniMap);
		
		 for (Map.Entry<String,Integer> entry : reportUniMap.entrySet())
		 {
			 ReportUniverseModel uniModel = new ReportUniverseModel();
			 uniModel.setUniverseName(entry.getKey());
			 uniModel.setCount(entry.getValue());
			 
			 List<ReportTypeUniverseModel> listReportTypeUni =getReportTypeUniModel(entry.getKey(),reportModelData);
			 uniModel.setReportTypeModel(listReportTypeUni);
			 list.add(uniModel);
		 }
		
		
		return list;
	}
	
	@GetMapping("/{id}/tableauReportDetails")
	public List<TableauDatasourceMap> getTableauReportDetails(@PathVariable int id) {
		System.out.println("Inside report details for Tableau");
		return projAnalysisService.getTableauReportDetails(id);
	}
	

	private List<ReportTypeUniverseModel> getReportTypeUniModel(String uniName,List<AnalysisReportModel> reportModelData) {
		// TODO Auto-generated method stub
		
		List<ReportTypeUniverseModel> listReportTypeUni = new LinkedList<ReportTypeUniverseModel>();
		Map<String,Integer> reportTypeMap = new LinkedHashMap<String, Integer>();
		
		Iterator<AnalysisReportModel> iterator = reportModelData.iterator();
	
		while(iterator.hasNext())
		{
			AnalysisReportModel model = iterator.next();
			String universeName= model.getUniverseName();
			String reportType = model.getReportType();
			if(universeName.equalsIgnoreCase(uniName))
			{
				if(reportTypeMap.containsKey(reportType))
				{
					reportTypeMap.put(reportType, reportTypeMap.get(reportType)+1);
				}
				else
				{
					reportTypeMap.put(reportType, 1);
				}
			}
		}
		 for (Map.Entry<String,Integer> entry : reportTypeMap.entrySet())
		 {
			 ReportTypeUniverseModel reportUniModel = new ReportTypeUniverseModel();
			 reportUniModel.setReportType(entry.getKey());
			 reportUniModel.setCount(entry.getValue());
			 
			 listReportTypeUni.add(reportUniModel);
		 }
		return listReportTypeUni;
		
	}
	
	
	
}
