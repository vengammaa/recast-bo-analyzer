package com.recast.recast.bo.analyzer.tableau.model;

public class TableauFormat {

	private String attr;
	private String field;
	private String value;
	private String dataclass;
	private String scope;
	
	public String getAttr() {
		return attr;
	}
	public void setAttr(String attr) {
		this.attr = attr;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDataclass() {
		return dataclass;
	}
	public void setDataclass(String dataclass) {
		this.dataclass = dataclass;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	@Override
	public String toString() {
		return "TableauFormat [attr=" + attr + ", field=" + field + ", value=" + value + ", dataclass=" + dataclass
				+ ", scope=" + scope + "]";
	}
	

}
