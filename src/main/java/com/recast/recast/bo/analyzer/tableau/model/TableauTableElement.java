package com.recast.recast.bo.analyzer.tableau.model;

import java.util.ArrayList;
import java.util.List;



public class TableauTableElement {
	
	private String caption;
	private String tableName;
	private int numberOfColumns;
	
	private List<TableauReportColumn> columnList = new ArrayList<TableauReportColumn>();
	private List<TableauColumnInstance> columnInstance = new ArrayList<TableauColumnInstance>();
	
	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
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

	public List<TableauReportColumn> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<TableauReportColumn> columnList) {
		this.columnList = columnList;
	}

	public List<TableauColumnInstance> getColumnInstance() {
		return columnInstance;
	}

	public void setColumnInstance(List<TableauColumnInstance> columnInstance) {
		this.columnInstance = columnInstance;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((columnList == null) ? 0 : columnList.hashCode());
		result = prime * result
				+ ((columnInstance == null) ? 0 : columnInstance.hashCode());
		result = prime * result + ((columnList == null) ? 0 : columnList.hashCode());

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
		TableauTableElement other = (TableauTableElement) obj;
		if (columnInstance == null) {
			if (other.columnInstance != null)
				return false;
		} else if (!columnInstance.equals(other.columnInstance))
			return false;
		if (columnInstance == null) {
			if (other.columnInstance != null)
				return false;
		} else if (!columnInstance.equals(other.columnInstance))
			return false;
		if (columnList == null) {
			if (other.columnList != null)
				return false;
		} else if (!columnList.equals(other.columnList))
			return false;
		
		return true;
	}
	
	
	
	@Override
	public String toString() {
		return "TableauTableElement [caption=" + caption + ", tableName=" + tableName + ", numberOfColumns="
				+ numberOfColumns + ", columnList=" + columnList + ", columnInstance=" + columnInstance + "]";
	}

	
	
}
