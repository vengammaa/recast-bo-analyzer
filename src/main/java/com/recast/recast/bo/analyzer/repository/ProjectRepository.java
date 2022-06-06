package com.recast.recast.bo.analyzer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.recast.recast.bo.analyzer.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
	@Query(value = "SELECT project.* FROM project INNER JOIN project_user_xref ON project.id = project_user_xref.project_id WHERE project_user_xref.user_name = ?1", nativeQuery = true)
	List<Project> findByUser(String userName);
	
	@Modifying
	@Query(value = "DELETE FROM project_user_xref WHERE project_id = ?1 AND user_name = ?2", nativeQuery = true)
	void removeUser(int projectId, String userName);
}
