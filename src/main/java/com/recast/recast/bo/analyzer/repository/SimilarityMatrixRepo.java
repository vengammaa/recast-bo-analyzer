package com.recast.recast.bo.analyzer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.recast.recast.bo.analyzer.entity.SimilarityMatrix;


/**
 * <h2>The SimilarityMatrixRepo Interface is responsible for retrieving and
 * updating data corresponding to the similarity matrix table.
 * 
 * By extending the JpaRepository, we have access to CRUD methods.</h2>
 * 
 * @author Lntinfotech
 *
 */
public interface SimilarityMatrixRepo extends JpaRepository<SimilarityMatrix, Integer> {

	/**
	 * <p>
	 * This method retrieves the primary key of the similarity matrix table that corresponds to the given reportIds.
	 * </p>
	 * @param reportId1 a report that has the given number associated to it
	 * @param reportId2 another report that has this different number associated to
	 *  
	 * @return String that is the primary key in the form of a String
	 */
	@Query(value = "SELECT id FROM similarity_matrix where report_id1=?1 AND report_id2 =?2", nativeQuery = true)
	public String getSimilarityMatrixIdByUsingReportId1AndReportId2(int reportId1, int reportId2);

	
//	@Modifying
//	@Query(value = "UPDATE similarity_matrix SET step1 = :step1, step2 = :step2, step3 = :step3, step4 = :step4, step5 = :step5, step6 = :step6,total_score = :commonalityPercentage WHERE (id = :id)", nativeQuery = true)
//	public void updateSimilarityMatrix(@Param("step1") String step1, @Param("step2") String step2,
//			@Param("step3") String step3, @Param("step4") String step4, @Param("step5") String step5,
//			@Param("step6") String step6, @Param("commonalityPercentage") int commonalityPercentage,
//			@Param("id") String id);

}
