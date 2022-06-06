package com.recast.recast.bo.analyzer.tableau.model;

public class TableauColumn {
	private String name;
	private String dataType;
	private Boolean isUsed = false;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public Boolean getIsUsed() {
		return isUsed;
	}
	public void setIsUsed(Boolean isUsed) {
		this.isUsed = isUsed;
	}
	
	@Override
	public String toString() {
		return "TableauColumn [name=" + name + ", dataType=" + dataType + ", isUsed=" + isUsed + "]";
	}
	
}
