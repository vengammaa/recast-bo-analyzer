package com.recast.recast.bo.analyzer.model;

import java.util.List;

public class BarRowData {
	String name;
	List<String> data;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getData() {
		return data;
	}

	public void setData(List<String> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "BarRowData [name=" + name + ", data=" + data + "]";
	}

	
}
