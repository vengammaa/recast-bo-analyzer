package com.recast.recast.bo.analyzer.tableau.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TableauVisualElements {

	private String type;
	
	private String category;
	
	private String elementName;
	
	private String elementId;
	
	private String parentId;
	
	private String alwaysFlag;
	
	private String xPosition;
	
	private String yPosition;
	
	private String minimalWidth;
	
	private String minimalHeight;

	private String backgroundColor;
	
	private String backgroundColorAlpha;
	
	private String border;
	
	private String font;
	
	private String allignment;
	
	private String formula;
	
	private String chartType;
	
	private String chartName;
	
	private String chartTitle;
	
	private String chartLegend;
	
	private String chartPlotArea;
	
	//private String chartAxes;
	
	
	private List<Axes> chartAxes = new ArrayList();
		
	private String maxWidth;
	
	private String maxHeight;
	
	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

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

	public String getxPosition() {
		return xPosition;
	}

	public void setxPosition(String xPosition) {
		this.xPosition = xPosition;
	}

	public String getyPosition() {
		return yPosition;
	}

	public void setyPosition(String yPosition) {
		this.yPosition = yPosition;
	}

	public String getMinimalWidth() {
		return minimalWidth;
	}

	public void setMinimalWidth(String minimalWidth) {
		this.minimalWidth = minimalWidth;
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

	public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
	}

	public String getAllignment() {
		return allignment;
	}

	public void setAllignment(String allignment) {
		this.allignment = allignment;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public String getElementId() {
		return elementId;
	}

	public void setElementId(String elementId) {
		this.elementId = elementId;
	}
	
	public String getBackgroundColorAlpha() {
		return backgroundColorAlpha;
	}

	public void setBackgroundColorAlpha(String backgroundColorAlpha) {
		this.backgroundColorAlpha = backgroundColorAlpha;
	}

	public String getChartType() {
		return chartType;
	}

	public void setChartType(String chartType) {
		this.chartType = chartType;
	}

	public String getChartName() {
		return chartName;
	}

	public void setChartName(String chartName) {
		this.chartName = chartName;
	}

	public String getChartTitle() {
		return chartTitle;
	}

	public void setChartTitle(String chartTitle) {
		this.chartTitle = chartTitle;
	}

	public String getChartLegend() {
		return chartLegend;
	}

	public void setChartLegend(String chartLegend) {
		this.chartLegend = chartLegend;
	}

	public String getChartPlotArea() {
		return chartPlotArea;
	}

	public void setChartPlotArea(String chartPlotArea) {
		this.chartPlotArea = chartPlotArea;
	}

//	public String getChartAxes() {
//		return chartAxes;
//	}
//
//	public void setChartAxes(String chartAxes) {
//		this.chartAxes = chartAxes;
//	}

	
	

	public String getParentId() {
		return parentId;
	}

	public List<Axes> getChartAxes() {
		return chartAxes;
	}

	public void setChartAxes(List<Axes> chartAxes) {
		this.chartAxes = chartAxes;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	
	public String getMaxWidth() {
		return maxWidth;
	}

	public void setMaxWidth(String maxWidth) {
		this.maxWidth = maxWidth;
	}

	public String getMaxHeight() {
		return maxHeight;
	}

	public void setMaxHeight(String maxHeight) {
		this.maxHeight = maxHeight;
	}

	@Override
	public String toString() {
		return "BOVisualElements [type=" + type + ", category=" + category + ", elementName=" + elementName
				+ ", elementId=" + elementId + ", parentId=" + parentId + ", alwaysFlag=" + alwaysFlag + ", xPosition="
				+ xPosition + ", yPosition=" + yPosition + ", minimalWidth=" + minimalWidth + ", minimalHeight="
				+ minimalHeight + ", backgroundColor=" + backgroundColor + ", backgroundColorAlpha="
				+ backgroundColorAlpha + ", border=" + border + ", font=" + font + ", allignment=" + allignment
				+ ", formula=" + formula + ", chartType=" + chartType + ", chartName=" + chartName + ", chartTitle="
				+ chartTitle + ", chartLegend=" + chartLegend + ", chartPlotArea=" + chartPlotArea + ", chartAxes="
				+ chartAxes + ", maxWidth=" + maxWidth + ", maxHeight=" + maxHeight + "]";
	}

	
	public String getAxisListJSON() {
		
		String jsonStr;
		ObjectMapper obj = new ObjectMapper();
		try {
			jsonStr = obj.writeValueAsString(this.getChartAxes());
		} 
		catch (JsonProcessingException e) {
			jsonStr = "Error";
			e.printStackTrace();
		}
		return jsonStr;		
	}

	


}
