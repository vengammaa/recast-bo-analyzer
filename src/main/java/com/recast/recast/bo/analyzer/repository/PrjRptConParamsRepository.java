package com.recast.recast.bo.analyzer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.recast.recast.bo.analyzer.entity.PrjRptConParams;

@Repository
public interface PrjRptConParamsRepository extends JpaRepository<PrjRptConParams, Integer> {
	List<PrjRptConParams> findByProjectReportConId(int projectReportConId);	
}
