package com.recast.recast.bo.analyzer.tableau.model;

import java.util.ArrayList;
import java.util.List;


public class TableauReportTab {

	private List<QueryFilter> queryFilters = new ArrayList<QueryFilter>();
	private List<TableauTableElement> tableauTableElements = new ArrayList<TableauTableElement>();

	
	public List<QueryFilter> getQueryFilters() {
		return queryFilters;
	}
	public void setQueryFilters(List<QueryFilter> queryFilters) {
		this.queryFilters = queryFilters;
	}

	public List<TableauTableElement> getTableauTableElements() {
		return tableauTableElements;
	}
	public void setTableauTableElements(List<TableauTableElement> tableauTableElements) {
		this.tableauTableElements = tableauTableElements;
	}
	@Override
	public String toString() {
		return "TableauReportTab [queryFilters=" + queryFilters + ", tableauTableElements=" + tableauTableElements
				+ "]";
	}
	
	
	
}
