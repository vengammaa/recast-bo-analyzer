package com.recast.recast.bo.analyzer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recast.recast.bo.analyzer.entity.CommonalityReport;

public interface CommonalityReportRepository extends JpaRepository<CommonalityReport, Integer> {
	List<CommonalityReport> findByPrjRptAnalysisId(int prjRptAnalysisId);
	List<CommonalityReport> deleteByPrjRptAnalysisId(int prjRptAnalysisId);
}
