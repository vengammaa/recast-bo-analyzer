package com.recast.recast.bo.analyzer.model;

import java.util.List;

public class Zones {
	private List<Child> childList;
	
	

	public List<Child> getChildList() {
		return childList;
	}



	public void setChildList(List<Child> childList) {
		this.childList = childList;
	}



	@Override
	public String toString() {
		return "Zones [childList=" + childList + "]";
	}

	
	

}
