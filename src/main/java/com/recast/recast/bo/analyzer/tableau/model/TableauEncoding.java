package com.recast.recast.bo.analyzer.tableau.model;

public class TableauEncoding {

	private String attr;
	private String field;
	private String fieldType;
	private String palette;
	private String type;
	
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
	public String getPalette() {
		return palette;
	}
	public void setPalette(String palette) {
		this.palette = palette;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	@Override
	public String toString() {
		return "TableauEncoding [attr=" + attr + ", field=" + field + ", fieldType=" + fieldType + ", palette="
				+ palette + ", type=" + type + "]";
	}
	
	

}
