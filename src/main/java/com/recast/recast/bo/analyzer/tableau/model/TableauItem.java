package com.recast.recast.bo.analyzer.tableau.model;

public class TableauItem {
	int id = -1;
	String name;
	String dataType = "NOT APPLICABLE";
	String select = "NOT APPLICABLE";
	String where = "NOT APPLICABLE";
	String path = "";
	String projectionFunction = "NOT APPLICABLE";
	Boolean isUsed = false;
	String objectIdentifier = "NOT APPLICABLE";
	String formula = "NOT APPLICABLE";
	String type = "";
	String role = "";
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public Boolean getIsUsed() {
		return isUsed;
	}
	public void setIsUsed(Boolean isUsed) {
		this.isUsed = isUsed;
	}
	public String getObjectIdentifier() {
		return objectIdentifier;
	}
	public void setObjectIdentifier(String objectIdentifier) {
		this.objectIdentifier = objectIdentifier;
	}
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "TableauItem [id=" + id + ", name=" + name + ", dataType=" + dataType + ", select=" + select + ", where="
				+ where + ", path=" + path + ", projectionFunction=" + projectionFunction + ", isUsed=" + isUsed
				+ ", objectIdentifier=" + objectIdentifier + ", formula=" + formula + ", type=" + type + ", role="
				+ role + "]";
	}
	
	
}
