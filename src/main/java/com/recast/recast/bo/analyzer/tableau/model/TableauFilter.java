package com.recast.recast.bo.analyzer.tableau.model;

import java.util.ArrayList;
import java.util.List;

public class TableauFilter {
	private String id = "-1";
	private String name;
	private String function;
	private List<String> members = new ArrayList<String>();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	public List<String> getMembers() {
		return members;
	}
	public void setMembers(List<String> members) {
		this.members = members;
	}
	@Override
	public String toString() {
		return "TableauFilter [id=" + id + ", name=" + name + ", function=" + function + ", members=" + members + "]";
	}
	
	
}
