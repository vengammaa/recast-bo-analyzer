package com.recast.recast.bo.analyzer.model;

import java.util.List;

public class BOQueryFilter {
	
	private String dataProviderId;
	private String logicalOperator;
	private List<QueryChildren> queryChildrenList;
	
	public String getDataProviderId() {
		return dataProviderId;
	}
	public void setDataProviderId(String dataProviderId) {
		this.dataProviderId = dataProviderId;
	}
	public String getLogicalOperator() {
		return logicalOperator;
	}
	public void setLogicalOperator(String logicalOperator) {
		this.logicalOperator = logicalOperator;
	}
	public List<QueryChildren> getQueryChildrenList() {
		return queryChildrenList;
	}
	public void setQueryChildrenList(List<QueryChildren> queryChildrenList) {
		this.queryChildrenList = queryChildrenList;
	}
	@Override
	public String toString() {
		return "BOQueryFilter [dataProviderId=" + dataProviderId + ", logicalOperator=" + logicalOperator
				+ ", queryChildrenList=" + queryChildrenList + "]";
	}
	
	

}
