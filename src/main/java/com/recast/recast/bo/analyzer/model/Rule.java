package com.recast.recast.bo.analyzer.model;

public class Rule {
	private String ruleId;
	private String expressionId;
	private String operator;
	private String operand;
	private String actionBackgroundColor;
	public String getRuleId() {
		return ruleId;
	}
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	public String getExpressionId() {
		return expressionId;
	}
	public void setExpressionId(String expressionId) {
		this.expressionId = expressionId;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getOperand() {
		return operand;
	}
	public void setOperand(String operand) {
		this.operand = operand;
	}
	public String getActionBackgroundColor() {
		return actionBackgroundColor;
	}
	public void setActionBackgroundColor(String actionBackgroundColor) {
		this.actionBackgroundColor = actionBackgroundColor;
	}
	@Override
	public String toString() {
		return "Rule [ruleId=" + ruleId + ", expressionId=" + expressionId + ", operator=" + operator + ", operand="
				+ operand + ", actionBackgroundColor=" + actionBackgroundColor + "]";
	}
	
	
	

}
