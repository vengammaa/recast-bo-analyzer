package com.recast.recast.bo.analyzer.service;



import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.recast.recast.bo.analyzer.entity.SimilarityMatrix;
import com.recast.recast.bo.analyzer.model.SimilarityMatrixModel;
import com.recast.recast.bo.analyzer.repository.CommonalityParamsRepository;
import com.recast.recast.bo.analyzer.repository.SimilarityMatrixRepo;


//import com.sun.istack.logging.Logger;
/**
 * <h2>The CommonalityParamsServiceImpl Class is responsible for overriding the
 * methods in the service and implementing all the logic of the similarity
 * engine.</h2>
 *
 * @author Team A - Sadia, Naushaba, and Timothy
 *
 */
@Service
public class CommonalityParamsServiceImpl implements CommonalityParamsService {

//	static final Logger logger = Logger.getLogger(CommonalityParamsServiceImpl.class);

	/**
	 * <p>
	 * int Step1CR integer assigned to a pair of report ids after passing step 1 of
	 * the similarity engine. Initialized in application.properties.
	 * </p>
	 */
	@Value("${Step1CR}")
	int Step1CR;
	/**
	 * <p>
	 * int Step2CR integer assigned to a pair of report ids after passing step 2 of
	 * the similarity engine. Initialized in application.properties.
	 * </p>
	 */
	@Value("${Step2CR}")
	int Step2CR;
	/**
	 * <p>
	 * int Step3CR integer assigned to a pair of report ids after passing step 3 of
	 * the similarity engine. Initialized in application.properties.
	 * </p>
	 */
	@Value("${Step3CR}")
	int Step3CR;
	/**
	 * <p>
	 * int Step4CR integer assigned to a pair of report ids after passing step 4 of
	 * the similarity engine. Initialized in application.properties.
	 * </p>
	 */
	@Value("${Step4CR}")
	int Step4CR;
	/**
	 * <p>
	 * int Step5CR integer assigned to a pair of report ids after passing step 5 of
	 * the similarity engine. Initialized in application.properties.
	 * </p>
	 */
	@Value("${Step5CR}")
	int Step5CR;
	/**
	 * <p>
	 * int Step6CR integer assigned to a pair of report ids after passing step 6 of
	 * the similarity engine. Initialized in application.properties.
	 * </p>
	 */
	@Value("${Step6CR}")
	int Step6CR;
	/**
	 * <p>
	 * int Step7CR integer assigned to a pair of report ids after passing step 7 of
	 * the similarity engine. Initialized in application.properties.
	 * </p>
	 */
	@Value("${Step7CR}")
	int Step7CR;

	/**
	 * <p>
	 * autowired commonalityParamsRepo which allows us to read and update the the
	 * database
	 * </p>
	 */
	@Autowired
	private CommonalityParamsRepository commonalityRepo;

	/**
	 * <p>
	 * autowired similarityMatrixRepo which allows us to save the similarity engine
	 * output into the database
	 * </p>
	 */
	@Autowired
	private SimilarityMatrixRepo similarityMatrixRepo;

	

	/**
	 * <p>
	 * Constructor for the CommonalityParamsServiceImpl
	 * </p>
	 * 
	 */
	public CommonalityParamsServiceImpl() {
		super();
	}



	/**
	 * <p>
	 * This method calls all the steps of the similarity engine on those report with
	 * the given taskId, and then saves the output in the similarity matrix table
	 * </p>
	 * 
	 * @param taskId the foreign key in the commonality params table
	 * @return step6resultlist that contains all the data that was saved into the
	 *         similarity matrix table after running the similarity engine.
	 */
	@Override
	public List<List<String>> similarityEngineProcess(int taskId) {
		// retrieve all the report_ids,task_ids, report_names, data_source_names,
		// table_names for a specified taskId
		List<List<String>> commonalityParamslist = commonalityRepo.getQuery(taskId);
		// Now use the commonalityParamslist to create a hashmap with keys as report ids
		// and values as list of their respective tables.
		Map<Integer, List<String>> hashmaplist = getHashMapList(commonalityParamslist);
		

		// Retrieve the primary key within the similarity matrix given the two report
		// ids we are comparing --> not used in similarity engine
//		String id = getSimilarityMatrixIdByGivenReportId1AndReportId2(6080, 8422);
//		System.out.print(id);

		// Now with the taskId commonalityParamslist, and hashmaplist in hand, we can
		// call step 1 and call all the following steps with the previous step's result.
		List<List<String>> step1resultList = doStep1Process(taskId, hashmaplist); // doStep1Process(taskId,
																					// commonalityParamslist,
																					// hashmaplist);
	//System.out.println("step1resultList: " + step1resultList);
		List<List<String>> step2resultlist = doStep2Process(step1resultList, hashmaplist);
	//	System.out.println("step2resultList: " + step2resultlist);
		List<List<String>> step3resultlist = doStep3Process(step2resultlist, hashmaplist);
		//System.out.println("step3resultList: " + step3resultlist);
		List<List<String>> step4resultlist = doStep4Process(step3resultlist);
		//System.out.println("step4resultList: " + step4resultlist);
		List<List<String>> step5resultlist = doStep5Process(step4resultlist);
		//System.out.println("step5resultList: " + step5resultlist);
		List<List<String>> step6resultlist = doStep6Process(step5resultlist);
		//System.out.println("step6resultList: " + step6resultlist);

		// After running similarity engine, let's save the data into the similarity
		// matrix table.
		saveIntoSimilarityMatrix(step6resultlist);

		// Now after saving, let's return the step6resultlist
//		return commonalityParamslist;
		return step6resultlist;
	}

	/**
	 * <p>
	 * This method takes in part of commonality params table as a list of list of
	 * strings and creates a HashMap with this. The key is the reportId and the
	 * value is the list of tables names for that reportId.
	 * </p>
	 * 
	 * @param commonalityParamsList commonality params table info for which we
	 *                              re-organize info into HashMap
	 * @return HashMap where the key is report id and the value is the list of table
	 *         names
	 */
	public Map<Integer, List<String>> getHashMapList(List<List<String>> commonalityParamsList) {
		// Initialize a Hash Map
		// Can use tree set as well - will always have order
		Map<Integer, List<String>> hashmaplist = new HashMap<>();

		// Iterate through the commonalityParamsList
		for (int i = 0; i < commonalityParamsList.size(); i++) {

			// Retrieve the reportId
			int reportId = Integer.parseInt(commonalityParamsList.get(i).get(0));

			// check if we have added this reportId's information to the HashMap
			if (hashmaplist.containsKey(reportId)) {
				// If this reportId is inside the HashMap, let us get the list of table names
				List<String> list1 = hashmaplist.get(reportId);
				// Retrieve the table name in the current list of strings we are considering and
				// add to list1
				list1.add(commonalityParamsList.get(i).get(4));
				// Sort the list of table names before setting it as the value of reportId in
				// the HashMap
				Collections.sort(list1); // [t1,t2] vs [t2, t1]
				hashmaplist.put(reportId, list1);
			}
			// If reportId is not inside the HashMap
			else {
				// Then we need to initialize the list of tables.
				List<String> list1 = new ArrayList<String>();
				// Now we can retrieve the table name in the current list of strings we are
				// considering and add to list1
				list1.add(commonalityParamsList.get(i).get(4));
				// Sort the list of table names before setting it as the value of reportId in
				// the HashMap
				Collections.sort(list1);
				hashmaplist.put(reportId, list1);
			}

		}
		// Return the HashMap
		return hashmaplist;
	}

