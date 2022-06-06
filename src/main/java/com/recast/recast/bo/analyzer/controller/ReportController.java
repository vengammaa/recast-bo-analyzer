package com.recast.recast.bo.analyzer.controller;

import java.io.BufferedReader;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.recast.recast.bo.analyzer.model.PrjRptAnalysisModel;
import com.recast.recast.bo.analyzer.model.PrjRptMigratorModel;
import com.recast.recast.bo.analyzer.model.ProjectReportConModel;
import com.recast.recast.bo.analyzer.model.ReportTypeModel;
import com.recast.recast.bo.analyzer.service.ReportService;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/reports")
public class ReportController {
	@Autowired(required = false)
	private ReportService reportServ;
	private Logger logger = LoggerFactory.getLogger(ReportController.class);
	@GetMapping("/reportTypes")
	public List<ReportTypeModel> getReportTypes() {
		return reportServ.getReportTypes();
	}
	
	@RequestMapping(path = "/connections/{projectId}/{reportType}", method = RequestMethod.GET, consumes = "application/json")
	public List<ProjectReportConModel> getConnections(@PathVariable int projectId,@PathVariable String reportType) {
		return reportServ.getConnections(projectId, reportType);
	}
	
	@RequestMapping(path = "/getTasks/{projectId}", method = RequestMethod.GET)
	public List<PrjRptAnalysisModel> getTasks(@PathVariable int projectId) {
		logger.debug("Inside get tasks");
		return reportServ.getTasks(projectId);
	}
	
	
	//Added by Kalpesh for getting the tasks for migrator
	@RequestMapping(path = "/getMigratorTasks/{projectId}", method = RequestMethod.GET)
	public List<PrjRptMigratorModel> getMigratorTasks(@PathVariable int projectId) {
		logger.debug("Inside get tasks");
		return reportServ.getTasksMigrator(projectId);
	}
	
	@RequestMapping(path="/getSourceConnectionTasks/{projectId}/{connectionId}",method= RequestMethod.GET)
	public List<PrjRptAnalysisModel> getSourceTask(@PathVariable int projectId,@PathVariable int connectionId)
	{
		return reportServ.getSourceTaskUniverse(projectId,connectionId);
		
	}
	
	//Code Called for python Calling
	@GetMapping("/pythonCall")
	public String getPythonCodeCalled() {
		String command = "python D:\\kalpesh\\python\\automating_PowerBI.py";
		
		try {
			Process proc = Runtime.getRuntime().exec(command);
			BufferedReader reader =
					new BufferedReader(new java.io.InputStreamReader(proc.getInputStream()));
			
			String line = "";
			
			while((line = reader.readLine()) != null) {
				System.out.print(line + "\n");
			}
			
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return "Fail";
		}
		
		return "Success";
	}
	
	
	
}
