package com.recast.recast.bo.analyzer.model;

import java.util.List;

public class Alerters {
	private String alerterId;
	private String alerterName;
	private List<Rule> ruleList;
	public String getAlerterId() {
		return alerterId;
	}
	public void setAlerterId(String alerterId) {
		this.alerterId = alerterId;
	}
	public String getAlerterName() {
		return alerterName;
	}
	public void setAlerterName(String alerterName) {
		this.alerterName = alerterName;
	}
	public List<Rule> getRuleList() {
		return ruleList;
	}
	public void setRuleList(List<Rule> ruleList) {
		this.ruleList = ruleList;
	}
	@Override
	public String toString() {
		return "Alerters [alerterId=" + alerterId + ", alerterName=" + alerterName + ", ruleList=" + ruleList + "]";
	}
	
	

}
