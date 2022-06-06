package com.recast.recast.bo.analyzer.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.recast.recast.bo.analyzer.model.PrjRptAnalysisModel;
import com.recast.recast.bo.analyzer.model.PrjRptMigratorModel;
import com.recast.recast.bo.analyzer.model.ProjectReportConModel;
import com.recast.recast.bo.analyzer.model.ReportTypeModel;
import com.recast.recast.bo.analyzer.repository.ProjectReportConRepository;
import com.recast.recast.bo.analyzer.repository.ProjectRptAnalysisRepository;
import com.recast.recast.bo.analyzer.repository.ProjectRptMigratorRepository;
import com.recast.recast.bo.analyzer.repository.ReportTypeRepository;
import com.recast.recast.bo.analyzer.repository.UniverseReportRepository;
import com.recast.recast.bo.analyzer.util.ModelBuilder;
@Service
public class ReportService {
	@Autowired
	private ReportTypeRepository reportRepository;
	@Autowired
	private ProjectReportConRepository projectReportConRepo;
	@Autowired
	private ProjectRptAnalysisRepository analysisRepo;
	
	@Autowired
	private ProjectRptMigratorRepository migratorRepo;
	
	@Autowired(required = false)
	private UniverseReportRepository universeReportRepository;
	
	private Logger logger = LoggerFactory.getLogger(ReportService.class);
	
	@Transactional
	public List<ReportTypeModel> getReportTypes() {
		return reportRepository.findAll().stream().map(ModelBuilder::reportTypeModelBuilder).collect(Collectors.toList());
	}
	
	@Transactional
	public List<ProjectReportConModel> getConnections(int id,String reporttype) {
		return projectReportConRepo.findByIdAndReporttype(id, reporttype).stream().map(ModelBuilder::projectReportConModelBuilder).collect(Collectors.toList());
	}
	
	@Transactional
	public List<PrjRptAnalysisModel> getTasks(int id) {
		logger.info("inside gettasks");
		return analysisRepo.findAllByProjectId(id).stream().map(ModelBuilder::projectReportAnalysisModelBuilder).collect(Collectors.toList());
	}
	
	//Added by Kalpesh for Migrator Code
	@Transactional
	public List<PrjRptMigratorModel> getTasksMigrator(int id) {
		logger.info("inside gettasks");
		return migratorRepo.findAllByProjectId(id).stream().map(ModelBuilder::projectReportMigratorModelBuilder).collect(Collectors.toList());
	}
	
	//Added by Kalpesh for getting universe details for source connections
	@Transactional
	public List<PrjRptAnalysisModel> getSourceTaskUniverse(int projectId, int connectionId) {
		// TODO Auto-generated method stub
		logger.info("inside getSourceTaskUniverse");
		return analysisRepo.findTaskByConnId(projectId,connectionId).stream().map(ModelBuilder::projectReportAnalysisModelBuilder).collect(Collectors.toList());
	}

	
}
