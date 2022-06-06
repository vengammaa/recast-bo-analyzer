package com.recast.recast.bo.analyzer.tableau.model;

import java.util.ArrayList;
import java.util.List;

public class QueryFilter {

	
	private String className;
	
	private String column;
	
	private String context;
	
	private String filterGroup;
	
	private String kind;
	private String functionName;	
	private List<String> members = new ArrayList<>();
	private String level;
	private String userUiEnumeration;
	private String userUiMarker;
	private String includedValues;
	private String userUiActionFilter;
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getFilterGroup() {
		return filterGroup;
	}
	public void setFilterGroup(String filterGroup) {
		this.filterGroup = filterGroup;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getFunctionName() {
		return functionName;
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	public List<String> getMembers() {
		return members;
	}
	public void setMembers(List<String> members) {
		this.members = members;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getUserUiEnumeration() {
		return userUiEnumeration;
	}
	public void setUserUiEnumeration(String userUiEnumeration) {
		this.userUiEnumeration = userUiEnumeration;
	}
	public String getUserUiMarker() {
		return userUiMarker;
	}
	public void setUserUiMarker(String userUiMarker) {
		this.userUiMarker = userUiMarker;
	}
	public String getIncludedValues() {
		return includedValues;
	}
	public void setIncludedValues(String includedValues) {
		this.includedValues = includedValues;
	}
	public String getUserUiActionFilter() {
		return userUiActionFilter;
	}
	public void setUserUiActionFilter(String userUiActionFilter) {
		this.userUiActionFilter = userUiActionFilter;
	}
	
	@Override
	public String toString() {
		return "QueryFilter [className=" + className + ", column=" + column + ", context=" + context + ", filterGroup="
				+ filterGroup + ", kind=" + kind + ", functionName=" + functionName + ", members=" + members
				+ ", level=" + level + ", userUiEnumeration=" + userUiEnumeration + ", userUiMarker=" + userUiMarker
				+ ", includedValues=" + includedValues + ", userUiActionFilter=" + userUiActionFilter + "]";
	}

	
}
