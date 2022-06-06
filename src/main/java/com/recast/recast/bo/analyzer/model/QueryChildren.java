package com.recast.recast.bo.analyzer.model;

import java.util.List;

public class QueryChildren {
	
	private String itemIdentifier;
	private String itemName;
	private String comparisonOperator;
	private String operandType;
	private List<String> operandValues;
	private String question;
	public String getItemIdentifier() {
		return itemIdentifier;
	}
	public void setItemIdentifier(String itemIdentifier) {
		this.itemIdentifier = itemIdentifier;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getComparisonOperator() {
		return comparisonOperator;
	}
	public void setComparisonOperator(String comparisonOperator) {
		this.comparisonOperator = comparisonOperator;
	}
	public String getOperandType() {
		return operandType;
	}
	public void setOperandType(String operandType) {
		this.operandType = operandType;
	}
	public List<String> getOperandValues() {
		return operandValues;
	}
	public void setOperandValues(List<String> operandValues) {
		this.operandValues = operandValues;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	@Override
	public String toString() {
		return "QueryChildren [itemIdentifier=" + itemIdentifier + ", itemName=" + itemName + ", comparisonOperator="
				+ comparisonOperator + ", operandType=" + operandType + ", operandValues=" + operandValues
				+ ", question=" + question + "]";
	}
	
	

}
