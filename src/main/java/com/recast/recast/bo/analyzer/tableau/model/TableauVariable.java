package com.recast.recast.bo.analyzer.tableau.model;

public class TableauVariable {

	private String id;
	private String name;
	private String dataType;
	private String qualification;
	private String formulaLanguageId;
	private String definition;
	private String className;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
	public String getQualification() {
		return qualification;
	}
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	public String getFormulaLanguageId() {
		return formulaLanguageId;
	}
	public void setFormulaLanguageId(String formulaLanguageId) {
		this.formulaLanguageId = formulaLanguageId;
	}
	public String getDefinition() {
		return definition;
	}
	public void setDefinition(String definition) {
		this.definition = definition;
	}
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	@Override
	public String toString() {
		return "TableauVariable [id=" + id + ", name=" + name + ", dataType=" + dataType + ", qualification="
				+ qualification + ", formulaLanguageId=" + formulaLanguageId + ", definition=" + definition
				+ ", className=" + className + "]";
	}

	
	

	
}
