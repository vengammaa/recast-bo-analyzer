package com.recast.recast.bo.analyzer.model;

public class BOItem {
	String name;
	String dataType = "NOT APPLICABLE";
	String select = "NOT APPLICABLE";
	String where = "NOT APPLICABLE";
	String path;
	String projectionFunction = "NOT APPLICABLE";
	Boolean isUsed = false;
	String objectIdentifier;
	
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
	public String getSelect() {
		return select;
	}
	public void setSelect(String select) {
		this.select = select;
	}
	public String getWhere() {
		return where;
	}
	public void setWhere(String where) {
		this.where = where;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}

	public String getProjectionFunction() {
		return projectionFunction;
	}
	public void setProjectionFunction(String projectionFunction) {
		this.projectionFunction = projectionFunction;
	}
	

	public String getObjectIdentifier() {
		return objectIdentifier;
	}
	public void setObjectIdentifier(String objectIdentifier) {
		this.objectIdentifier = objectIdentifier;
	}
	@Override
	public String toString() {
		return "BOItem [name=" + name + ", dataType=" + dataType + ", select=" + select + ", where=" + where + ", path="
				+ path + ", projectionFunction=" + projectionFunction + ", isUsed=" + isUsed + ", objectIdentifier="
				+ objectIdentifier + "]";
	}
	
	
}
