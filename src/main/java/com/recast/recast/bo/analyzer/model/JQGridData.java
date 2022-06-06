
package com.recast.recast.bo.analyzer.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * @author 10616367
 * JQGridData pojo class is used to store the JQGrid data
 * @param <T> - JQGridData<T> of the specified type T 
 */
public class JQGridData<T> {

 /** Total number of pages */
 private int total;
 /** The current page number */
 private int page;
 /** Total number of records */
 private Long records;
 /** The actual data */
 private List<T> rows;
 /**
  * Constructor which assigns the values to the variables
  * @param total - Total number of pages
  * @param page - Current page number
  * @param records - Total number of records
  * @param rows - data in current row
  */
 public JQGridData(int total, int page, Long records, List<T> rows) {
  this.total = total;
  this.page = page;
  this.records = records;
  this.rows = rows;
 }

 /**
  * Class constructor
  */
 public JQGridData() {
}

public int getTotal() {
  return total;
 }

 public int getPage() {
  return page;
 }

 public Long getRecords() {
  return records;
 }

 public List<T> getRows() {
  return rows;
 }
 
 
 public void setTotal(int total) {
	this.total = total;
}

public void setPage(int page) {
	this.page = page;
}

public void setRecords(Long records) {
	this.records = records;
}

public void setRows(List<T> rows) {
	this.rows = rows;
}

public String getJsonString() throws JsonGenerationException, JsonMappingException, IOException{
  Map<String, Object> map = new HashMap<String, Object>();
  map.put("page", page);
  map.put("total", total);
  map.put("records", records);
  map.put("rows", rows);
  ObjectMapper mapper  = new ObjectMapper();
  
  return mapper.writeValueAsString(map);
 }

@Override
public String toString() {
	return "JQGridData [total=" + total + ", page=" + page + ", records=" + records + ", rows=" + rows + "]";
}


}
