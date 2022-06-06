package com.recast.recast.bo.analyzer.model;

public class DataFilter {
	private String id;
	private String name;
	private String reportTabId;
	private String filterType;
	private String conditionKey;
	private String conditionValue;
	private String operator;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getReportTabId() {
		return reportTabId;
	}
	public void setReportTabId(String reportTabId) {
		this.reportTabId = reportTabId;
	}
	public String getFilterType() {
		return filterType;
	}
	public void setFilterType(String filterType) {
		this.filterType = filterType;
	}
	public String getConditionKey() {
		return conditionKey;
	}
	public void setConditionKey(String conditionKey) {
		this.conditionKey = conditionKey;
	}
	public String getConditionValue() {
		return conditionValue;
	}
	public void setConditionValue(String conditionValue) {
		this.conditionValue = conditionValue;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	@Override
	public String toString() {
		return "DataFilter [id=" + id + ", name=" + name + ", reportTabId=" + reportTabId + ", filterType=" + filterType
				+ ", conditionKey=" + conditionKey + ", conditionValue=" + conditionValue + ", operator=" + operator
				+ "]";
	}
	
	
	
	

}
