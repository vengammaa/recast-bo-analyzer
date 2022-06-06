package com.recast.recast.bo.analyzer.model;

import java.util.ArrayList;
import java.util.List;

public class BOTable {
	private String name;
	private List<BOColumn> columns = new ArrayList<BOColumn>();
	private Boolean isUsed = false;
	
	public Boolean getIsUsed() {
		return isUsed;
	}
	public void setIsUsed(Boolean isUsed) {
		this.isUsed = isUsed;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public List<BOColumn> getColumns() {
		return columns;
	}
	public void setColumns(List<BOColumn> columns) {
		this.columns = columns;
	}
	@Override
	public String toString() {
		return "BOTable [name=" + name + ", columns=" + columns + ", isUsed=" + isUsed + "]";
	}
	
	
}