	/**
	 * <p>
	 * This method retrieves all the column names that have column type select for a
	 * given reportId and tableName. Used in step 2 of the similarity engine.
	 * </p>
	 * 
	 * @param reportId  a report that has the given number associated to it
	 * @param tableName each report can have any number of tables. This is just one
	 *                  table name for the given reportId
	 * @return List of Strings that has column names as entries
	 */
	@Override
	public List<String> getStep2QueryByReportIdTableName(int reportId, String tableName) {
		// Call the query for step 2 that gets all the column names that have column
		// type = select for a given reportId and tableName
		List<String> step2queryspecific = commonalityRepo.getStep2QueryByReportIdTableName(reportId, tableName);
		// Return this query as a list of Strings
		return step2queryspecific;
	}

	/**
	 * <p>
	 * This method retrieves all the column names that do not have column type
	 * select for a given reportId and tableName. We can have column types such as
	 * "where" and "groupby" that we consider here. Used in step 3 of the similarity
	 * engine.
	 * </p>
	 * 
	 * @param reportId  a report that has the given number associated to it
	 * @param tableName each report can have any number of tables. This is just one
	 *                  table name for the given reportId
	 * @return List of Strings that has column names as entries
	 */
	@Override
	public List<String> getStep3QueryColumnNamesWithoutSelectColumnType(int reportId, String tableName) {
		// Call the query for step 3 that gets all the column names that have column
		// type != select for a given reportId and tableName
		List<String> step3querycolumnnameswithoutselect = commonalityRepo
				.getStep3QueryColumnNamesWhichDoesNotHaveSelectColumnType(reportId, tableName);
		// Return this query as a list of Strings
		return step3querycolumnnameswithoutselect;
	}

	/**
	 * <p>
	 * This method retrieves all the column types for a given reportId, tableName,
	 * and columnName. We can have column types such as "where" and "groupby" that
	 * we consider here. Used in step 3 of the similarity engine.
	 * </p>
	 * 
	 * @param reportId   a report that has the given number associated to it
	 * @param tableName  each report can have any number of tables. This is just one
	 *                   of table name for the given reportId
	 * @param columnName one of the column names for the given reportId and
	 *                   tableName
	 * @return List of Strings that has column types as entries, ex: [Groupby,
	 *         Where]
	 */
	@Override
	public List<String> getStep3QueryColumnTypesWithoutSelectColumnType(int reportId, String tableName,
			String columnName) {
		// Call the query for step 3 that gets all the column types for a specific
		// reportId, tableName, and columnName
		List<String> step3columntypesquerywithoutselect = commonalityRepo
				.getStep3QueryColumnTypesWhichDoesNotHaveSelectColumnType(reportId, tableName, columnName);
			// Return this query as a list of Strings
		return step3columntypesquerywithoutselect;
	}


	/**
	 * <p>
	 * This method retrieves all the unique entries in the measureList column of the
	 * commonality params table for a given reportId, and returns a list of these
	 * items. If all the rows corresponding to the reportId have measureList as
	 * null, it will return null. The benefit of using an Optional is that we can
	 * either return a list or a null. Used in step 4 of the similarity engine.
	 * </p>
	 * 
	 * @param reportId a report that has the given number associated to it
	 * @return Optional List of Strings that has the contents of the measureList
	 *         column in the commonality params table. It will return null, if all
	 *         entries of measureList are null.
	 */
	public Optional<List<String>> getStep4QueryMeasureListByGivenReportId(int reportId) {
		// Call the query for step 4 that gets the measure list for a specific reportId
		Optional<List<String>> step4MeasureListforSpecificreportid = Optional
				.of(commonalityRepo.getStep4QueryForMeasureList(reportId));
		// Return this query as a list of Strings
		return step4MeasureListforSpecificreportid;
	}

	/**
	 * <p>
	 * This method retrieves all the unique entries in the variableList column of
	 * the commonality params table for a given reportId, and returns a list of
	 * these items. If all the rows corresponding to the reportId have variableList
	 * as null, it will return null. The benefit of using an Optional is that we can
	 * either return a list or a null. Used in step 4 of the similarity engine.
	 * </p>
	 * 
	 * @param reportId a report that has the given number associated to it
	 * @return Optional List of Strings that has the contents of the variableList
	 *         column in the commonality params table. It will return null, if all
	 *         entries of variableList are null.
	 */
	public Optional<List<String>> getStep4QueryVariableListByGivenReportId(int reportId) {
		// Call the query for step 4 that gets the variable list for a specific reportId
		Optional<List<String>> step4VariableListforSpecificreportid = Optional
				.of(commonalityRepo.getStep4QueryForVariableList(reportId));
		// Return this query as a list of Strings
		return step4VariableListforSpecificreportid;
	}

	/**
	 * <p>
	 * This method retrieves all the unique entries in the attributeList column of
	 * the commonality params table for a given reportId, and returns a list of
	 * these items. If all the rows corresponding to the reportId have attributeList
	 * as null, it will return null. The benefit of using an Optional is that we can
	 * either return a list or a null. Used in step 4 of the similarity engine.
	 * </p>
	 * 
	 * @param reportId a report that has the given number associated to it
	 * @return Optional List of Strings that has the contents of the attributeList
	 *         column in the commonality params table. It will return null, if all
	 *         entries of attributeList are null.
	 */
	public Optional<List<String>> getStep4QueryAttributeListByGivenReportId(int reportId) {
		// Call the query for step 4 that gets the attribute list for a specific
		// reportId
		Optional<List<String>> step4AttributeListforSpecificreportid = Optional
				.of(commonalityRepo.getStep4QueryForAttributeList(reportId));
		// Return this query as a list of Strings
		return step4AttributeListforSpecificreportid;
	}

	/**
	 * <p>
	 * This method retrieves all the visuals for a given reportId. These are found
	 * in the chart type column in the analysis reports visualization table. As an
	 * example, if one report has a bar graph and a line graph, these will be in our
	 * returned list. Used in step 5 of the similarity engine.
	 * </p>
	 * 
	 * @param reportId each report has a corresponding number associated to it
	 * @return List of Strings that has chart type/visuals as entries.
	 */
	@Override
	public List<String> getStep5QueryChartTypesWithGivenReportId(int reportId) {
		// Call the query for step 5 that gets the chart types (i.e. visuals) for a
		// specific reportId
		List<String> step5charttypesforspecificreportid = commonalityRepo.getStep5QueryChartTypesForReportId(reportId);
			// Return this query as a list of Strings
		return step5charttypesforspecificreportid;
	}

	/**
	 * <p>
	 * This method retrieves the contents of the data filters column in the analysis
	 * report table for a given reportId. Its content is returned as a String. When
	 * implementing step 6 of the similarity engine, we will convert this String to
	 * a JSONArray, easing the process of comparing two data filter entries.
	 * </p>
	 * 
	 * @param reportId a report that has the given number associated to it
	 * @return String that has contents of the data filter information.
	 */
	@Override
	public String getStep6QueryForDataFiltersByGivenReportId(int reportId) {
		// Call the query for step 6 that gets the data filters for a specific reportId
		String step6DataFilterForSpecificReportId = commonalityRepo.getStep6QueryForDataFilters(reportId);
		// Return this query as a String
		return step6DataFilterForSpecificReportId;
	}




