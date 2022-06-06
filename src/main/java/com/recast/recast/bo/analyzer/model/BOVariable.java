package com.recast.recast.bo.analyzer.model;

public class BOVariable {
	String id;
	String name;
	String dataType;
	String qualification;
	String formulaLanguageId;
	String definition;
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
	@Override
	public String toString() {
		return "BOVariable [id=" + id + ", name=" + name + ", dataType=" + dataType + ", qualification=" + qualification
				+ ", formulaLanguageId=" + formulaLanguageId + ", definition=" + definition + "]";
	}
	
	
}
