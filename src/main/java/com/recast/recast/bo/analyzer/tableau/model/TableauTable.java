package com.recast.recast.bo.analyzer.tableau.model;

import java.util.ArrayList;
import java.util.List;

public class TableauTable {
	private String name;
	private List<TableauColumn> columns = new ArrayList<TableauColumn>();
	private Boolean isUsed = false;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<TableauColumn> getColumns() {
		return columns;
	}
	public void setColumns(List<TableauColumn> columns) {
		this.columns = columns;
	}
	public Boolean getIsUsed() {
		return isUsed;
	}
	public void setIsUsed(Boolean isUsed) {
		this.isUsed = isUsed;
	}
	
	@Override
	public String toString() {
		return "TableauTable [name=" + name + ", columns=" + columns + ", isUsed=" + isUsed + "]";
	}
	
}
