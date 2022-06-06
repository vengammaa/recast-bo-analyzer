package com.recast.recast.bo.analyzer.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.recast.recast.bo.analyzer.model.BOAnalyzerModel;
import com.recast.recast.bo.analyzer.model.BOColumn;
import com.recast.recast.bo.analyzer.model.BOItem;
import com.recast.recast.bo.analyzer.model.BOReportQuery;
import com.recast.recast.bo.analyzer.model.BOReportQueryColumn;
import com.recast.recast.bo.analyzer.model.BOTable;
import com.recast.recast.bo.analyzer.model.BOUniverse;

public class CheckUseService {
	final static String DIMENSION = "Dimension", MEASURE = "Measure", ATTRIBUTE = "Attribute", DIMENSIONKEY = "dimension", MEASUREKEY = "measure", ATTRIBUTEKEY = "attribute";
	static final Logger logger = Logger.getLogger(CheckUseService.class);
	
	static void checkUse(List<BOAnalyzerModel> reports, List<BOUniverse> universes) {
		for(BOAnalyzerModel report : reports) {
			for(BOReportQuery query : report.getBoReportQueries()) {
				processBOReportQuery(query, universes);
				processFormulae(universes);
			}		
		}
	}
 
	static void processBOReportQuery(BOReportQuery query, List<BOUniverse> universes) {
		for(BOReportQueryColumn column : query.getBoReportQueryColumns()) {
			logger.info("Current checking: " + column.getColumnName());
			for(BOUniverse unv : universes) {
				Set<BOItem> items = unv.getItems().get(column.getColumnQualification().toLowerCase() + "s");
				if(items != null) {
					for(BOItem item : items) {
						if(item.getName().equals(column.getColumnName())) {
							logger.info("Match Found");
							item.setIsUsed(true);
						
						}
					}
				}
			}
		}
		if(query.getQuery() != null) {

		}
	}
	
	static void processFormulae(List<BOUniverse> universes){
		/* iterate all universes */
		for(BOUniverse unv : universes) {
			/* iterate all types of items in each universe */
			for(Map.Entry<String, Set<BOItem>> x : unv.getItems().entrySet()) {
				/* iterate all items of each type */
				for(BOItem item : x.getValue()) {
					/* if item is used only then process the query */
					if(item.getIsUsed()) {
						Map<String, Set<String>> objects = processQuery(item.getSelect(), 2);
						/* iterate all tables in the universe */
						for(BOTable table: unv.getTables()) {
							/* if table is used in query then set it as used */
							if(objects.containsKey(table.getName())) {
								logger.info("Table " + table.getName() + " is set as used");
								table.setIsUsed(true);
								/* iterate all columns in the table */
								for(BOColumn column : table.getColumns()) {
									/* if column is used in the query set it as used */
									if(objects.get(table.getName()).contains(column.getName())) {
										logger.info("Column " + column.getName() + " is set as used");
										column.setIsUsed(true);
									}
								}
							}
						}
					}				
				}
			}
		}
	}
	
	public static Map<String, Set<String>> processQuery(String s, int format) {
		Map<String, Set<String>> objects = new HashMap<String, Set<String>> ();
		Pattern pattern;
		if(format == 3)
			pattern = Pattern.compile("[a-zA-Z0-9_]+\\.[a-zA-Z0-9_]+\\.[a-zA-Z0-9_]+"); // db.table.column
		else if(format == 2)
			pattern = Pattern.compile("[a-zA-Z0-9_]+\\.[a-zA-Z0-9_]+"); // table.column
		else 
			return objects;
		Matcher matcher = pattern.matcher(s);
		while (matcher.find()) {
			String dotSplit[] = matcher.group().split("\\.");
			/* table.column */
			if(dotSplit.length == 2) {  
				if(!objects.containsKey(dotSplit[0])) {
					objects.put(dotSplit[0], new HashSet<String>());
				}
				objects.get(dotSplit[0]).add(dotSplit[1]);
			}	
			/* db.table.column */
			else if(dotSplit.length == 3) {
				if(!objects.containsKey(dotSplit[1])) {
					objects.put(dotSplit[1], new HashSet<String>());
				}
				objects.get(dotSplit[1]).add(dotSplit[2]);
			}			
		}
		for(Map.Entry<String, Set<String>> entry : objects.entrySet()) {
			System.out.println("Table: " + entry.getKey());
			System.out.println("Columns:");
			for(String value : entry.getValue()) {
				System.out.println(value);
			}
		}
		return objects;
	}
}
