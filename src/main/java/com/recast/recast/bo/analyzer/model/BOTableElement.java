package com.recast.recast.bo.analyzer.model;

import java.util.ArrayList;
import java.util.List;

public class BOTableElement {
	//VTable, Section, XTable, HTable
	private String tableType;
	private String tableName;
	private int numberOfColumns;
	private List<BOReportColumn> rowAxis = new ArrayList<BOReportColumn>();
	private List<BOReportColumn> columnAxis = new ArrayList<BOReportColumn>();
//	corneraxis or bodyaxis
	private List<BOReportColumn> cornerAxis = new ArrayList<BOReportColumn>();
	
	public String getTableType() {
		return tableType;
	}
	public void setTableType(String tableType) {
		this.tableType = tableType;
	}
	public List<BOReportColumn> getRowAxis() {
		return rowAxis;
	}
	public void setRowAxis(List<BOReportColumn> rowAxis) {
		this.rowAxis = rowAxis;
	}
	public List<BOReportColumn> getColumnAxis() {
		return columnAxis;
	}
	public void setColumnAxis(List<BOReportColumn> columnAxis) {
		this.columnAxis = columnAxis;
	}
	public List<BOReportColumn> getCornerAxis() {
		return cornerAxis;
	}
	public void setCornerAxis(List<BOReportColumn> cornerAxis) {
		this.cornerAxis = cornerAxis;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
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
				+ ((columnAxis == null) ? 0 : columnAxis.hashCode());
		result = prime * result
				+ ((cornerAxis == null) ? 0 : cornerAxis.hashCode());
		result = prime * result + ((rowAxis == null) ? 0 : rowAxis.hashCode());
		result = prime * result
				+ ((tableType == null) ? 0 : tableType.hashCode());
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
		BOTableElement other = (BOTableElement) obj;
		if (columnAxis == null) {
			if (other.columnAxis != null)
				return false;
		} else if (!columnAxis.equals(other.columnAxis))
			return false;
		if (cornerAxis == null) {
			if (other.cornerAxis != null)
				return false;
		} else if (!cornerAxis.equals(other.cornerAxis))
			return false;
		if (rowAxis == null) {
			if (other.rowAxis != null)
				return false;
		} else if (!rowAxis.equals(other.rowAxis))
			return false;
		if (tableType == null) {
			if (other.tableType != null)
				return false;
		} else if (!tableType.equals(other.tableType))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "BOTableElement [tableType=" + tableType + ", tableName=" + tableName + ", numberOfColumns="
				+ numberOfColumns + ", rowAxis=" + rowAxis + ", columnAxis=" + columnAxis + ", cornerAxis=" + cornerAxis
				+ "]";
	}
	
	
}
