package com.recast.recast.bo.analyzer.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UniverseReport implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	String name;
	String connectionClass;
	String description;
	String items;
	Integer prjRptAnalysisId;
	String universeSourceId;
	String tables;
	String joins;
	String dataSources;
	String filters;
	
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
	public Integer getPrjRptAnalysisId() {
		return prjRptAnalysisId;
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
	
	public String getDataSources() {
		return dataSources;
	}
	public void setDataSources(String dataSources) {
		this.dataSources = dataSources;
	}
	public String getConnectionClass() {
		return connectionClass;
	}
	public void setConnectionClass(String connectionClass) {
		this.connectionClass = connectionClass;
	}
	public String getFilters() {
		return filters;
	}
	public void setFilters(String filters) {
		this.filters = filters;
	}
	@Override
	public String toString() {
		return "UniverseReport [id=" + id + ", name=" + name + ", connectionClass=" + connectionClass + ", description="
				+ description + ", items=" + items + ", prjRptAnalysisId=" + prjRptAnalysisId + ", universeSourceId="
				+ universeSourceId + ", tables=" + tables + ", joins=" + joins + ", dataSources=" + dataSources
				+ ", filters=" + filters + "]";
	}
	
	
	
	
	
}
