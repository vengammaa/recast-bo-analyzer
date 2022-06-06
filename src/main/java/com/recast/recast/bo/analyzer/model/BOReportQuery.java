package com.recast.recast.bo.analyzer.model;

import java.util.ArrayList;
import java.util.List;

public class BOReportQuery {
	private String queryId;
	private String queryName;
	private String query;
	private String dataSourceId;
	private String dataSourceName;
	private String dataSourceType;
	private List<String> queryStatements = new ArrayList<String>();
	private Integer numberOfPrompts;
	private Integer numberOfFormulas;
	private List<BOReportQueryColumn> boReportQueryColumns = new ArrayList<BOReportQueryColumn>();
	private List<BOReportObject> boReportObject = new ArrayList<BOReportObject>();
	
	public String getQueryId() {
		return queryId;
	}
	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}
	public String getQueryName() {
		return queryName;
	}
	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public Integer getNumberOfPrompts() {
		return numberOfPrompts;
	}
	public void setNumberOfPrompts(Integer numberOfPrompts) {
		this.numberOfPrompts = numberOfPrompts;
	}
	public Integer getNumberOfFormulas() {
		return numberOfFormulas;
	}
	public void setNumberOfFormulas(Integer numberOfFormulas) {
		this.numberOfFormulas = numberOfFormulas;
	}
	public List<BOReportQueryColumn> getBoReportQueryColumns() {
		return boReportQueryColumns;
	}
	public void setBoReportQueryColumns(List<BOReportQueryColumn> boReportQueryColumns) {
		this.boReportQueryColumns = boReportQueryColumns;
	}
	
	public List<String> getQueryStatements() {
		return queryStatements;
	}
	public void setQueryStatements(List<String> queryStatements) {
		this.queryStatements = queryStatements;
	}
	

	public String getDataSourceId() {
		return dataSourceId;
	}
	public void setDataSourceId(String dataSourceId) {
		this.dataSourceId = dataSourceId;
	}
	public String getDataSourceName() {
		return dataSourceName;
	}
	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}
	public String getDataSourceType() {
		return dataSourceType;
	}
	public void setDataSourceType(String dataSourceType) {
		this.dataSourceType = dataSourceType;
	}
	public List<BOReportObject> getBoReportObject() {
		return boReportObject;
	}
	public void setBoReportObject(List<BOReportObject> boReportObject) {
		this.boReportObject = boReportObject;
	}
/*	@Override
	public String toString() {
		StringBuilder output = new StringBuilder();
		output.append("\n######################################## Query Metadata ########################################");
		output.append("\nQuery Id:" + queryId + "\tQuery Name:" + queryName);
		output.append("\nNumber of Prompts: " + numberOfPrompts + "\tNumber of Formulae: " + numberOfFormulas);
		output.append("\n================================================================================================");
		output.append("\n======================================= Query Statement =======================================\n");
		output.append(query);
		output.append("\n================================================================================================");
		output.append("\n======================================== Query Columns ========================================");
		
		for (int i = 0; i < boReportQueryColumns.size(); i++) {
			BOReportQueryColumn column = boReportQueryColumns.get(i);
			output.append("\n----------------------------------- Column# " + (i + 1) + " -----------------------------------");
			output.append("\nColumn Id: " + column.getColumnId() + "\tColumn Name: " + column.getColumnName());
			output.append("\nData Type: " + column.getColumnDataType() + "\tQualification: " + column.getColumnQualification() + "\tAggregate Function: " + column.getAggregateFunction());
			output.append("\nDescription: " + column.getColumnDescription());
			output.append("\n---------------------------------------------------------------------------------------------");
		}
		
		output.append("\n##################################################################################################\n");
		return output.toString();*/
//		return "BOReportQuery [queryId=" + queryId + ", queryName=" + queryName + ", query=" + query
//				+ ", numberOfPrompts=" + numberOfPrompts + ", numberOfFormulas=" + numberOfFormulas
//				+ ", boReportQueryColumns=" + boReportQueryColumns + "]";
//	}
	@Override
	public String toString() {
		return "BOReportQuery [queryId=" + queryId + ", queryName=" + queryName + ", query=" + query + ", dataSourceId="
				+ dataSourceId + ", dataSourceName=" + dataSourceName + ", dataSourceType=" + dataSourceType
				+ ", queryStatements=" + queryStatements + ", numberOfPrompts=" + numberOfPrompts
				+ ", numberOfFormulas=" + numberOfFormulas + ", boReportQueryColumns=" + boReportQueryColumns
				+ ", boReportObject=" + boReportObject + "]";
	}

}