	/**
	 * <p>
	 * This method applies step 1 of the similarity engine to all reports with the
	 * given taskId. Step 1 checks if the list of table names match for each pair of
	 * reportIds. For each pair of reports that pass step 1, we build a list of
	 * strings that holds all the information about the comparison of these report
	 * ids. Each entry in this list of strings corresponds to a column in the
	 * similarity matrix table. This is the table that holds the results of the
	 * similarity engine.
	 * </p>
	 * 
	 * <p>
	 * This list of strings is then added into a list which will be returned at the
	 * end of step 1 and will be updated in the later steps of the similarity
	 * engine.
	 * </p>
	 * 
	 * @param taskId      foreign key in the commonality params table which
	 *                    corresponds to the primary key in the prj rpt analysis
	 *                    table
	 * @param hashmaplist the HashMap that has keys as reportIds and values as the
	 *                    corresponding list of table names.
	 * @return List of List of Strings that has all pairs of report ids that passed
	 *         step 1 and their information
	 */
	public List<List<String>> doStep1Process(int taskId, Map<Integer, List<String>> hashmaplist) {
		// removed parameter List<List<String>> commonalityParamslist since we are no
		// longer building HashMap inside this method
		

		// Use the HashMap to create a list of reportIds. We will iterate over this list
		// of reportIds.
		List<Integer> reportidlist = new ArrayList<>();
		Iterator hmIterator = hashmaplist.entrySet().iterator();
		while (hmIterator.hasNext()) {
			Map.Entry mapElement = (Map.Entry) hmIterator.next();
			int key = (int) mapElement.getKey();
			reportidlist.add(key);
		}
		// Create a list of list of strings that will carry the result after step 1.
		List<List<String>> similarityAfterStep1 = new ArrayList<List<String>>();// changing in Integer type to String

		// Collect all the values in the HashMap. These are all the possible list of
		// table names the report ids can have and will be handy in creating clusters
		Collection<List<String>> values = hashmaplist.values();

		// But values has repeats, so let us remove the repeats by using Sets.
		Set<Set<String>> uniqueGroupTableSet = new HashSet<Set<String>>();
		//System.out.println("uniqueGroupTable Set : " + uniqueGroupTableSet);
		// Convert the set of set of strings to a list. This allows us to have order and
		// makes it easier to set a cluster for a given pair of report ids.
		List<List<String>> uniqueList = new ArrayList<List<String>>();

	// Use a double for loop and iterate over the list of report ids to compare all
		// possible pairs of report ids
		for (int i = 0; i < reportidlist.size(); i++) {
			for (int j = i + 1; j < reportidlist.size(); j++) {
				// The outer loop will be used for one report id and the inner loop for the
				// other report id
				int reportid1 = reportidlist.get(i);
				int reportid2 = reportidlist.get(j);
				// Use the HashMap to find the list of tables for both report ids
				List<String> tablesList1 = hashmaplist.get(reportidlist.get(i));
				List<String> tablesList2 = hashmaplist.get(reportidlist.get(j));

				// Sort tables --> modified HashMap to sort tables before adding, so no need to
				// sort again.
//				Collections.sort(tablesList1);
//				Collections.sort(tablesList2);

				// initialize commonalityPercentage to 0 and identical to false.
				int commonalityPercentage = 0;

				// Check if the list of tables for the two report ids are the same.
				if (tablesList1.equals(tablesList2)) {
					// If so, then set commonalityPercentage to Step1CR (initialized in
					// application.properties)
					commonalityPercentage = Step1CR;

					// Now for each pair of report ids that pass step 1, we need to add to their
					// information into a list of strings
					// This way it will be easier to save into the similarity matrix table later on
					List<String> entries = new ArrayList<>();

					// Convert the report ids and commonalityPercentages to strings so we can add
					// them into entries
					String reportid1str = String.valueOf(reportid1);
					String reportid2str = String.valueOf(reportid2);
					String commonalityPercentagestr = String.valueOf(commonalityPercentage);

					// Retrieve the position of tableList1 inside the uniqueList.
					// This will give the cluster number and we will use it to make an alpha-numeric
					// cluster_column
					int number = uniqueList.indexOf(tablesList1);
					String cluster_column = "cluster-" + number;

					// Now add the report ids and commonality percentage to entries,
					entries.add(reportid1str);// index =0
					entries.add(reportid2str);// index =1
					entries.add(commonalityPercentagestr);// index=2

					// as well as Y/N for each step,
					entries.add("Y");// Step 1 index =3
					entries.add("N");// Step 2 index = 4
					entries.add("N");// Step 3 index = 5
					entries.add("N");// Step 4 index =6
					entries.add("N");// Step 5 index =7
					entries.add("N");// Step 6 index =8

					// the passed step 1 description,
					entries.add("Table matched with these table Name: " + tablesList1);// Step1_description index=9

					// the cluster column and taskId,
					entries.add(cluster_column); // index = 10
					entries.add(String.valueOf(taskId));// index =11

					// and the default step descriptions for the remaining steps.
					entries.add("Cannot compare report ids due to previous failed commonality percentage test."); // step2Description
																													// index=12
					entries.add("Cannot compare report ids due to previous failed commonality percentage test."); // step3Description
																													// index=13
					entries.add("Cannot compare report ids due to previous failed commonality percentage test."); // step4Description
																													// index=14
					entries.add("Cannot compare report ids due to previous failed commonality percentage test."); // step5Description
																													// index=15
					entries.add("Cannot compare report ids due to previous failed commonality percentage test."); // step6Description
																													// index=16

					// Now add entries to the step 1 result list
					similarityAfterStep1.add(entries);

				}

			}

		}
		// Return this step 1 result list
		return similarityAfterStep1;
	}

