package com.recast.recast.bo.analyzer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.recast.recast.bo.analyzer.entity.VisualDetails;

@Repository
public interface VisualDetailsRepository extends JpaRepository<VisualDetails, Integer>{
	@Query(value = "SELECT * FROM analysis_reports_visualization WHERE task_id = ?1 AND report_Id = ?2", nativeQuery = true)
	List<VisualDetails> findByTaskidAndReportId(Integer taskid,Integer reportId);
	@Query(value = "SELECT report_Id,report_tab_name, element_name,\r\n"
			+ "case when element_type='Visualization' then chart_type\r\n"
			+ "when element_type='Page Zone' then element_name\r\n"
			+ "else element_type\r\n"
			+ "end as 'Element Type ',\r\n"
			+ "case when element_type='Cell' then formula\r\n"
			+ "else chart_axes "
			+ "end as 'Data Axis Info', x_position, y_position, minimal_height, minimal_width, element_id, parent_id from recast.analysis_reports_visualization WHERE task_id= ?1", nativeQuery = true)
	List<List<String>> findByReportTab(Integer taskid);
	List<VisualDetails> findByTaskId(Integer taskId);

}
