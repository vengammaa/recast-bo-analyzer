package com.recast.recast.bo.analyzer.model;

public class BOJoin {
	private String identifier;
	private String cardinality;
	private String leftTable;
	private String rightTable;
	private String outerType;
	private String expression;
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
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
	@Override
	public String toString() {
		return "BOJoin [identifier=" + identifier + ", cardinality=" + cardinality + ", leftTable=" + leftTable
				+ ", rightTable=" + rightTable + ", outerType=" + outerType + ", expression=" + expression + "]";
	}
	
	
	
	
}