	/**
	 * <p>
	 * This method applies step 2 of the similarity engine to all report pairs that
	 * passed step 1. Step 2 takes a pair of reportIds, and for each table in that
	 * report, it checks if the column names that have column type select are the
	 * same. For each pair of reportIds considered for step 2, this method updates
	 * the step1ResultList accordingly (e.g. updating the commonalityValue, step2
	 * and/or step2Description)
	 * </p>
	 * 
	 * @param step1ResultList that has all pairs of report ids that passed step 1
	 *                        and their information
	 * @param hashmaplist     the HashMap that has keys as reportIds and values as
	 *                        the corresponding list of table names.
	 * @return List of List of Strings that has all pairs of report ids that passed
	 *         step 1 and their updated information after being considered for step
	 *         2.
	 */
	public List<List<String>> doStep2Process(List<List<String>> step1ResultList,
			Map<Integer, List<String>> hashmaplist) {
		
		// Declare all fields
		int reportId1;
		int reportId2;
		int count;
		int commonalityValue;
		String step2Description;
		List<String> tablenamelist1;
		List<String> list = null;
		List<String> table_ColumnName_List1 = null;
		List<String> table_ColumnName_List2 = null;
		// Iterate step1ResultList and update as necessary
		for (int i = 0; i < step1ResultList.size(); i++) {

			// Retrieve information for a pair of report ids.
			list = step1ResultList.get(i);

			// Get the commonalityValue from the current report ids we are comparing
			commonalityValue = Integer.parseInt(list.get(2));

			// We need to check if the column names where column types = select match for
			// each table name.
			// To do this, we need a counter. Set the count to 0 at the start of each
			// iteration.
			count = 0;

			// step2Description will describe why or why not our pair of report ids passed
			// step 2.
			step2Description = "";

			// If the pair of reportIds we are looking at pass step 1, then proceed to step
			// 2.
			if (commonalityValue == Step1CR) {
				// Retrieve the report ids
				reportId1 = Integer.parseInt(list.get(0));
				reportId2 = Integer.parseInt(list.get(1));
				// Use the HashMap to get the list of table names.
				// These report ids passes step 1, so their list of tables are the same.
				tablenamelist1 = hashmaplist.get(reportId1);

				// For a pair of report ids, we are going to keep track of which tables passed
				// step 2. This will be used in the failed cases of step2Description
				List<String> tableNameMatchList = new ArrayList<String>();

				// Now iterate through each table to check if their column names with column
				// type = select match
				for (int j = 0; j < tablenamelist1.size(); j++) {
					// We are currently looking at the following table:
					String tablename = tablenamelist1.get(j);

					// call the query that will get the column names where column type = select for
					// both report ids
					table_ColumnName_List1 = getStep2QueryByReportIdTableName(reportId1, tablename);
					table_ColumnName_List2 = getStep2QueryByReportIdTableName(reportId2, tablename);

					// sort these lists
					Collections.sort(table_ColumnName_List1);
					Collections.sort(table_ColumnName_List2);
//					System.out.println("table name is " + tablename);
//					System.out.println("the column names for reportId1 are " + table_ColumnName_List1);
//					System.out.println("the column names for reportId2 are " + table_ColumnName_List2);

					// Now if these list of column names are same size, they may be equal
					if (table_ColumnName_List1.size() == table_ColumnName_List2.size()) {
						// check if these lists are actually equal
						if (table_ColumnName_List1.equals(table_ColumnName_List2)) {
							// if the two column names lists are equal that means the query is satisfied for
							// one of the tables, so increment the counter
							count += 1;
							// and add this table to the tableNamesMatchedList
							tableNameMatchList.add(tablename);

						}

					}

				}
				// The column names have now been compared for each of the tables.
				// If these column names matched for each table, our count should be the same as
				// the size of our list of tables.
				if (count == tablenamelist1.size()) {
					// If it is, then we need to update our commonalityValue in the list, as well as
					// set step 2 from a "N" to a "Y"
					list.set(2, String.valueOf(Step2CR));
					list.set(4, "Y");
					// Also, let us have the following as our Step2Description and set this in our
					// list as well
					step2Description = "Passed step 2 column names matched with column type equal to select for all tables.";
					list.set(12, step2Description);
				} else {
					// Step 2 did not pass, so let us write a step2Description for this case and set
					// this in our list
					// Use tableNameMatchList to explain for which tables step 2 did pass.
					step2Description = "Did not pass step 2. Only passed for the following tables  "
							+ tableNameMatchList;
					list.set(12, step2Description);
				}

			}

		}

		// Return this step 2 result list
		return step1ResultList;

	}

	/**
	 * <p>
	 * This method applies step 3 of the similarity engine to all report pairs that
	 * passed step 2. Step 3 takes a pair of reportIds, and for each table in that
	 * report, it checks if the column names that do not have column type select are
	 * the same. If the column names match, then it checks if each corresponding
	 * column name has the same column types. If this holds, this method updates the
	 * step2ResultList accordingly for that pair of reportIds (e.g. updating the
	 * commonalityValue, step3 and/or step3Description)
	 * </p>
	 * 
	 * @param step2ResultList that has all pairs of report ids that passed step 1,
	 *                        their information, as well as how they did in step 2.
	 * @param hashmaplist     the HashMap that has keys as reportIds and values as
	 *                        the corresponding list of table names.
	 * @return List of List of Strings that has all pairs of report ids that passed
	 *         step 1 and their updated information after being considered for steps
	 *         2 - 3.
	 */
	public List<List<String>> doStep3Process(List<List<String>> step2ResultList,
			Map<Integer, List<String>> hashmaplist) {
		// Declare all fields
		List<String> list;
		int commonalityValue;
		int reportid1;
		int reportid2;
		String tablename;
		List<String> table_ColumnName_List1;
		List<String> table_ColumnName_List2;
		String columnName;
		List<String> table_ColumnType_List1;
		List<String> table_ColumnType_List2;

		int columnTypeCounter;
		int tableCounter;
		String step3Description = "";
		List<String> tableNameMatchList = null;
		// iterate over step2ResultList
		for (int i = 0; i < step2ResultList.size(); i++) {
			// resetting counters before comparing reports
			columnTypeCounter = 0;
			tableCounter = 0;
			// Empty list for keeping track of table that pass step 3
			tableNameMatchList = new ArrayList<String>();
			// getting Report info from step2ResultList.
			list = step2ResultList.get(i);
			// getting commonalityValue for reports
			commonalityValue = Integer.parseInt(list.get(2));
			// if commonality is 70, it passed steps 1 and 2, so do step 3 on these two
			// reports.
			if (commonalityValue == Step2CR) {
				reportid1 = Integer.parseInt(list.get(0));
				reportid2 = Integer.parseInt(list.get(1));
				// both reports have the same list of tables (passed step 1)
				List<String> tablenamelist1 = hashmaplist.get(reportid1);
				// iterate each table in reports
				for (int j = 0; j < tablenamelist1.size(); j++) {
					tablename = tablenamelist1.get(j);
//					System.out.println("Table name: " + tablename);
					// Reset columnTypeCounter so we can keep track of how many columns pass step 3
					columnTypeCounter = 0;
					// getting list of columnName which does not have select columnType, rather than
					// select it can be group by or where clause
					table_ColumnName_List1 = getStep3QueryColumnNamesWithoutSelectColumnType(reportid1, tablename);
					table_ColumnName_List2 = getStep3QueryColumnNamesWithoutSelectColumnType(reportid2, tablename);
					// Sort the two list of column names and see if they match
					Collections.sort(table_ColumnName_List1);
					Collections.sort(table_ColumnName_List2);
					// If both are null, the two column names and their corresponding column types
					// automatically match, as null = null
					if (table_ColumnName_List1 == null && table_ColumnName_List1 == null) {
						step3Description = "Passed step 3. List of column names are both null, so column types are also null.";
								}
					// Otherwise, we either both are not null and we can compare as usual or one of
					// the two are null and will be caught in the catch block.
					else {
						try {
							if (table_ColumnName_List1.size() == table_ColumnName_List2.size()) {
								if (table_ColumnName_List1.equals(table_ColumnName_List2)) {
									// iterate through each column name for a given table.
									for (int k = 0; k < table_ColumnName_List1.size(); k++) {
										columnName = table_ColumnName_List1.get(k);
//										// for a given column, find the column types corresponding to the given report
										// id and table name
										table_ColumnType_List1 = getStep3QueryColumnTypesWithoutSelectColumnType(
												reportid1, tablename, columnName);
										table_ColumnType_List2 = getStep3QueryColumnTypesWithoutSelectColumnType(
												reportid2, tablename, columnName);
										// sort the columnTypes
										Collections.sort(table_ColumnType_List1);
										Collections.sort(table_ColumnType_List2);
					
										// if the list of column types match for a specific column name increment
										// columnTypeCounter
										if (table_ColumnType_List1.size() == table_ColumnType_List2.size()) {
											if (table_ColumnType_List1.equals(table_ColumnType_List2)) {
												columnTypeCounter += 1;

											}
											//System.out.println(columnTypeCounter);
										}
									}
									// if all column types list match for all column names for a specific table,
									// increment tableCounter
									if (columnTypeCounter == table_ColumnName_List1.size()) {
										tableCounter += 1;
										// this table passed step 3 so we can show in step 3 descriptions column in
										// similarity engine
										tableNameMatchList.add(tablename);

									}
									
								}

							}
						} catch (NullPointerException ex) {
							step3Description = "Did not pass step 3 due to a caught NullPointerException.";
							ex.printStackTrace();
						}
					}

				}
				// if all column types list match for all column names for all tables, update
				// similarity engine commonality value
				if (tableCounter == tablenamelist1.size()) {
					list.set(2, String.valueOf(Step3CR));
					list.set(5, "Y");
					// step3 Description for passed case
					step3Description = "Passed step 3. All column types list match for all column names for all tables.";
				}
				// if step3Description is empty, it has not been set yet -> step 3 failed
				else if (step3Description.isEmpty()) {
					// step3 Description for failed case when no nullPointerException was caught in
					// the process.
					step3Description = "Did not pass step 3. Only passed for the following tables: "
							+ tableNameMatchList;
				}
				// update step3Description in list
				list.set(13, step3Description);
			}

		}
		// Return step 3 result list.
		return step2ResultList;
	}

