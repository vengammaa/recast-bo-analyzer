package com.recast.recast.bo.analyzer.service;

import java.util.List;

/**
 * <h2>The CommonalityParamsService Interface is responsible for listing certain
 * methods that will be overridden in the service implementation, some of which
 * are the method that runs the similarity engine as well as all the methods
 * that use the repository to receive data from the database.</h2>
 *
 * @author Team A - Sadia, Naushaba, and Timothy
 *
 */
public interface CommonalityParamsService {


	/**
	 * <p>
	 * This method calls all the steps of the similarity engine on those report with
	 * the given taskId, and then saves the output in the similarity matrix table
	 * </p>
	 * 
	 * @param taskId the foreign key in the commonality params table
	 * @return List of List of String that contains all the data that was saved into the
	 *         similarity matrix table after running the similarity engine.
	 */
	List<List<String>> similarityEngineProcess(int taskId);
		
	
	/**
	 * <p>
	 * This method retrieves all the column names that have column type = select for
	 * a given reportId and tableName. Used in step 2 of the similarity engine.
	 * </p>
	 * 
	 * @param reportId  a report that has the given number associated to it
	 * @param tableName is one table name for the given reportId
	 * @return List of Strings that has column names as entries
	 */
	List<String> getStep2QueryByReportIdTableName(int reportId, String tableName);
	
	
	/**
	 * <p>
	 * This method retrieves all the column names that do not have column type
	 * select for a given reportId and tableName. Used in step 3 of the similarity
	 * engine.
	 * </p>
	 * 
	 * @param reportId  a report that has the given number associated to it
	 * @param tableName is one table name for the given reportId
	 * @return List of Strings that has column names as entries
	 */
	List<String> getStep3QueryColumnNamesWithoutSelectColumnType(int reportId, String tableName);
	
	
	/**
	 * <p>
	 * This method retrieves all the column types for a given reportId, tableName,
	 * and columnName. Used in step 3 of the similarity engine.
	 * </p>
	 * 
	 * @param reportId  a report that has the given number associated to it
	 * @param tableName is one table name for the given reportId
	 * @param columnName one of the column names for the given reportId and tableName
	 * @return List of Strings that has column types as entries
	 */
	List<String> getStep3QueryColumnTypesWithoutSelectColumnType(int reportId, String tableName, String columnName);

	
	/**
	 * <p>
	 * This method retrieves all the unique chart type column entries in the
	 * analysis reports visualization table. Used in step 5 of the similarity
	 * engine.
	 * </p>
	 * 
	 * @param reportId each report has a corresponding number associated to it
	 * @return List of Strings that has chart type/visuals as entries.
	 */
	List<String> getStep5QueryChartTypesWithGivenReportId(int reportId);
	
	
	/**
	 * <p>
	 * This method retrieves the contents of the data filters column in the analysis
	 * report table as a String for a given reportId. Used in step 6 of the similarity engine.
	 * </p>
	 * 
	 * @param reportId a report that has the given number associated to it
	 * @return String that has contents of the data filter information.
	 */
	String getStep6QueryForDataFiltersByGivenReportId(int reportId);
	
}
