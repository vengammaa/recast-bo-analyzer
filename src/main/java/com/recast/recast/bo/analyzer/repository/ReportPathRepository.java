package com.recast.recast.bo.analyzer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.recast.recast.bo.analyzer.entity.ReportPath;


@Repository
public interface ReportPathRepository extends JpaRepository<ReportPath,Integer> {
	List<ReportPath> findByPathId(Integer pathId);
	

}
