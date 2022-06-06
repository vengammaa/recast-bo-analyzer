package com.recast.recast.bo.analyzer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recast.recast.bo.analyzer.model.ReportPathModel;
import com.recast.recast.bo.analyzer.service.ReportPathService;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/reportPath")
public class ReportPathController {

	@Autowired(required = false)
	ReportPathService reportPathService;
	
	@GetMapping("getFolderDetails/{reportType}/{connectionId}")
	public List<ReportPathModel> getReportPathDetails(@PathVariable String reportType, @PathVariable int connectionId)
	{
		List<ReportPathModel> reportModelList = reportPathService.fetchReportPath(reportType,connectionId);
		
		return reportModelList;
		
	}
	
}