	/**
	 * <p>
	 * This method applies step 4 of the similarity engine to all report pairs that
	 * passed step 3. Step 4 takes a pair of reportIds, and compares the columns
	 * dimensionList, measureList, variableList, and attributeList.
	 * </p>
	 * <p>
	 * For each column that matches, we increment the commonalityValue by 2. If all
	 * 4 columns match, we will have a commonalityValue of 88 as Step3CR is 80, and
	 * in this case, we will update the step3resultlist to have a commonality value
	 * of Step4CR for that pair of reportIds. Note: Step4CR is 90. After comparing
	 * all 4 columns, if at least one of the columns match (but not all) for a pair
	 * of reportIds, then we will set the commonalityValue to what it is in the
	 * step3resultlist corresponding to that pair (e.g. 82,84,86).
	 * </p>
	 * <p>
	 * For each pair of reportIds considered for step 4, we also update the step 4
	 * and step4Description columns in the step3resultlist. If all 4 columns match
	 * for a given pair of reports, then we say the pair passed step 4 100% and set
	 * column step4 to "Y". Otherwise, it will be kept as "N".
	 * </p>
	 * 
	 * @param step3resultlist that has all pairs of report ids that passed step 1
	 *                        and their updated information after being considered
	 *                        for steps 2 - 3.
	 * @return List of List of Strings that has all pairs of report ids that passed
	 *         step 1 and their updated information after being considered for steps
	 *         2 - 4.
	 */
	public List<List<String>> doStep4Process(List<List<String>> step3resultlist) {
	
		List<String> list;
		int commonalityValue;
		int reportid1;
		int reportid2;
		Optional<List<String>> dimensionList1;
		Optional<List<String>> dimensionList2;
		Optional<List<String>> measureList1;
		Optional<List<String>> measureList2;
		Optional<List<String>> variableList1;
		Optional<List<String>> variableList2;
		Optional<List<String>> attributeList1;
		Optional<List<String>> attributeList2;
		String step4Description;
		// create matchedItems list to keep track of which of the following items passed
		// for step 4: dimension, measure, variable, and attribute
		List<String> matchedItems = null;
		// iterate over step3resultlist
		for (int i = 0; i < step3resultlist.size(); i++) {
			list = step3resultlist.get(i);
			// Empty list for keeping track of items that pass step 4
			matchedItems = new ArrayList<String>();
			// getting commonalityValue for reports
			commonalityValue = Integer.parseInt(list.get(2));
			// if reports passed step 3 then go for step 4
			if (commonalityValue == Step3CR) {
				reportid1 = Integer.parseInt(list.get(0));
				reportid2 = Integer.parseInt(list.get(1));
			
				// get the list of dimensions, measures, variables, and attributes
				dimensionList1 = getStep4QueryDimensionListByGivenReportId(reportid1);
				dimensionList2 = getStep4QueryDimensionListByGivenReportId(reportid2);
				measureList1 = getStep4QueryMeasureListByGivenReportId(reportid1);
				measureList2 = getStep4QueryMeasureListByGivenReportId(reportid2);
				variableList1 = getStep4QueryVariableListByGivenReportId(reportid1);
				variableList2 = getStep4QueryVariableListByGivenReportId(reportid2);
				attributeList1 = getStep4QueryAttributeListByGivenReportId(reportid1);
				attributeList2 = getStep4QueryAttributeListByGivenReportId(reportid2);

				// checking dimensionLists when both lists are not null
				if (dimensionList1.isPresent() && dimensionList2.isPresent()) {
					/*
					 * if report id has multiple values and one of them is null in dimension list
					 * then Collections does not know how to sort null values, so we will get a
					 * NullPointerException
					 */
					// Collections.sort(dimensionList1.get());
					// Collections.sort(dimensionList2.get());
					if (dimensionList1.get().size() == dimensionList2.get().size()) {
						if (dimensionList1.get().equals(dimensionList2.get())) {
							// if reports have same dimension list then add 2 to commonalityValue
							commonalityValue = commonalityValue + 2;
							// add dimensionList in to matchedItems for step 4 description
							matchedItems.add("dimensionList");
							/*
							 * System.out.
							 * println("Checking dimensionList: inside equality for isPresent step 4 ");
							 * System.out.println("dimensionList1: " + dimensionList1);
							 * System.out.println("dimensionList2: " + dimensionList2);
							 * System.out.println("commonalityValue: " + commonalityValue);
							 */

						}
					}
				}
				// if both dimensionLists are null, then increment commonalityValue by 2
				else if (!dimensionList1.isPresent() && !dimensionList2.isPresent()) {
					commonalityValue = commonalityValue + 2;
					// add dimensionList in to matchedItems for step 4 description
					matchedItems.add("dimensionList");
					/*
					 * System.out.
					 * println("Checking dimensionList: inside equality for Not isPresent step 4.");
					 * System.out.println("dimensionList1: " + dimensionList1);
					 * System.out.println("dimensionList2: " + dimensionList2);
					 * System.out.println("commonalityValue: " + commonalityValue);
					 */
				}

				// checking measureLists when both lists are not null
				if (measureList1.isPresent() && measureList2.isPresent()) {
					/*
					 * if report id has multiple values and one of them is null in measure list then
					 * Collections does not know how to sort null values, so we will get a
					 * NullPointerException
					 */
					// Collections.sort(measureList1.get());
					// Collections.sort(measureList2.get());
					if (measureList1.get().size() == measureList2.get().size()) {
						if (measureList1.get().equals(measureList2.get())) {
							// if reports have same measure list then add 2 to commonalityValue
							commonalityValue = commonalityValue + 2;
							// add measureList in to matchedItems for step 4 description
							matchedItems.add("measureList");
							/*
							 * System.out.
							 * println("Checking measure List: inside equality for isPresent step 4");
							 * System.out.println("measureList1: " + measureList1);
							 * System.out.println("measureList2: " + measureList2);
							 * System.out.println("commonalityValue: " + commonalityValue);
							 */

						}
					}

				}
				// if both measureLists are null, then increment commonalityValue by 2
				else if (!measureList1.isPresent() && !measureList2.isPresent()) {
					commonalityValue = commonalityValue + 2;
					// add measureList in to matchedItems for step 4 description
					matchedItems.add("measureList");
					/*
					 * System.out.
					 * println("Checking measureList: inside equality for Not isPresent step 4");
					 * System.out.println("measureList1: " + measureList1);
					 * System.out.println("measureList2: " + measureList2);
					 * System.out.println("commonalityValue: " + commonalityValue);
					 */
				}

				// checking variableLists when both lists are not null
				if (variableList1.isPresent() && variableList2.isPresent()) {
					/*
					 * if report id has multiple values and one of them is null in variable list
					 * then Collections does not know how to sort null values, so we will get a
					 * NullPointerException
					 */
					// Collections.sort(variableList1.get());
					// Collections.sort(variableList2.get());
					if (variableList1.get().size() == variableList2.get().size()) {
						if (variableList1.get().equals(variableList2.get())) {
							// if reports have same variable list then add 2 to commonalityValue
							commonalityValue = commonalityValue + 2;
							// add variableList in to matchedItems for step 4 description
							matchedItems.add("variableList");
							/*
							 * System.out.
							 * println("Checking variableList: inside equality for isPresent step 4");
							 * System.out.println("variableList1: " + variableList1);
							 * System.out.println("variableList2: " + variableList2);
							 * System.out.println("commonalityValue: " + commonalityValue);
							 */

						}
					}

				}
				// if both variableLists are null, then increment commonalityValue by 2
				else if (!variableList1.isPresent() && !variableList2.isPresent()) {
					commonalityValue = commonalityValue + 2;
					// add variableList in to matchedItems for step 4 description
					matchedItems.add("variableList");
					/*
					 * System.out.
					 * println("Checking variableList: inside equality for Not isPresent step 4");
					 * System.out.println("variableList1: " + variableList1);
					 * System.out.println("variableList2: " + variableList2);
					 * System.out.println("commonalityValue: " + commonalityValue);
					 */
				}

				// checking attributeLists when both lists are not null
				if (attributeList1.isPresent() && attributeList2.isPresent()) {
					/*
					 * if report id has multiple values and one of them is null in attribute list
					 * then Collections does not know how to sort null values, so we will get a
					 * NullPointerException
					 */
					// Collections.sort(attributeList1.get());
					// Collections.sort(attributeList2.get());
					if (attributeList1.get().size() == attributeList2.get().size()) {
						if (attributeList1.get().equals(attributeList2.get())) {
							// if reports have same attribute list then add 2 to commonalityValue
							commonalityValue = commonalityValue + 2;
							// add attributeList in to matchedItems for step 4 description
							matchedItems.add("attributeList");
						}
					}

				}
				// if both attributeLists are null, then increment commonalityValue by 2
				else if (!attributeList1.isPresent() && !attributeList2.isPresent()) {
					commonalityValue = commonalityValue + 2;
					// add attributeList in to matchedItems for step 4 description
					matchedItems.add("attributeList");
					/*
					 * System.out.
					 * println("Checking attributeList: inside equality for Not isPresent step 4");
					 * System.out.println("attributeList1: " + attributeList1);
					 * System.out.println("attributeList2: " + attributeList2);
					 * System.out.println("commonalityValue: " + commonalityValue);
					 */
				}
				// when reports pass all 4 items, our commonality value is 88, and we update
				// commonalityValue to Step4CR which is 90
				if (commonalityValue == 88) {
					list.set(2, String.valueOf(Step4CR));
					list.set(6, "Y");
					// step4 Description for passed case
					step4Description = "Passed Step 4. Dimension list, measure list, variable list, and attribute list all match.";
				}
				// when reports pass at least one item,
				else if (commonalityValue > 80) {
					// set as commonality values as it is
					list.set(2, String.valueOf(commonalityValue));
					// step4 Description for when some of the items match in the reports. It will
					// show matchedItems.
					step4Description = "Passed Step 4 Partially. Only the following items matched: " + matchedItems;
				} else {
					// step4 Description for failed case
					step4Description = "Did not pass step 4.";
				}
				// update step4Description in list
				list.set(14, step4Description);

			}

		}

		
		// Return step 4 result list.
		return step3resultlist;
	}

