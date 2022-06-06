package com.recast.recast.bo.analyzer.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BOUniverse {
	private int id;
	private String name;
	private String description;
	private Map<String, Set<BOItem>> items = new HashMap<String, Set<BOItem>>();
	private List<BOTable> tables = new ArrayList<BOTable>();
	private List<BOJoin> joins = new ArrayList<BOJoin>();
	private List<BONavigationPath> navigationPaths = new ArrayList<BONavigationPath>();

	
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

	
	
	public List<BOTable> getTables() {
		return tables;
	}
	public void setTables(List<BOTable> tables) {
		this.tables = tables;
	}
	public List<BOJoin> getJoins() {
		return joins;
	}
	public void setJoins(List<BOJoin> joins) {
		this.joins = joins;
	}
	
	
	public Map<String, Set<BOItem>> getItems() {
		return items;
	}
	public void setItems(Map<String, Set<BOItem>> items) {
		this.items = items;
	}
	public List<BONavigationPath> getNavigationPaths() {
		return navigationPaths;
	}
	public void setNavigationPaths(List<BONavigationPath> navigationPaths) {
		this.navigationPaths = navigationPaths;
	}
	@Override
	public String toString() {
		return "BOUniverse [id=" + id + ", name=" + name + ", description=" + description + ", items=" + items
				+ ", tables=" + tables + ", joins=" + joins + ", navigationPaths=" + navigationPaths + "]";
	}
	
	public String getBOItemsJSON() {
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
	
	public String getJoinsJSON() {
		String jsonStr;
		ObjectMapper obj = new ObjectMapper();
		try {
			jsonStr = obj.writeValueAsString(this.getJoins());
		} 
		catch (JsonProcessingException e) {
			jsonStr = "Error";
			e.printStackTrace();
		}
		return jsonStr;
	}
	
	
}
