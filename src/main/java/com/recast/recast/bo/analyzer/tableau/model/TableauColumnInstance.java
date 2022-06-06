package com.recast.recast.bo.analyzer.tableau.model;

public class TableauColumnInstance {

	private String column;
	private String derivation;
	private String columnName;
	private String pivot;
	private String type;

	
	public String getColumn() {
		return column;
	}


	public void setColumn(String column) {
		this.column = column;
	}


	public String getDerivation() {
		return derivation;
	}


	public void setDerivation(String derivation) {
		this.derivation = derivation;
	}


	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}


	public String getPivot() {
		return pivot;
	}


	public void setPivot(String pivot) {
		this.pivot = pivot;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	@Override
	public String toString() {
		return "TableauColumnInstance [column=" + column + ", derivation=" + derivation + ", columnName=" + columnName
				+ ", pivot=" + pivot + ", type=" + type + "]";
	}
	
	
	
	
}
