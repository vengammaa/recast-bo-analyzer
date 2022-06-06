package com.recast.recast.bo.analyzer.model;

public class BOQueryDetailsModel {

	private int reportId;
	
	private String reportName;
	
	private String dataSourceName;
	
	private String tableName;
	
	private String queryName;
	
	private String tableAliasName;
	
	private String columnList;

	public int getReportId() {
		return reportId;
	}

	public void setReportId(int reportId) {
		this.reportId = reportId;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getDataSourceName() {
		return dataSourceName;
	}

	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getQueryName() {
		return queryName;
	}

	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}

	public String getTableAliasName() {
		return tableAliasName;
	}

	public void setTableAliasName(String tableAliasName) {
		this.tableAliasName = tableAliasName;
	}
	
	

	public String getColumnList() {
		return columnList;
	}

	public void setColumnList(String columnList) {
		this.columnList = columnList;
	}

	@Override
	public String toString() {
		return "BOQueryDetailsModel [reportId=" + reportId + ", reportName=" + reportName + ", dataSourceName="
				+ dataSourceName + ", tableName=" + tableName + ", queryName=" + queryName + ", tableAliasName="
				+ tableAliasName + ", columnList=" + columnList + "]";
	}

	
		
	
}
