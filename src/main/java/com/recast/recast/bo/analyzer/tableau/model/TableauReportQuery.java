package com.recast.recast.bo.analyzer.tableau.model;

import java.util.ArrayList;
import java.util.List;



public class TableauReportQuery {

	private String queryId;
	private String queryName;
	private String query;
	private List<String> queryStatements = new ArrayList<String>();
	private Integer numberOfPrompts;
	private Integer numberOfFormulas;
	private List<TableauReportQueryColumn> tabReportQueryColumns = new ArrayList<TableauReportQueryColumn>();
	//private List<>
	
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
	public List<String> getQueryStatements() {
		return queryStatements;
	}
	public void setQueryStatements(List<String> queryStatements) {
		this.queryStatements = queryStatements;
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
	
	public List<TableauReportQueryColumn> getTabReportQueryColumns() {
		return tabReportQueryColumns;
	}
	public void setTabReportQueryColumns(List<TableauReportQueryColumn> tabReportQueryColumns) {
		this.tabReportQueryColumns = tabReportQueryColumns;
	}
	@Override
	public String toString() {
		return "TableauReportQuery [queryId=" + queryId + ", queryName=" + queryName + ", query=" + query
				+ ", queryStatements=" + queryStatements + ", numberOfPrompts=" + numberOfPrompts
				+ ", numberOfFormulas=" + numberOfFormulas + ", tabReportQueryColumns=" + tabReportQueryColumns + "]";
	}
	
//	@Override
//	public String toString() {
//		StringBuilder output = new StringBuilder();
//		output.append("\n######################################## Query Metadata ########################################");
//		output.append("\nQuery Id:" + queryId + "\tQuery Name:" + queryName);
//		output.append("\nNumber of Prompts: " + numberOfPrompts + "\tNumber of Formulae: " + numberOfFormulas);
//		output.append("\n================================================================================================");
//		output.append("\n======================================= Query Statement =======================================\n");
//		output.append(query);
//		output.append("\n================================================================================================");
//		output.append("\n======================================== Query Columns ========================================");
//		
//		for (int i = 0; i < tabReportQueryColumns.size(); i++) {
//			TableauReportQueryColumn column = tabReportQueryColumns.get(i);
//			output.append("\n----------------------------------- Column# " + (i + 1) + " -----------------------------------");
//			output.append("\nColumn Id: " + column.getColumnId() + "\tColumn Name: " + column.getColumnName());
//			output.append("\nData Type: " + column.getColumnDataType() + "\tQualification: " + column.getColumnQualification() + "\tAggregate Function: " + column.getAggregateFunction());
//			output.append("\nColumn Type: " + column.getColumnType());
//			output.append("\n---------------------------------------------------------------------------------------------");
//		}
//		
//		output.append("\n##################################################################################################\n");
//		return output.toString();
////		return "BOReportQuery [queryId=" + queryId + ", queryName=" + queryName + ", query=" + query
////				+ ", numberOfPrompts=" + numberOfPrompts + ", numberOfFormulas=" + numberOfFormulas
////				+ ", boReportQueryColumns=" + boReportQueryColumns + "]";
//	}
	
	
	
	
}
