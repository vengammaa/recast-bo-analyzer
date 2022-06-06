package com.recast.recast.bo.analyzer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recast.recast.bo.analyzer.entity.AnalysisSemanticColumns;

public interface AnalysisSemanticColumnRepository extends JpaRepository<AnalysisSemanticColumns, Integer> {

	List<AnalysisSemanticColumns> findByTaskId (int taskId);
}