	/**
	 * <p>
	 * This method applies step 5 of the similarity engine to all report pairs that
	 * passed step 4 100%. This is, only those pairs that had their dimensionList,
	 * measureList, variableList, and attributeList columns in the commonality
	 * params table match, will be considered for step 5.
	 * </p>
	 *
	 * <p>
	 * Step 5 takes a pair of reportIds, and compares if the two reportIds have the
	 * same visuals. The visual information is found in the chart type column in the
	 * analysis reports visualization table.
	 * </p>
	 * 
	 * <p>
	 * After being considered for step 5, we update the columns corresponding to
	 * that pair of reportIds in the step4resultlist, e.g. step5, step5Description,
	 * commonalityValue.
	 * </p>
	 * 
	 * @param step4resultlist that has all pairs of report ids that passed step 1
	 *                        and their updated information after being considered
	 *                        for steps 2 - 4.
	 * @return List of List of Strings that has all pairs of report ids that passed
	 *         step 1 and their updated information after being considered for step
	 *         2 - 5.
	 */
	public List<List<String>> doStep5Process(List<List<String>> step4resultlist) {
		//System.out.println("inside step 5");
		// Declare fields
		List<String> list;
		int commonalityValue;
		int reportId1;
		int reportId2;
		List<String> chartTypesList1;
		List<String> chartTypesList2;
		String step5Description = "";
		// iterate through step4resultlist
		for (int i = 0; i < step4resultlist.size(); i++) {
			list = step4resultlist.get(i);
			// getting commonalityValue for a pair of reports
			commonalityValue = Integer.parseInt(list.get(2));
			// Only apply step 5 logic to pairs that match 100 % at step 4.
			if (commonalityValue == Step4CR) {
				// if passed step 4, record report ids
				reportId1 = Integer.parseInt(list.get(0));
				reportId2 = Integer.parseInt(list.get(1));
				// call query 5 which will compare visual information of the two reports
				chartTypesList1 = getStep5QueryChartTypesWithGivenReportId(reportId1);
				chartTypesList2 = getStep5QueryChartTypesWithGivenReportId(reportId2);
				// sort these lists of chartTypes
				Collections.sort(chartTypesList1);
				Collections.sort(chartTypesList2);
				//System.out.println("chartTypesList1: " + chartTypesList1);
				//System.out.println("chartTypesList2: " + chartTypesList2);
				// if both chart types are null, they are the same and pass step 5.
				if (chartTypesList1 == null && chartTypesList2 == null) {
					step5Description = "Passed Step 5. Chart types matched and both are null.";
					//System.out.println("comparing chart types: both are null");
				}
				// Now let us compare these using a try catch, so we can catch a
				// NullPointerException if necessary
				else {
					try {
						// If the visuals are the same for both reports, update similarityValue to
						// Step5CR
						if (chartTypesList1.size() == chartTypesList2.size()) {
							if (chartTypesList1.equals(chartTypesList2)) {
								list.set(2, String.valueOf(Step5CR));
								list.set(7, "Y");
								// step 5 Description for passed case
								step5Description = "Passed Step 5. Chart types matched.";
							}
							// chart types same size but not equal
							else {
								// step 5 Description for failed case
								step5Description = "Did not pass step 5. Chart types are the same size but are not equal";
							}
						}
						// chart types are of different sizes
						else {
							// step 5 Description for failed case
							step5Description = "Did not pass step 5. Chart types are of different sizes.";
						}

					} catch (NullPointerException e) {
						// if not both chart types are null and one of them is null, we will get an
						// exception. In this case, we did not pass step 5.
						step5Description = "Did not pass step 5. A NullPointerException was catched.";
						e.printStackTrace();
					}
				}

				// update step5Description in list
				list.set(15, step5Description);
			}
		}

		
		//System.out.println(step4resultlist);

		// Return step 5 result list.
		return step4resultlist;

	}

