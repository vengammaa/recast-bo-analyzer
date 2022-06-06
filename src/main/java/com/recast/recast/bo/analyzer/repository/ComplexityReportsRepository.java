package com.recast.recast.bo.analyzer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recast.recast.bo.analyzer.entity.ComplexityReport;

public interface ComplexityReportsRepository extends JpaRepository<ComplexityReport,Integer>{

	List<ComplexityReport> findBytaskId(Integer taskId);
	
}
