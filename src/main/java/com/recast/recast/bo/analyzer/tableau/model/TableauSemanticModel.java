package com.recast.recast.bo.analyzer.tableau.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TableauSemanticModel {
	private int id = -1;
	private String name;
	private String connectionClass;
	private String description = "NOT APPICABLE";
	private Map<String, List<TableauItem>> items = new HashMap<String, List<TableauItem>>();
	private List<TableauTable> tables = new ArrayList<TableauTable>();
	private List<TableauJoin> joins = new ArrayList<TableauJoin>();
	private List<TableauFilter> filters = new ArrayList<TableauFilter>();
	public List<TableauFilter> getFilters() {
		return filters;
	}
	public void setFilters(List<TableauFilter> filters) {
		this.filters = filters;
	}

	private List<TableauNavigationPath> navigationPaths = new ArrayList<TableauNavigationPath>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	public Map<String, List<TableauItem>> getItems() {
		return items;
	}
	public void setItems(Map<String, List<TableauItem>> items) {
		this.items = items;
	}
	public List<TableauTable> getTables() {
		return tables;
	}
	public void setTables(List<TableauTable> tables) {
		this.tables = tables;
	}
	public List<TableauJoin> getJoins() {
		return joins;
	}
	public void setJoins(List<TableauJoin> joins) {
		this.joins = joins;
	}
	public List<TableauNavigationPath> getNavigationPaths() {
		return navigationPaths;
	}
	public void setNavigationPaths(List<TableauNavigationPath> navigationPaths) {
		this.navigationPaths = navigationPaths;
	}
	
	public String getConnectionClass() {
		return connectionClass;
	}
	public void setConnectionClass(String connectionClass) {
		this.connectionClass = connectionClass;
	}
	
	@Override
	public String toString() {
		return "TableauSemanticModel [id=" + id + ", name=" + name + ", connectionClass=" + connectionClass
				+ ", description=" + description + ", items=" + items + ", tables=" + tables + ", joins=" + joins
				+ ", filters=" + filters + ", navigationPaths=" + navigationPaths + "]";
	}
	public String getTablesJSON() {
		String jsonStr;
		ObjectMapper obj = new ObjectMapper();
		try {
			jsonStr = obj.writeValueAsString(this.getTables());
		} 
		catch (JsonProcessingException e) {
			jsonStr = "Error";
			e.printStackTrace();
		}
		return jsonStr;
	}
	
	public String getTableauItemsJSON() {
		String jsonStr;
		ObjectMapper obj = new ObjectMapper();
		try {
			jsonStr = obj.writeValueAsString(this.getItems());
		} 
		catch (JsonProcessingException e) {
			jsonStr = "Error";
			e.printStackTrace();
		}
		return jsonStr;
	}
	
	public String getTableauFiltersJSON() {
		String jsonStr;
		ObjectMapper obj = new ObjectMapper();
		try {
			jsonStr = obj.writeValueAsString(this.getFilters());
		}
		catch (JsonProcessingException e) {
			jsonStr = "Error";
			e.printStackTrace();
		}
		return jsonStr;
	}
	
}
