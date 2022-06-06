package com.recast.recast.bo.analyzer.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BOChartElement implements Serializable{

	private static final long serialVersionUID = 1L;
//	Possible ChartTypes are
//	Pie, VerticalBar, VerticalStackedBar, Line, Doughnut, Bubble, Waterfall
//	HorizontalBar, CombinedBarLine, PieWithDepth, DualBar, TreeMap, TagCloud
	private String chartType;
	private String chartName;
	private int numberOfColumns;
//	Possible axes roles are
//	Pie - PieSectorSize, PieSectorColor
//	VerticalBar, VerticalStackedBar, Line, HorizontalBar, CombinedBarLine - Color, Category, Value
//	Doughnut, PieWithDepth - PieSectorSize, PieSectorColor, PieDepthSize
//	Bubble - Value1, Value2, BubbleWidth, BubbleHeight, Color, Shape
//	Waterfall - Category, Value
//	DualBar - Color, Category, Value1, Value2
//	TreeMap - RectangleTitle, RectangleWeight, Color
//	TagCloud - Category, TagsWeight, TagsFamily
	private Map<String, List<BOReportColumn>> roleAxisColumns = new HashMap<String, List<BOReportColumn>>();
	
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
	public Map<String, List<BOReportColumn>> getRoleAxisColumns() {
		return roleAxisColumns;
	}
	public void setRoleAxisColumns(Map<String, List<BOReportColumn>> roleAxisColumns) {
		this.roleAxisColumns = roleAxisColumns;
	}
	public int getNumberOfColumns() {
		return numberOfColumns;
	}
	public void setNumberOfColumns(int numberOfColumns) {
		this.numberOfColumns = numberOfColumns;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((chartType == null) ? 0 : chartType.hashCode());
		result = prime * result
				+ ((roleAxisColumns == null) ? 0 : roleAxisColumns.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BOChartElement other = (BOChartElement) obj;
		if (chartType == null) {
			if (other.chartType != null)
				return false;
		} else if (!chartType.equals(other.chartType))
			return false;
		if (roleAxisColumns == null) {
			if (other.roleAxisColumns != null)
				return false;
		} else if (!roleAxisColumns.equals(other.roleAxisColumns))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "BOChartElement [chartType=" + chartType + ", chartName=" + chartName + ", numberOfColumns="
				+ numberOfColumns + ", roleAxisColumns=" + roleAxisColumns + "]";
	}
	
	
}
