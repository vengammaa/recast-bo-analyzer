package com.recast.recast.bo.analyzer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recast.recast.bo.analyzer.entity.PrjRptMigrator;

public interface ProjectRptMigratorRepository extends JpaRepository<PrjRptMigrator, Integer> {

	List<PrjRptMigrator> findAllByProjectId(int id);
}
