package com.recast.recast.bo.analyzer.tableau.model;

import java.util.ArrayList;
import java.util.List;


public class TableauStyleRule {

	private String elementName;
	private List<TableauFormat> formatList = new ArrayList<TableauFormat>();
	private List<TableauEncoding> encodingList = new ArrayList<TableauEncoding>();
	
	public String getElementName() {
		return elementName;
	}
	public void setElementName(String elementName) {
		this.elementName = elementName;
	}
	public List<TableauFormat> getFormatList() {
		return formatList;
	}
	public void setFormatList(List<TableauFormat> formatList) {
		this.formatList = formatList;
	}
	
	
	public List<TableauEncoding> getEncodingList() {
		return encodingList;
	}
	public void setEncodingList(List<TableauEncoding> encodingList) {
		this.encodingList = encodingList;
	}
	@Override
	public String toString() {
		return "TableauStyleRule [elementName=" + elementName + ", formatList=" + formatList + ", encodingList="
				+ encodingList + "]";
	}
	
	
}
