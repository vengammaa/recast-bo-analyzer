package com.recast.recast.bo.analyzer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.recast.recast.bo.analyzer.entity.RptConParamType;

@Repository
public interface RptConParamTypeRepository extends JpaRepository<RptConParamType, String> {
	List<RptConParamType> findByReportTypeCode(String code);
}
