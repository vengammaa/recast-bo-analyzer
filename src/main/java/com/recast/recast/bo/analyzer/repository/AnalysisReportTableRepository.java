package com.recast.recast.bo.analyzer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recast.recast.bo.analyzer.entity.AnalysisReportsTable;

public interface AnalysisReportTableRepository extends JpaRepository<AnalysisReportsTable, Integer>{
	List<AnalysisReportsTable> deleteByTaskId(int taskId);
	
	List<AnalysisReportsTable> findByTaskId (int taskId);
}
