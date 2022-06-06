package com.recast.recast.bo.analyzer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.recast.recast.bo.analyzer.entity.CommonalityParams;


@Repository
public interface CommonalityParamsRepository extends JpaRepository<CommonalityParams,Integer> {
	
	
	/**
	 * <p>
	 * Retrieves all the commonality params table information corresponding to the
	 * given taskId
	 * </p>
	 * 
	 * @param taskId the foreign key in the commonality params table
	 * @return List of CommonalityParams that holds all the data for the given
	 *         taskId from the commonality params table
	 */
	List<CommonalityParams> findByTaskId(int taskId);
	
	
	/**
	 * <p>
	 * Filters data by taskId in the commonality params table and retrieves data for
	 * the following columns: report_id, task_id, report_name, data_source_name, and
	 * table_name.
	 * </p>
	 * 
	 * <p>
	 * This data will be used to build a HashMap which will be needed to compare
	 * list of table names in step 1.
	 * </p>
	 * 
	 * @param taskId the foreign key in the commonality params table
	 * @return List of List of String that contains the filtered data of the
	 *         commonality params table by taskId and by the specified columns.
	 */
	@Query(value = "SELECT report_id,task_id,report_name,data_source_name,table_name FROM commonality_params where task_id = ?1 GROUP BY table_name, report_name", nativeQuery = true)
	public List<List<String>> getQuery(int taskId);
	
	
	/**
	 * <p>
	 * This method retrieves all the column names that have column type select for a
	 * given reportId and tableName from the commonality params table.
	 * </p>
	 * 
	 * <p>
	 * Used in step 2 of the similarity engine when comparing if two tables have
	 * same column names that have column type select.
	 * </p>
	 * 
	 * @param reportId  a report that has the given number associated to it
	 * @param tableName is one table name for the given reportId
	 * @return List of Strings that has column names as entries
	 */
	@Query(value = "Select column_name from commonality_params where report_id = ?1 and table_name = ?2 and column_Type =\"select\" group by column_name", nativeQuery = true)
	public List<String> getStep2QueryByReportIdTableName(int reportId, String tableName);
	
	
	/**
	 * <p>
	 * This method retrieves all the column names that do not have column type
	 * select for a given reportId and tableName from the commonality params table.
	 * </p>
	 * 
	 * <p>
	 * For step 3, it is necessary to check if this list of column names are the
	 * same for a table in both reports first (i.e. before continuing step 3).
	 * </p>
	 * 
	 * @param reportId  a report that has the given number associated to it
	 * @param tableName is one table name for the given reportId
	 * @return List of Strings that has column names as entries
	 */
	// used to see
	// if two tables have same column names which don't have select column type
	@Query(value = "Select column_name from commonality_params where report_id = ?1 and table_name = ?2 and column_Type !=\"select\" group by column_name", nativeQuery = true)
	public List<String> getStep3QueryColumnNamesWhichDoesNotHaveSelectColumnType(int reportId, String tableName);
	
	
	/**
	 * <p>
	 * This method retrieves all the column types for a given reportId, tableName,
	 * and columnName from the commonality params table.
	 * </p>
	 * 
	 * <p>
	 * Once the column names with column type not select are the same for a specific
	 * table and pair of reports. This query is used to check if for each column
	 * name the corresponding column types match as well. This is used for the
	 * second half of step 3.
	 * </p>
	 * 
	 * @param reportId   a report that has the given number associated to it
	 * @param tableName  is one table name for the given reportId
	 * @param columnName one of the column names for the given reportId and
	 *                   tableName
	 * @return List of Strings that has column types as entries
	 */
	@Query(value = "Select column_Type from commonality_params where report_id =?1 and table_name =?2 and column_name =?3 and column_Type !=\"select\" group by column_Type", nativeQuery = true)
	public List<String> getStep3QueryColumnTypesWhichDoesNotHaveSelectColumnType(int reportId, String tableName,
			String columnName);
	
	
	/**
	 * <p>
	 * This method retrieves all the unique entries in the attributeList column of
	 * the commonality params table for a given reportId, and returns a list of
	 * these items.
	 * </p>
	 * 
	 * <p>
	 * This query is used to compare the attributeList entries for two reports in
	 * step 4.
	 * </p>
	 * 
	 * @param reportId a report that has the given number associated to it
	 * @return List of Strings that has the contents of the attributeList column in
	 *         the commonality params table.
	 */
	@Query(value = "select attribute_list from commonality_params where report_id=?1 group by attribute_list", nativeQuery = true)
	public List<String> getStep4QueryForAttributeList(int reportId);
	
	
	/**
	 * <p>
	 * This method retrieves all the unique entries in the measureList column of the
	 * commonality params table for a given reportId, and returns a list of these
	 * items.
	 * </p>
	 * 
	 * <p>
	 * This query is used to compare the measureList entries for two reports in step
	 * 4.
	 * </p>
	 * 
	 * @param reportId a report that has the given number associated to it
	 * @return List of Strings that has the contents of the measureList column in
	 *         the commonality params table.
	 */
	@Query(value = "select measure_list from commonality_params where report_id=?1 group by measure_list", nativeQuery = true)
	public List<String> getStep4QueryForMeasureList(int reportId);
	
	
	/**
	 * <p>
	 * This method retrieves all the unique entries in the variableList column of
	 * the commonality params table for a given reportId, and returns a list of
	 * these items.
	 * </p>
	 * 
	 * <p>
	 * This query is used to compare the variableList entries for two reports in
	 * step 4.
	 * </p>
	 * 
	 * @param reportId a report that has the given number associated to it
	 * @return List of Strings that has the contents of the variableList column in
	 *         the commonality params table.
	 */
	@Query(value = "select variable_list from commonality_params where report_id=?1 group by variable_list", nativeQuery = true)
	public List<String> getStep4QueryForVariableList(int reportId);
	
	
	/**
	 * <p>
	 * This method retrieves all the unique chart type column entries in the
	 * analysis reports visualization table.
	 * </p>
	 * 
	 * <p>
	 * This query is used to compare all the visuals between two reports in step 5.
	 * </p>
	 * 
	 * @param reportId a report that has the given number associated to it
	 * @return List of Strings that has chart type/visuals as entries.
	 */
	@Query(value = "select analysis_reports_visualization.chart_type from analysis_reports_visualization where element_type = \"Visualization\" and report_id = ?1 group by chart_type", nativeQuery = true)
	public List<String> getStep5QueryChartTypesForReportId(int reportId);
	
	
	/**
	 * <p>
	 * This method retrieves the contents of the data filters column in the analysis
	 * report table as a String for a given reportId.
	 * </p>
	 * 
	 * <p>
	 * This query is used to compare the data filter entries for two reports in step
	 * 6.
	 * </p>
	 * 
	 * @param reportId a report that has the given number associated to it
	 * @return String that has contents of the data filter information.
	 */
	@Query(value = "select data_filters from analysis_report where report_id = ?1", nativeQuery = true)
	public String getStep6QueryForDataFilters(int reportId);
	
	/**
	 * <p>
	 * This method retrieves all the unique entries in the dimensionList column of
	 * the commonality params table for a given reportId, and returns a list of
	 * these items.
	 * </p>
	 * 
	 * <p>
	 * This query is used to compare the dimensionList entries for two reports in
	 * step 4.
	 * </p>
	 * 
	 * @param reportId a report that has the given number associated to it
	 * @return List of Strings that has the contents of the dimensionList column in
	 *         the commonality params table.
	 */
	@Query(value = "select dimension_list from commonality_params where report_id=?1 group by dimension_list", nativeQuery = true)
	public List<String> getStep4QueryForDimensionList(int reportId);
	
	
}
