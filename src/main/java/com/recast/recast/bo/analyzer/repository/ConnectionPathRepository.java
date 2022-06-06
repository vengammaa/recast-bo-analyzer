package com.recast.recast.bo.analyzer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.recast.recast.bo.analyzer.entity.ConnectionPath;

@Repository
public interface ConnectionPathRepository extends JpaRepository<ConnectionPath,Integer> {
	@Query(value = "SELECT * FROM connection_path WHERE connection_Id = ?1 AND path_Name = ?2", nativeQuery = true)
	List<ConnectionPath> findByConnectionIdAndPathName(Integer connectionId,String pathName);
	List<ConnectionPath> findByPathName(String pathName);
	List<ConnectionPath> findByConnectionId(Integer connectionId);

}
