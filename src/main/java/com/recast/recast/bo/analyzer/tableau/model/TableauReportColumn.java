package com.recast.recast.bo.analyzer.tableau.model;

public class TableauReportColumn {

	
	private String columnExpression;
	private String columnDataType;
	private String columnRole;
	private String columnSemanticRole;
	private String columnCaption;
	private String columnType;
	private String defaultformat;
	
	public String getColumnExpression() {
		return columnExpression;
	}
	public void setColumnExpression(String columnExpression) {
		this.columnExpression = columnExpression;
	}
	public String getColumnDataType() {
		return columnDataType;
	}
	public void setColumnDataType(String columnDataType) {
		this.columnDataType = columnDataType;
	}
	public String getColumnRole() {
		return columnRole;
	}
	public void setColumnRole(String columnRole) {
		this.columnRole = columnRole;
	}
	public String getColumnSemanticRole() {
		return columnSemanticRole;
	}
	public void setColumnSemanticRole(String columnSemanticRole) {
		this.columnSemanticRole = columnSemanticRole;
	}
	public String getColumnCaption() {
		return columnCaption;
	}
	public void setColumnCaption(String columnCaption) {
		this.columnCaption = columnCaption;
	}
	public String getColumnType() {
		return columnType;
	}
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	public String getDefaultformat() {
		return defaultformat;
	}
	public void setDefaultformat(String defaultformat) {
		this.defaultformat = defaultformat;
	}
	
	@Override
	public String toString() {
		return "TableauReportColumn [columnExpression=" + columnExpression + ", columnDataType=" + columnDataType
				+ ", columnRole=" + columnRole + ", columnSemanticRole=" + columnSemanticRole + ", columnCaption="
				+ columnCaption + ", columnType=" + columnType + ", defaultformat=" + defaultformat + "]";
	}
	

	
}
