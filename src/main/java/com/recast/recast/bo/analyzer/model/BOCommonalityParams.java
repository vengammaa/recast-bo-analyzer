package com.recast.recast.bo.analyzer.model;

import java.util.ArrayList;
import java.util.List;

public class BOCommonalityParams {
	private Integer reportId;
	private String reportName;
	private String dataSourceName;
	private String tableName;
	private String columnName;
	private String columnType;
	private String columnAlias;
	private String dimensionList; 
	private String measureList;
	private String attributeList;
	private String variableList;
	
	public Integer getReportId() {
		return reportId;
	}
	public void setReportId(Integer reportId) {
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
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getColumnType() {
		return columnType;
	}
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	public String getColumnAlias() {
		return columnAlias;
	}
	public void setColumnAlias(String columnAlias) {
		this.columnAlias = columnAlias;
	}
	public String getDimensionList() {
		return dimensionList;
	}
	public void setDimensionList(String dimensionList) {
		this.dimensionList = dimensionList;
	}
	public String getMeasureList() {
		return measureList;
	}
	public void setMeasureList(String measureList) {
		this.measureList = measureList;
	}
	public String getVariableList() {
		return variableList;
	}
	public void setVariableList(String variableList) {
		this.variableList = variableList;
	}
	public String getAttributeList() {
		return attributeList;
	}
	public void setAttributeList(String attributeList) {
		this.attributeList = attributeList;
	}
	@Override
	public String toString() {
		return "BOCommonalityParams [reportId=" + reportId + ", reportName=" + reportName + ", dataSourceName="
				+ dataSourceName + ", tableName=" + tableName + ", columnName=" + columnName + ", columnType="
				+ columnType + ", columnAlias=" + columnAlias + ", dimensionList=" + dimensionList + ", measureList="
				+ measureList + ", attributeList=" + attributeList + ", variableList=" + variableList + "]";
	}
	
}
