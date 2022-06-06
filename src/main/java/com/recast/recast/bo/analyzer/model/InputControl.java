package com.recast.recast.bo.analyzer.model;

import java.util.List;

public class InputControl {
	private String inputControlId;
	private String inputControlName;
	private String objectName;
	private String operator;
	private String defaultValue;
	private List<String> customListOfValues;
	public String getInputControlId() {
		return inputControlId;
	}
	public void setInputControlId(String inputControlId) {
		this.inputControlId = inputControlId;
	}
	public String getInputControlName() {
		return inputControlName;
	}
	public void setInputControlName(String inputControlName) {
		this.inputControlName = inputControlName;
	}
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public List<String> getCustomListOfValues() {
		return customListOfValues;
	}
	public void setCustomListOfValues(List<String> customListOfValues) {
		this.customListOfValues = customListOfValues;
	}
	@Override
	public String toString() {
		return "InputControl [inputControlId=" + inputControlId + ", inputControlName=" + inputControlName
				+ ", objectName=" + objectName + ", operator=" + operator + ", defaultValue=" + defaultValue
				+ ", customListOfValues=" + customListOfValues + "]";
	}
	
	
}