	/**
	 * <p>
	 * This method applies step 6 of the similarity engine to all report pairs that
	 * passed step 5. Step 5 takes a pair of reportIds, and compares the data
	 * filters and query filters columns in the analysis report table. For each
	 * filter (data and query) it increments the commonalityValue by 1.
	 * </p>
	 * 
	 * <p>
	 * After being considered for step 6, we update the columns corresponding to
	 * that pair of reportIds in the step5resultlist, e.g. step6, step6Description,
	 * commonalityValue.
	 * </p>
	 * 
	 * <p>
	 * As of right now, the data filters comparison is complete. However, the query
	 * filter implementation is not complete as there is not enough information on
	 * it yet.
	 * </p>
	 * 
	 * @param step5resultlist that has all pairs of report ids that passed step 1
	 *                        and their updated information after being considered
	 *                        for step 2 - 5.
	 * @return List of List of Strings that has all pairs of report ids that passed
	 *         step 1 and their updated information after being considered for step
	 *         2 - 6.
	 */
	public List<List<String>> doStep6Process(List<List<String>> step5resultlist) {
	
		// Declare fields
		List<String> list;
		int commonalityValue;
		int reportId1;
		int reportId2;
		String dataFilters1;
		String dataFilters2;
		String queryFilters1;
		String queryFilters2;
		List<String> dataFilterReportId1;
		List<String> dataFilterReportId2;
		int counter;
		String step6Description = "";
		JSONArray jsonObjectDataFilter1 = null;
		JSONArray jsonObjectDataFilter2 = null;
		Object objDataFilters1;
		Object objDataFilters2;
		List<String> extractedList1;
		List<String> extractedList2;
		int jsonObjectDataFilter1Size = 0;

		// iterate through step5resultlist
		for (int i = 0; i < step5resultlist.size(); i++) {
			// resetting counters before comparing reports
			counter = 0;
			list = step5resultlist.get(i);
			// getting commonalityValue for a pair of reports
			commonalityValue = Integer.parseInt(list.get(2));
			// if a pair of report ids passes step 5, apply step 6.
			if (commonalityValue == Step5CR) {
				// retrieve the report ids we are comparing
				reportId1 = Integer.parseInt(list.get(0));
				reportId2 = Integer.parseInt(list.get(1));

				// retrieve the data filters and the query filters for both the report ids
				dataFilters1 = getStep6QueryForDataFiltersByGivenReportId(reportId1);
				dataFilters2 = getStep6QueryForDataFiltersByGivenReportId(reportId2);
				// QueryFilters1 = getStep6QueryForQueryFiltersByGivenReportId(reportId1);
				// QueryFilters2 = getStep6QueryForQueryFiltersByGivenReportId(reportId2);

				// if both data filters are null, then they are actually equal
				if (dataFilters1 == null && dataFilters2 == null) {
					step6Description = "Passed Step 6. Data filters are both null.";
					commonalityValue += 1;
//					list.set(2, String.valueOf(commonalityValue));
//					list.set(8, "Y");
				}
				// otherwise, they are both not null and we can compare them or one of the two
				// is null and we enter the catch block
				else {
					try {
						// For datFilters1, convert the given String to an object
						objDataFilters1 = JSONValue.parse(dataFilters1);
						// For datFilters1, create a jsonArray from this object
						jsonObjectDataFilter1 = (JSONArray) objDataFilters1;
						//System.out.println("jsonObjectDataFilter1: " + jsonObjectDataFilter1);

						// For datFilters2, convert the given String to an object
						objDataFilters2 = JSONValue.parse(dataFilters2);
						// For datFilters2, create a jsonArray from this object
						jsonObjectDataFilter2 = (JSONArray) objDataFilters2;

						// Now if the two JSONArrays are the same size, they may be equal
						if (jsonObjectDataFilter1.size() == jsonObjectDataFilter2.size()) {
							jsonObjectDataFilter1Size = jsonObjectDataFilter1.size();
							// iterate through this jsonArray
							for (int j = 0; j < jsonObjectDataFilter1.size(); j++) {
								//System.out.println(jsonObjectDataFilter1.get(j).toString());
								// for each given element inside the jsonArray, extract the conditionKey,
								// conditionValue, and operator
								extractedList1 = jsonConverter(jsonObjectDataFilter1.get(j).toString());
								extractedList2 = jsonConverter(jsonObjectDataFilter2.get(j).toString());
								// check if at index j of the JSONArray, the two data filters match
								if (extractedList1.equals(extractedList2)) {
									// if so, increment counter
									counter += 1;
								}
							}
							// after looping through jsonArray, if the counter is equal to the size of the
							// jSONArray,
							// then we have the same extractedList at each index, so increment
							// commonalityValue
							if (counter == jsonObjectDataFilter1.size()) {
								commonalityValue += 1;
							}
						}
					} catch (NullPointerException ex) {
						step6Description = "Did not pass step 6. A NullPointerException was catched.";
						ex.printStackTrace();
						
					}
					// If the pair of reports have matching data filters -> never went inside catch.
					if (commonalityValue == 96) {
						// update similarityValue to 96
//						list.set(2, String.valueOf(commonalityValue));
//						list.set(8, "Y");
						// step 5 Description for passed case
						step6Description = "Passed step 6. Data Filters matched.";
					}
				}

				// If the pair of reports have matching data filters -> never went inside catch.
				if (commonalityValue == 96) {
					// update similarityValue to 96
					list.set(2, String.valueOf(commonalityValue));
					list.set(8, "Y");
					// step 5 Description for passed case
//					step6Description = "Passed step 6. Data Filters matched.";
				}
				// Now if both data filters are not null or if they did not pass step 6, we need
				// to set the failed cases for step 6.
//				else 
				if (step6Description.isEmpty()) {
					// If the data filters size is different or none of the entries within the
					// JSONArray are the same, we have a 0% match and counter is still 0
					if (counter == 0) {
						// step 6 Description for failed case
						step6Description = "Did not pass step 6 for any of the Data Filters.";
					}
					// If the size of the data filters is the same, but only some of the entries in
					// the JSONArray matched
					else {
						// step 6 Description explaining how many of the data filters matched
						step6Description = "Did not pass step 6. Only " + counter + " out of "
								+ jsonObjectDataFilter1Size + " Data Filters matched.";
					}
				}

				// update step6Description in list
				list.set(16, step6Description);
			}

		}
		// Return step 6 result list.
		return step5resultlist;
	}

