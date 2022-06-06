package com.recast.recast.bo.analyzer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.recast.recast.bo.analyzer.entity.AnalysisReport;

public interface AnalysisReportRepository extends JpaRepository<AnalysisReport, Integer>{
	List<AnalysisReport> findByPrjRptAnalysisId (int prjRptAnalysisId);

	@Query(value="select task.report_path, report.report_name, report.column_names," +
			"report.universe_name from recast.folder_task as task, recast.analysis_report as " + 
			"report where task.prj_folder_analysis_id = report.prj_rpt_analysis_id and report.prj_rpt_analysis_id=?1", nativeQuery = true)
			List<List<String>> findTableauReportDetailsByTaskId(int taskId);
	
}
