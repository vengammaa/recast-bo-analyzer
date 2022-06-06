package com.recast.recast.bo.analyzer.model;

public class BOVisualizationColumn {

	
	private String tableName;
	
	private String columnNames;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(String columnNames) {
		this.columnNames = columnNames;
	}

	@Override
	public String toString() {
		return "BOVisualizationColumn [tableName=" + tableName + ", columnNames=" + columnNames + "]";
	}

	
}
