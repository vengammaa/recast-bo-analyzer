package com.recast.recast.bo.analyzer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.recast.recast.bo.analyzer.entity.TaskStatus;

@Repository
public interface TaskStatusRepository extends JpaRepository<TaskStatus, String>{

}
