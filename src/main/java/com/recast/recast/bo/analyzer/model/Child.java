package com.recast.recast.bo.analyzer.model;

public class Child {
	private String row;
	private String rowSpan;
	private String column;
	private String columnSpan;
	private String id;
	public String getRow() {
		return row;
	}
	public void setRow(String row) {
		this.row = row;
	}
	public String getRowSpan() {
		return rowSpan;
	}
	public void setRowSpan(String rowSpan) {
		this.rowSpan = rowSpan;
	}
	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}
	public String getColumnSpan() {
		return columnSpan;
	}
	public void setColumnSpan(String columnSpan) {
		this.columnSpan = columnSpan;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Child [row=" + row + ", rowSpan=" + rowSpan + ", column=" + column + ", columnSpan=" + columnSpan
				+ ", id=" + id + "]";
	}
	
	

}
