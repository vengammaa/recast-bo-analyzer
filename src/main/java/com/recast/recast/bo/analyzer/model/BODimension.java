package com.recast.recast.bo.analyzer.model;

public class BODimension {
	String name;
	String dataType;
	String select;
	String where;
	String path;
	String parent;
	
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
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	@Override
	public String toString() {
		return "BODimension [name=" + name + ", dataType=" + dataType + ", select=" + select + ", where=" + where
				+ ", path=" + path + ", parent=" + parent + "]";
	}
	
	
	
	
}