	/**
	 * <p>
	 * For step 6, we retrieve the data filters from the analysis report table as a
	 * string. This string is then converted into a JSONArray, and each element
	 * inside of it is considered a String.
	 * </p>
	 * 
	 * <p>
	 * This method takes in a String from the JSONArray and converts it to a
	 * JSONObject. It then extracts out all the items needed to be compared for data
	 * filters, namely the values of the conditionKey, conditionValue, and operator
	 * in the JSONObject. It returns these as a list of Strings.
	 * </p>
	 * 
	 * @param currentStringFilter that is a String within the JSONArray
	 *                            corresponding to a data filter
	 * @return List of String that has the values of conditionKey, conditionValue,
	 *         and operator to be compared for a given data filter entry
	 */
	public List<String> jsonConverter(String currentStringFilter) {
		// create a list of the items which will hold what you want to compare.
		List<String> extractedList = new ArrayList<>();
		// convert the given String to an object
		try {
			Object obj = JSONValue.parse(currentStringFilter);
			// creating an object of JSONObject class and casting the object into JSONObject
			// type
			
			JSONObject jsonObject = (JSONObject) obj;
			// Then extract out the necessary information to be compared and add to the
			// extracted list
			// For Data Filters, this means condition Key, ConditionValue, and operator
			extractedList.add(jsonObject.get("conditionKey").toString());
			extractedList.add(jsonObject.get("conditionValue").toString());
			extractedList.add(jsonObject.get("operator").toString());
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		// return this extracted list
		return extractedList;
	}

	/**
	 * <p>
	 * This method takes in the resultList that has all pair of reportIds that
	 * passed step 1 and have been considered for steps 2 - 6. For each list of
	 * strings in this list, we create a unique primary key, i.e. id, and take note
	 * of the reportIds we are comparing, their taskId, commonalityValue, and
	 * cluster column as well as the entries corresponding to the step and step
	 * description columns in the similarity matrix. With this information, we
	 * create a SimilarityMatrixModel and add it to a list of Models.
	 * </p>
	 * 
	 * <p>
	 * Once we have iterated the resultList and converted each entry into a model,
	 * we convert it to a list of entities which can then be saved into the
	 * similarity matrix table in the database. After this information is saved to
	 * the database, the method returns the list of models.
	 * </p>
	 * 
	 * @param resultList that has all pairs of report ids that passed step 1 and
	 *                   their updated information after being considered for step 2
	 *                   - 6.
	 * @return List of SimilarityMatrixModel that contains each item in the
	 *         resultList in the form of a model.
	 */
	public List<SimilarityMatrixModel> saveIntoSimilarityMatrix(List<List<String>> resultList) {
		// Declare fields
		// Create a list of SimilarityMatrixModels so we can take each item in the
		// resultList, convert it to a Model, and add to this list of Models
		List<SimilarityMatrixModel> similarityMatrixList = new ArrayList<SimilarityMatrixModel>();
		List<String> currentList;
		SimilarityMatrixModel currentModel;
		String id;
		int taskId;
		int reportId1;
		int reportId2;
		int commonalityPercentage;
		String step1;
		String step2;
		String step3;
		String step4;
		String step5;
		String step6;
		String clusterColumn;
		String step1Description;
		String step2Description;
		String step3Description = "";
		String step4Description = "";
		String step5Description = "";
		String step6Description = "";
		// iterate over resultList
		for (int i = 0; i < resultList.size(); i++) {

			// get current report id pair information
			currentList = resultList.get(i);

			// Create an unique id for the pair of reportIds. Concatenate the two reportIds
			// with a "_" between them
			id = currentList.get(0) + "_" + currentList.get(1);

			// Use currentList to record report ids, their commonalityPercentage, and Y/N
			// for each step
			reportId1 = Integer.parseInt(currentList.get(0));
			reportId2 = Integer.parseInt(currentList.get(1));
			commonalityPercentage = Integer.parseInt(currentList.get(2));
			step1 = currentList.get(3);
			step2 = currentList.get(4);
			step3 = currentList.get(5);
			step4 = currentList.get(6);
			step5 = currentList.get(7);
			step6 = currentList.get(8);

			// Let us also record the cluster, step descriptions, and taskId
			step1Description = currentList.get(9);
			clusterColumn = currentList.get(10);
			taskId = Integer.parseInt(currentList.get(11));
			step2Description = currentList.get(12);
			step3Description = currentList.get(13);
			step4Description = currentList.get(14);
			step5Description = currentList.get(15);
			step6Description = currentList.get(16);
			

			// Use this information to make a SimilarityMatrixModel
			currentModel = new SimilarityMatrixModel(id, taskId, reportId1, reportId2, commonalityPercentage, step1,
					step2, step3, step4, step5, step6, clusterColumn, step1Description, step2Description,
					step3Description, step4Description, step5Description, step6Description);

			// Now add this model to the list of Models
			similarityMatrixList.add(currentModel);

			// Now let's make an entity for the pair and add into the table.
//			SimilarityMatrix currentSimilarityPair = new SimilarityMatrix(reportId1, reportId2, commonalityPercentage,
//					step1, step2, step3, step4, step5, step6);
//			similarityMatrixRepo.save(currentSimilarityPair);

		}

		// Convert the list of Models to a list of SimilarityMatrix entities
		List<SimilarityMatrix> similarityMatrixEntityList = new ArrayList<>();
		similarityMatrixList.stream()
				.forEach(similarity_matrix -> similarityMatrixEntityList.add(new SimilarityMatrix(
						similarity_matrix.getId(), similarity_matrix.getTaskId(), similarity_matrix.getReportId1(),
						similarity_matrix.getReportId2(), similarity_matrix.getCommonalityPercentage(),
						similarity_matrix.getStep1(), similarity_matrix.getStep2(), similarity_matrix.getStep3(),
						similarity_matrix.getStep4(), similarity_matrix.getStep5(), similarity_matrix.getStep6(),
						similarity_matrix.getClusterColumn(), similarity_matrix.getStep1Description(),
						similarity_matrix.getStep2Description(), similarity_matrix.getStep3Description(),
						similarity_matrix.getStep4Description(), similarity_matrix.getStep5Description(),
						similarity_matrix.getStep6Description())));
		
		// Save each of the items in the list of entities into the database
		similarityMatrixRepo.saveAll(similarityMatrixEntityList);


		// Return the list of Models
		return similarityMatrixList;
	}
	

	/**
	 * <p>
	 * This method retrieves all the unique entries in the dimensionList column of
	 * the commonality params table for a given reportId, and returns a list of
	 * these items. If all the rows corresponding to the reportId have dimensionList
	 * as null, it will return null. The benefit of using an Optional is that we can
	 * either return a list or a null. Used in step 4 of the similarity engine.
	 * </p>
	 * 
	 * @param reportId a report that has the given number associated to it
	 * @return Optional List of Strings that has the contents of the dimensionList
	 *         column in the commonality params table. It will return null, if all
	 *         entries of dimensionList are null.
	 */
	public Optional<List<String>> getStep4QueryDimensionListByGivenReportId(int reportId) {
		// Call the query for step 4 that gets the dimension list for a specific
		// reportId
		Optional<List<String>> step4DimensionListforSpecificreportid = Optional
				.of(commonalityRepo.getStep4QueryForDimensionList(reportId));
		// Return this query as a list of Strings
		return step4DimensionListforSpecificreportid;
	}

}