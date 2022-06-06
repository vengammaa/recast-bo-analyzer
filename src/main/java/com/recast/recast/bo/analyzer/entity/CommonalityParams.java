package com.recast.recast.bo.analyzer.entity;


import javax.persistence.*;
import java.io.Serializable;

/**
 * <h2> CommanlityParams entity: we are taking data as list of list of string from commonality_params table</h2>
 * 
 * 
 * @author TeamA(Sadia, Timothy, Naushaba)
 *
 */




@Entity
@Table(name="commonality_params")
public class CommonalityParams implements Serializable {


    private static final long serialVersionUID = 1L;

/**
 * <p> this id is use as the primary key</p>
 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//
    @Column(name = "id")
    private int id;

    /**
     * <p>taskId is a foreign key associated to the report_id</p>
     */
    
    //FOREIGN KEY (`task_id`) REFERENCES `prj_rpt_analysis` (`id`)
    @Column(name = "task_id")
    @JoinColumn(name = "task_id", referencedColumnName = "task_id")
    private int taskId;

    
    /**
     * <p>reportId is an integer that is associated with a report </p>
     */
    
    @Column(name = "report_id")
    private String reportId;

    /**
     * <p>report name is a String that is associated with a report</p>
     * 
     */
    
    @Column(name = "report_name")
    private String reportName;

    /**
     * <p>dataSourceName is a string that is associated with a report</p>  
     */
    
    @Column(name = "data_source_name")
    private String dataSourceName;

    
    /**
     * <p>tableName is a string that is associated with a report</p>  
     */
    @Column(name = "table_name")
    private String tableName;

    /**
     * <p>columnName is a string that is associated with a report</p>  
     */
    @Column(name = "column_name")
    private String columnName;

    /**
     * <p>columnType is a string that is associated with a report. Column name has multiple column types </p>  
     */
    @Column(name = "column_Type")
    private String columnType;

    @Column(name = "column_Alias")
    private String columnAlias;
    
    /**
     * <p>dimensionList is a string that is associated with a report. In the CommonalityParamsRepo, we used report id to get dimensionList info</p>  
     */
    @Column(name = "dimension_list")
    private String dimensionList;
    
    /**
     * <p>measureList is a string that is associated with a report.In the CommonalityParamsRepo, we used report id to get measureList info</p>  
     */
    @Column(name = "measure_list")
    private String measureList;  
    
    /**
     * <p>variableList is a string that is associated with a report.In the CommonalityParamsRepo, we used report id to get variableList info</p>  
     */
    @Column(name = "variable_list")
    private String variableList; 
    

    /**
     * <p>attributeList is a string that is associated with a report.In the CommonalityParamsRepo, we used report id to get attributeList info</p>  
     */
    @Column(name = "attribute_list")
    private String attributeList; 
   
   /**
    * <p>Empty constructor to create CommonalityParams Entity</P> 
    */
    public CommonalityParams() {

    }

   /**
    * 
    * @param id this is primary key
    * @param taskId this is foreign key
    * @param reportId this number is associated with report
    * @param reportName this is used as name of the report
    * @param dataSourceName this is the database name 
    * @param tableName this is table name
    * @param columnName this is column name
    * @param columnType this is describe column type
    * @param columnAlias this is column Alias 
    * @param dimensionList this describes dimension of the report
    * @param measureList this describes measure of the report
    * @param variableList this describes variable of the report
    * @param attributeList this describes attribute of the report
    */

    public CommonalityParams(int id, int taskId, String reportId, String reportName, String dataSourceName,
			String tableName, String columnName, String columnType, String columnAlias, String dimensionList,
			String measureList, String variableList, String attributeList) {
		super();
		this.id = id;
		this.taskId = taskId;
		this.reportId = reportId;
		this.reportName = reportName;
		this.dataSourceName = dataSourceName;
		this.tableName = tableName;
		this.columnName = columnName;
		this.columnType = columnType;
		this.columnAlias = columnAlias;
		this.dimensionList = dimensionList;
		this.measureList = measureList;
		this.variableList = variableList;
		this.attributeList = attributeList;
	}


    /**
     * <p>this method take an integer and set it as Primary key in the table </p>
     * @param id it is primary key in the commonalityParams Table 
     * 
     */
	public void setId(int id) {
        this.id = id;
    }

	/**
	 * 
	 * @return
	 */
    public int getId() {
        return id;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    

    public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getColumnAlias() {
        return columnAlias;
    }

    public void setColumnAlias(String columnAlias) {
        this.columnAlias = columnAlias;
    }

    public String getDimensionList() {
		return dimensionList;
	}



	public void setDimensionList(String dimensionList) {
		this.dimensionList = dimensionList;
	}



	public String getMeasureList() {
		return measureList;
	}



	public void setMeasureList(String measureList) {
		this.measureList = measureList;
	}



	public String getVariableList() {
		return variableList;
	}



	public void setVariableList(String variableList) {
		this.variableList = variableList;
	}



	public String getAttributeList() {
		return attributeList;
	}



	public void setAttributeList(String attributeList) {
		this.attributeList = attributeList;
	}

	@Override
	public String toString() {
		return "CommonalityParams [id=" + id + ", taskId=" + taskId + ", reportId=" + reportId + ", reportName="
				+ reportName + ", dataSourceName=" + dataSourceName + ", tableName=" + tableName + ", columnName="
				+ columnName + ", columnType=" + columnType + ", columnAlias=" + columnAlias + ", dimensionList="
				+ dimensionList + ", measureList=" + measureList + ", variableList=" + variableList + ", attributeList="
				+ attributeList + "]";
	}

}
