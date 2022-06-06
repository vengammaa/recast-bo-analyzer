package com.recast.recast.bo.analyzer.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recast.recast.bo.analyzer.model.ReportTabModel;
import com.recast.recast.bo.analyzer.service.ReportTabService;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/reportTab")
public class ReportTabController {
	private Logger logger = LoggerFactory.getLogger(ReportTabController.class);

	@Autowired(required = false)
	private ReportTabService reportTabService;
	
	@GetMapping("/getTabDetails/{taskId}")
	public List<ReportTabModel> getReportTab(@PathVariable int taskId) throws Exception
	{
		return reportTabService.getReportElementList(taskId);
	}
	
	
	
	

}
