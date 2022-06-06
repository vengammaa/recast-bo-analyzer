package com.recast.recast.bo.analyzer.model;

public class AxisFormula {

	private String name;
	
	private String hide;
	
	private String dataType;
	
	private String dataObjectId;
	
	private String qualification;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHide() {
		return hide;
	}

	public void setHide(String hide) {
		this.hide = hide;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDataObjectId() {
		return dataObjectId;
	}

	public void setDataObjectId(String dataObjectId) {
		this.dataObjectId = dataObjectId;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	@Override
	public String toString() {
		return "AxisFormula [name=" + name + ", hide=" + hide + ", dataType=" + dataType + ", dataObjectId="
				+ dataObjectId + ", qualification=" + qualification + "]";
	}
	
	
	
	
}
