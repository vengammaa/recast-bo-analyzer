package com.recast.recast.bo.analyzer.tableau.model;

public class TableauJoin {
	private String identifier = "NOT APPICABLE";
	private String cardinality = "NOT APPICABLE";
	private String leftTable = "NOT APPICABLE";
	private String rightTable = "NOT APPICABLE";
	private String outerType = "NOT APPICABLE";
	private String expression = "NOT APPICABLE";
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public String getCardinality() {
		return cardinality;
	}
	public void setCardinality(String cardinality) {
		this.cardinality = cardinality;
	}
	public String getLeftTable() {
		return leftTable;
	}
	public void setLeftTable(String leftTable) {
		this.leftTable = leftTable;
	}
	public String getRightTable() {
		return rightTable;
	}
	public void setRightTable(String rightTable) {
		this.rightTable = rightTable;
	}
	public String getOuterType() {
		return outerType;
	}
	public void setOuterType(String outerType) {
		this.outerType = outerType;
	}
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	
	@Override
	public String toString() {
		return "TableauJoin [identifier=" + identifier + ", cardinality=" + cardinality + ", leftTable=" + leftTable
				+ ", rightTable=" + rightTable + ", outerType=" + outerType + ", expression=" + expression + "]";
	}

}
