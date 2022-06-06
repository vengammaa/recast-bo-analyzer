package com.recast.recast.bo.analyzer.model;

public class BOPageZone {

	
	private String elementName;
	
	private String alwaysFlag;
	
	private String minimalHeight;
	
	private String backgroundColor;
	
	private String border;
	
	

	public String getElementName() {
		return elementName;
	}

	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

	public String getAlwaysFlag() {
		return alwaysFlag;
	}

	public void setAlwaysFlag(String alwaysFlag) {
		this.alwaysFlag = alwaysFlag;
	}

	public String getMinimalHeight() {
		return minimalHeight;
	}

	public void setMinimalHeight(String minimalHeight) {
		this.minimalHeight = minimalHeight;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public String getBorder() {
		return border;
	}

	public void setBorder(String border) {
		this.border = border;
	}

	@Override
	public String toString() {
		return "BOPageZone [elementName=" + elementName + ", alwaysFlag=" + alwaysFlag + ", minimalHeight="
				+ minimalHeight + ", backgroundColor=" + backgroundColor + ", border=" + border + "]";
	}


	
}
