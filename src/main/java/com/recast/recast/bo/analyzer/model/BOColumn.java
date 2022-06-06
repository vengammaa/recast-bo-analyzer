package com.recast.recast.bo.analyzer.model;

public class BOColumn {
	private String name;
	private String dataType;
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
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	@Override
	public String toString() {
		return "BOColumn [name=" + name + ", dataType=" + dataType + ", isUsed=" + isUsed + "]";
	}
	
	
}
