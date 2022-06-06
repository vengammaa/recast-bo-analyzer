package com.recast.recast.bo.analyzer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recast.recast.bo.analyzer.model.PrjRptMigratorModel;
import com.recast.recast.bo.analyzer.service.PrjReportMigratorService;


//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/migrator/")
public class PrjReportMigratorController {

		private static Logger logger = LoggerFactory.getLogger(PrjReportMigratorController.class);
	
		@Autowired(required = false)
		PrjReportMigratorService projMigratorService;
		
		@PostMapping("/addTask")
		@Async
		public String addTask(@RequestBody PrjRptMigratorModel pm){
			PrjRptMigratorModel p = projMigratorService.save(pm);
			
			String op;
			try {
				op = projMigratorService.analyzeConnection(p).get();
			} 
			catch (Exception e) {
				logger.error(e.getMessage());
				e.printStackTrace();
				op = "Failed";
			}
			return "Success";
		}
		
		
		
		@DeleteMapping("removeTasks/{id}")
		public int removeTasks(@PathVariable int id) {
			projMigratorService.deleteTask(id);
			return id;
		}
}
