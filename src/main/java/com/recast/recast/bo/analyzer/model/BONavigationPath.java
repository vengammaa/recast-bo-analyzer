package com.recast.recast.bo.analyzer.model;

import java.util.ArrayList;
import java.util.List;

public class BONavigationPath {
	private String name;
	private List<String> children = new ArrayList<String>();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getChildren() {
		return children;
	}
	public void setChildren(List<String> children) {
		this.children = children;
	}
	@Override
	public String toString() {
		return "BONavigationPath [name=" + name + ", children=" + children + "]";
	}
	
	
}
