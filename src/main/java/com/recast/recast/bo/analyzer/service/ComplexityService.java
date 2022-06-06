package com.recast.recast.bo.analyzer.service;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ComplexityService {
	
	private static final Logger logger = LoggerFactory.getLogger(ComplexityService.class);
	
	public static String minNumberOfDataSources = "";
	public static String minNumberOfReportElements = "";
	public static String minNumberOfObjects = "";
	public static String minNumberOfQueries = "";
	public static String minNumberOfVariables = "";
	public static String minNumberOfReportTabs = "";
	
	public static String minNumberOfPages = "";
	public static String minNumberOfContainers = "";
	public static String minNumberOfFilters = "";
	
	public static String minNumberOfPromptPages="";
	
	public static String minNumberOfComplexCalculations = "";
	
	
	public static String maxNumberOfDataSources = "";
	public static String maxNumberOfReportElements = "";
	public static String maxNumberOfObjects = "";
	public static String maxNumberOfQueries = "";
	public static String maxNumberOfVariables = "";
	public static String maxNumberOfReportTabs = "";
	public static String maxNumberOfPages = "";
	public static String maxNumberOfContainers = "";
	public static String maxNumberOfFilters = "";
	public static String maxNumberOfPromptPages="";
	
	public static String maxNumberOfComplexCalculations = "";
	
	@Transactional
	public String classifyBOComplexityReport(String BOComplexityData) 
	{
		
		FileReader fileReader;
    try {
			fileReader = new FileReader(getClass().getClassLoader().getResource("").getPath().substring(1).replaceAll("%20", " ")+"/complexity.properties");
			Properties p=new Properties();
			 p.load(fileReader);
		minNumberOfDataSources = p.getProperty("BOminNumberOfDataSources");
		maxNumberOfDataSources = p.getProperty("BOmaxNumberOfDataSources");
		
		minNumberOfReportElements = p.getProperty("BOminNumberOfReportElements");
		maxNumberOfReportElements = p.getProperty("BOmaxNumberOfReportElements");
		
		minNumberOfObjects = p.getProperty("BOminNumberOfObjects");
		maxNumberOfObjects = p.getProperty("BOmaxNumberOfObjects");
		
		minNumberOfQueries = p.getProperty("BOminNumberOfQueries");
		maxNumberOfQueries = p.getProperty("BOmaxNumberOfQueries");
		
		minNumberOfVariables = p.getProperty("BOminNumberOfVariables");
		maxNumberOfVariables = p.getProperty("BOmaxNumberOfVariables");
		
		minNumberOfReportTabs = p.getProperty("BOminNumberOfReportTabs");
		maxNumberOfReportTabs =	p.getProperty("BOmaxNumberOfReportTabs");	
		
		JSONArray complexityArray = new JSONArray(BOComplexityData);
		
		String res = "Simple";
		
		int numberOfDataSources = 0;
		int numberOfQueries = 0;
		int numberOfVariables = 0;
		int numberOfReportTabs = 0;
		int numberOfReportElements = 0;
		int numberOfObjects = 0;
		
		for(int i=0;i<complexityArray.length();i++)
	 	{
	 		JSONObject complexityObj = complexityArray.getJSONObject(i);
	 		
	 		numberOfDataSources = complexityObj.getInt("numberOfDataSources");
	 		numberOfQueries = complexityObj.getInt("numberOfQueries");
	 		numberOfVariables =  complexityObj.getInt("numberOfVariables");
	 		numberOfReportTabs =  complexityObj.getInt("numberOfReportTabs");
	 		numberOfReportElements = complexityObj.getInt("numberOfReportElements");
	 		numberOfObjects = complexityObj.getInt("numberOfObjects");
	 	}
		
		List<String> complexity = new ArrayList<String>();
		
	    complexity.add(compareParameters(numberOfDataSources,Integer.parseInt(minNumberOfDataSources),Integer.parseInt(maxNumberOfDataSources)));
	    complexity.add(compareParameters(numberOfReportElements,Integer.parseInt(minNumberOfReportElements),Integer.parseInt(maxNumberOfReportElements)));
	    complexity.add(compareParameters(numberOfObjects,Integer.parseInt(minNumberOfObjects),Integer.parseInt(maxNumberOfObjects)));
	    complexity.add(compareParameters(numberOfQueries,Integer.parseInt(minNumberOfQueries),Integer.parseInt(maxNumberOfQueries)));
	    complexity.add(compareParameters(numberOfVariables,Integer.parseInt(minNumberOfVariables),Integer.parseInt(maxNumberOfVariables)));
	    complexity.add(compareParameters(numberOfReportTabs,Integer.parseInt(minNumberOfReportTabs),Integer.parseInt(maxNumberOfReportTabs)));
	    
		if(complexity.contains("C"))
		{
			res = "Complex";
		}
		else if(complexity.contains("M"))
		{
			res = "Medium";
		}
		else
		{
			res = "Simple";
		}	
		return res;
    }
    catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    return "Simple";
		
	}
	public String compareParameters(int a,int b,int c)
	{
		String res ="";
		if(a<b)
		{
			res = "S";
		}
		else if(a>=b && a<c)
		{
			res = "M";
		}
		else
		{
			res = "C";
		}
		return res;
	}

	@Transactional
	public String classifyCognosComplexityReport(String complexityData) {
		// TODO Auto-generated method stub
		
		//JSONObject complexityDataJSON = new JSONObject(complexityData);
		
		FileReader fileReader;
		try {
			fileReader = new FileReader(getClass().getClassLoader().getResource("").getPath().substring(1).replaceAll("%20", " ")+"/complexity.properties");
			Properties p=new Properties();
			 p.load(fileReader);
			 
			 
			 	minNumberOfQueries = p.getProperty("CognosminNumberOfQueries");
				maxNumberOfQueries = p.getProperty("CognosmaxNumberOfQueries");
				
				minNumberOfPages = p.getProperty("CognosminNumberOfPages");
				maxNumberOfPages = p.getProperty("CognosmaxNumberOfPages");
				
				minNumberOfContainers = p.getProperty("CognosminNumberOfContainers");
				maxNumberOfContainers =	p.getProperty("CognosmaxNumberOfContainers");	
			 
				minNumberOfFilters = p.getProperty("CognosminNumberOfFilters");
				maxNumberOfFilters = p.getProperty("CognosmaxNumberOfFilters");
			 
				minNumberOfPromptPages = p.getProperty("CognosminNumberOfPromptPages");
				maxNumberOfPromptPages = p.getProperty("CognosmaxNumberOfPromptPages");
				
			 	System.out.println("complexity JSON::"+complexityData);
				
			 	JSONArray complexityArray = new JSONArray(complexityData);
				
			 	int noOfQueries =0;
			 	int noOfContainers=0;
			 	int noOfPages=0;
			 	int noOfFilters=0;
			 	int noOfPromptPages=0;
			 	String res = "Simple";
			 	
			 	
			 	for(int i=0;i<complexityArray.length();i++)
			 	{
			 		JSONObject complexityObj = complexityArray.getJSONObject(i);
			 		
			 		noOfQueries = complexityObj.getInt("noOfQueries");
			 		noOfContainers = complexityObj.getInt("noOfContainers");
			 		noOfPages =  complexityObj.getInt("noOfPages");
			 		noOfFilters =  complexityObj.getInt("noOfFilters");
			 		noOfPromptPages = complexityObj.getInt("noOfPromptPages");
			 	}
				
			 	
			 	List<String> complexity = new ArrayList<String>();
				
			    complexity.add(compareParameters(noOfQueries,Integer.parseInt(minNumberOfQueries),Integer.parseInt(maxNumberOfQueries)));
			    complexity.add(compareParameters(noOfContainers,Integer.parseInt(minNumberOfContainers),Integer.parseInt(maxNumberOfContainers)));
			    complexity.add(compareParameters(noOfPages,Integer.parseInt(minNumberOfPages),Integer.parseInt(maxNumberOfPages)));
			    complexity.add(compareParameters(noOfFilters,Integer.parseInt(minNumberOfFilters),Integer.parseInt(maxNumberOfFilters)));			
				complexity.add(compareParameters(noOfPromptPages,Integer.parseInt(minNumberOfPromptPages),Integer.parseInt(maxNumberOfPromptPages)));
			    if(complexity.contains("C"))
				{
					res = "Complex";
				}
				else if(complexity.contains("M"))
				{
					res = "Medium";
				}
				else
				{
					res = "Simple";
				}		
				return res;
			    
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Simple";
	}
	

	@Transactional
	public String classifyComplexityTableau(String complexityData) {
	FileReader fileReader;
	try {
	fileReader = new FileReader(getClass().getClassLoader().getResource("").getPath().substring(1).replaceAll("%20", " ") + "/complexity.properties");
	Properties p = new Properties();
	p.load(fileReader);
	minNumberOfDataSources = p.getProperty("TableauMinNumberOfDataSources");
	maxNumberOfDataSources = p.getProperty("TableauMaxNumberOfDataSources");
	minNumberOfVariables = p.getProperty("TableauMinNumberOfVariables");
	maxNumberOfVariables = p.getProperty("TableauMaxNumberOfVariables");
	minNumberOfFilters = p.getProperty("TableauMinNumberOfFilters");
	maxNumberOfFilters = p.getProperty("TableauMaxNumberOfFilters");
	minNumberOfComplexCalculations = p.getProperty("TableauMinNumberOfComplexCalculations");
	maxNumberOfComplexCalculations = p.getProperty("TableauMaxNumberOfComplexCalculations");
	System.out.println("complexity JSON:: " + complexityData);
	JSONArray complexityArray = new JSONArray(complexityData);
	Integer numberOfDataSources = 0;
	Integer numberOfFilters = 0;
	Integer numberOfVariables = 0;
	Integer numberOfComplexCalculations = 0;
	String res = "Simple";
	for (int i=0; i<complexityArray.length(); i++) {
	JSONObject complexityObj = complexityArray.getJSONObject(i);
	numberOfDataSources = complexityObj.getInt("numberOfDataSources");
	numberOfFilters = complexityObj.getInt("numberOfFilters");
	numberOfVariables = complexityObj.getInt("numberOfVariables");
	numberOfComplexCalculations = complexityObj.getInt("numberOfComplexCalculations");
	}
	List<String> complexity = new ArrayList<String>();
	complexity.add(compareParameters(numberOfDataSources,Integer.parseInt(minNumberOfDataSources),Integer.parseInt(maxNumberOfDataSources)));
	complexity.add(compareParameters(numberOfFilters,Integer.parseInt(minNumberOfFilters),Integer.parseInt(maxNumberOfFilters)));
	complexity.add(compareParameters(numberOfVariables,Integer.parseInt(minNumberOfVariables),Integer.parseInt(maxNumberOfVariables)));
	complexity.add(compareParameters(numberOfComplexCalculations,Integer.parseInt(minNumberOfComplexCalculations),Integer.parseInt(maxNumberOfComplexCalculations)));
	if(complexity.contains("C"))
	{
	res = "Complex";
	}
	else if(complexity.contains("M"))
	{
	res = "Medium";
	}
	else
	{
	res = "Simple";
	}
	return res;
	} catch (IOException e) {
	e.printStackTrace();
	}
	return "Simple";
	}


	

}
