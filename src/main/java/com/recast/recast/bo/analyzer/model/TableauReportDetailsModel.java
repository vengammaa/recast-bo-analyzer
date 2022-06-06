package com.recast.recast.bo.analyzer.model;

import java.util.List;

public class TableauReportDetailsModel {
	private String reportPath;
	private String worksheetName;
	private String[] columnNames;
	private String[] datasourceNames;
	
	public String getReportPath() {
		return reportPath;
	}
	public void setReportPath(String reportPath) {
		this.reportPath = reportPath;
	}
	public String getWorksheetName() {
		return worksheetName;
	}
	public void setWorksheetName(String worksheetName) {
		this.worksheetName = worksheetName;
	}
	public String[] getColumnNames() {
		return columnNames;
	}
	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}
	public String[] getDatasourceNames() {
		return datasourceNames;
	}
	public void setDatasourceNames(String[] datasourceNames) {
		this.datasourceNames = datasourceNames;
	}
	@Override
	public String toString() {
		return "TableauReportDetailsModel [reportPath=" + reportPath + ", worksheetName=" + worksheetName
				+ ", columnNames=" + columnNames + ", datasourceNames=" + datasourceNames + "]";
	}
	
	
}
