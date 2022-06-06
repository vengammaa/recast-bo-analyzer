package com.recast.recast.bo.analyzer.model;

public class UniverseReportModel {
	Integer id;
	String name;
	String description;
	String items;
	Integer prjRptAnalysisId;
	String universeSourceId;
	String tables;
	String joins;
	String dataSources;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getItems() {
		return items;
	}
	public void setItems(String items) {
		this.items = items;
	}

	
	public void setPrjRptAnalysisId(Integer prjRptAnalysisId) {
		this.prjRptAnalysisId = prjRptAnalysisId;
	}
	public String getUniverseSourceId() {
		return universeSourceId;
	}
	public void setUniverseSourceId(String universeSourceId) {
		this.universeSourceId = universeSourceId;
	}
	
	
	public String getTables() {
		return tables;
	}
	public void setTables(String tables) {
		this.tables = tables;
	}
	public String getJoins() {
		return joins;
	}
	public void setJoins(String joins) {
		this.joins = joins;
	}
	public Integer getPrjRptAnalysisId() {
		return prjRptAnalysisId;
	}
	
	
	public String getDataSources() {
		return dataSources;
	}
	public void setDataSources(String dataSources) {
		this.dataSources = dataSources;
	}
	@Override
	public String toString() {
		return "UniverseReportModel [id=" + id + ", name=" + name + ", description=" + description + ", items=" + items
				+ ", prjRptAnalysisId=" + prjRptAnalysisId + ", universeSourceId=" + universeSourceId + ", tables="
				+ tables + ", joins=" + joins + ", dataSources=" + dataSources + "]";
	}
	
	
}
