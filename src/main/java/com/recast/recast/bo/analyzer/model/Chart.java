package com.recast.recast.bo.analyzer.model;

import java.util.List;

public class Chart {
	List<String> date;
	String name=null;
	
	
	public List<String> getDate() {
		return date;
	}
	public void setDate(List<String> date) {
		this.date = date;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Chart [date=" + date + ", name=" + name + "]";
	}

}

