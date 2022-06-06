package com.recast.recast.bo.analyzer.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import com.recast.recast.bo.analyzer.dto.TableauReportConfigDTO;
import com.recast.recast.bo.analyzer.tableau.model.Axes;
import com.recast.recast.bo.analyzer.tableau.model.QueryFilter;
import com.recast.recast.bo.analyzer.tableau.model.ReportData;
import com.recast.recast.bo.analyzer.tableau.model.TabNewReportComplexity;
import com.recast.recast.bo.analyzer.tableau.model.TableauAnalyzerModel;
import com.recast.recast.bo.analyzer.tableau.model.TableauColumn;
import com.recast.recast.bo.analyzer.tableau.model.TableauColumnInstance;
import com.recast.recast.bo.analyzer.tableau.model.TableauEncoding;
import com.recast.recast.bo.analyzer.tableau.model.TableauFilter;
import com.recast.recast.bo.analyzer.tableau.model.TableauFormat;
import com.recast.recast.bo.analyzer.tableau.model.TableauItem;
import com.recast.recast.bo.analyzer.tableau.model.TableauReportColumn;
import com.recast.recast.bo.analyzer.tableau.model.TableauReportQuery;
import com.recast.recast.bo.analyzer.tableau.model.TableauReportQueryColumn;
import com.recast.recast.bo.analyzer.tableau.model.TableauReportTab;
import com.recast.recast.bo.analyzer.tableau.model.TableauSemanticModel;
import com.recast.recast.bo.analyzer.tableau.model.TableauStyleRule;
import com.recast.recast.bo.analyzer.tableau.model.TableauTable;
import com.recast.recast.bo.analyzer.tableau.model.TableauTableElement;
import com.recast.recast.bo.analyzer.tableau.model.TableauVariable;
import com.recast.recast.bo.analyzer.tableau.model.TableauVisualElements;
import com.recast.recast.bo.analyzer.tableau.model.TableauVisualizationModel;
import com.recast.recast.bo.analyzer.tableau.model.TableauWorkbookInfoModel;

public class TableauAnalyzerAPI {

	static final Logger logger = Logger.getLogger(TableauAnalyzerAPI.class);

	private static int reportId;

	private static int noOfFormulas;

	private static int noOfFilters;

	private static String TABSERVERIP = null;
	private static String TABUSERID = null;
	private static String TABPASSWORD = null;
	private static String TAB_CONNECTION_TYPE = null;
	private static String TAB_WS_PORT = null;
	private static String TAB_PATH = null;
	private static String TAB_CONTENT_URL = null;
	private static String logonToken;
	private static String siteId;

	private static final String REQUEST_METHOD_POST = "POST";
	private static final String REQUEST_METHOD_GET = "GET";
	private static final String REQUEST_PROPERTY_CONTENT_TYPE = "Content-Type";
	private static final String REQUEST_PROPERTY_APPL_XML = "application/xml";
	private static final String REQUEST_PROPERTY_ACCEPT = "Accept";
	private static final String REQUEST_PROPERTY_X_TABLEAU_AUTH = "X-Tableau-Auth";
	private static final String REQUEST_PROPERTY_APPL_OCTET_STREAM = "application/octet-stream";

	private static final int BUFFER_SIZE = 4096;

	public TableauAnalyzerAPI() {
		// TODO Auto-generated constructor stub
		TableauAnalyzerAPI.reportId = 1;

	}

	public static Map<String, String> logonAndGetTokenAndSiteID(String host, String username, String password,
			String contentUrl, String connectionType) throws MalformedURLException, IOException {
		Map<String, String> tokenAndSiteID = new HashMap<String, String>();

		StringBuilder output = new StringBuilder("");
		String strToken = "";
		String strSiteID = "";

		try {
			URL url = new URL(connectionType + "://" + host + "/api/3.12/auth/signin");
			logger.info("URL:" + url);
			System.out.println("URL:" + url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod(REQUEST_METHOD_POST);
			conn.setRequestProperty(REQUEST_PROPERTY_CONTENT_TYPE, REQUEST_PROPERTY_APPL_XML);
			conn.setRequestProperty(REQUEST_PROPERTY_ACCEPT, REQUEST_PROPERTY_APPL_XML);
			
			String input = "";
			
			if (contentUrl.equals(""))
			{
				input = "<tsRequest>" + "<credentials name=\"" + username + "\" password=\"" + password + "\">"
						+ "<site contentUrl=\"" + "\" />" + "</credentials>" + "</tsRequest>";
			}
			else 
			{
				input = "<tsRequest>" + "<credentials name=\"" + username + "\" password=\"" + password + "\">"
						+ "<site contentUrl=\"" + contentUrl + "\" />" + "</credentials>" + "</tsRequest>";
			}

			System.out.println("Input:" + input);

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String tempReadLine = br.readLine();
			while (tempReadLine != null) {
				output.append(tempReadLine);
				tempReadLine = br.readLine();
			}

			if (output.toString().equals("")) {
				return null;
			}
//			System.out.println("output:::" + output.toString());
			strToken = output.toString().substring(output.toString().indexOf("token") + 7,
					output.toString().indexOf("\"><site"));
//			System.out.println("strToken:::" + strToken);
			strSiteID = output.toString().substring(output.toString().indexOf("site") + 9,
					output.toString().indexOf("\" contentUrl"));
//			System.out.println("site id:::" + strSiteID);

			if (conn != null) {
				conn.disconnect();
				br.close();
			}

		} catch (Exception ex) {
			logger.error(ex);
			System.out.println("Exception:::" + ex);
		}

		tokenAndSiteID.put("token", strToken);
		tokenAndSiteID.put("siteID", strSiteID);

		return tokenAndSiteID;
	}

	public String testConnection(TableauReportConfigDTO config) {
		try {
			TABSERVERIP = config.getHostname();
			TABUSERID = config.getUsername();
			TABPASSWORD = config.getPassword();
			TAB_CONTENT_URL = config.getContentUrl();
			TAB_WS_PORT = config.getPort();
			TAB_CONNECTION_TYPE = config.getConnectionType();

			Map<String, String> tokenAndSiteId = logonAndGetTokenAndSiteID(TABSERVERIP, TABUSERID, TABPASSWORD, TAB_CONTENT_URL, TAB_CONNECTION_TYPE);
			logonToken = tokenAndSiteId.get("token");
			siteId = tokenAndSiteId.get("siteID");
			if (logonToken == null || logonToken.equalsIgnoreCase("")) {
				return "Fail";
			}
		} catch (Exception e) {
			return "Fail";
		} finally {
			logout(logonToken);
		}
		return "Success";
	}

	public static List<TableauWorkbookInfoModel> fetchTableauWorkbookInfo(TableauReportConfigDTO config) {
		List<TableauWorkbookInfoModel> tableauWorkbookList = new ArrayList<TableauWorkbookInfoModel>();

		try {
			StringBuilder output = new StringBuilder("");
//			Map<String, String> tokenAndSiteID = logonAndGetTokenAndSiteID("prod-apnortheast-a.online.tableau.com", "kpra16is@cmrit.ac.in", "Kpra16is@cmr", "recastinternal");
//			String token = tokenAndSiteID.get("token");
//			String siteId = tokenAndSiteID.get("siteID");

			String token = "53ZIeM0JQQmxxs7Jb-YOVQ|CMe0H5wxYbG5him8TFHk8k2JLqgBiuMN";
			String siteId = "4b789da7-73d5-46fc-a88c-499f415f5ac5";

			URL url = new URL(
					"https://" + "prod-apnortheast-a.online.tableau.com" + "/api/3.12/sites/" + siteId + "/workbooks");
			System.out.println("URL:" + url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod(REQUEST_METHOD_GET);
			conn.setRequestProperty(REQUEST_PROPERTY_X_TABLEAU_AUTH, token);

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String tempReadLine = br.readLine();
			while (tempReadLine != null) {
				output.append(tempReadLine);
				tempReadLine = br.readLine();
			}

			if (output.toString().equals("")) {
				return null;
			}

			System.out.println("output:::" + output.toString());

			JSONObject workbookDetailsJSON = XML.toJSONObject(output.toString());

			JSONObject workbooksJSONObj = workbookDetailsJSON.getJSONObject("tsResponse").getJSONObject("workbooks");

			tableauWorkbookList = fetchWorkbookInfo(workbooksJSONObj);
			System.out.println("tableau workbook list:::" + tableauWorkbookList);

			if (conn != null) {
				conn.disconnect();
				br.close();
			}

		} catch (Exception e) {
			System.out.println("Workbook Info Exception:::" + e);
		}

		return tableauWorkbookList;
	}

	public static String fetchTableauWorkbookTwbAsString(String workbookId) {
		String twbAsXml = "";

		try {
			StringBuilder output = new StringBuilder("");

//			String token = "v9HvD4lGS1C3OMOHPxIiKw|9udM7CMgrRB2PKCPlFBh9yESv8GL0HKA";
//			String siteId = "f821b569-7b67-433d-b206-dcd56c7e4489";

			URL url = new URL(TAB_CONNECTION_TYPE + "://" + TABSERVERIP + "/api/3.12/sites/" + siteId
					+ "/workbooks/" + workbookId + "/content");
			System.out.println("URL:" + url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod(REQUEST_METHOD_GET);
			conn.setRequestProperty(REQUEST_PROPERTY_X_TABLEAU_AUTH, logonToken);
			conn.setRequestProperty(REQUEST_PROPERTY_ACCEPT, REQUEST_PROPERTY_APPL_XML);
			
			if (conn.getContentType().equals("application/octet-stream")) {
				InputStream inputStream = conn.getInputStream();

				String destDirPath = "C:\\Tableau-Zip";
				File dir = new File(destDirPath);
				// create output directory if it doesn't exist
				if (!dir.exists())
					dir.mkdirs();
				File zipFile = new File("C:\\Tableau-Zip\\file.zip");
				zipFile.createNewFile(); // if file already exists will do nothing 
				String zipFilePath = "C:\\Tableau-Zip\\file.zip";

				OutputStream outputStream = new FileOutputStream(zipFile);

				byte[] buffer = new byte[BUFFER_SIZE];
				int bytesRead = -1;

				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}

				outputStream.flush();
				outputStream.close();

				// Unzip the file
				String twbFilePath = unzipAndGetTwbPath(zipFilePath, destDirPath);

				twbAsXml = getTwbAsXml(twbFilePath);

//	            System.out.println("twb as xml:::" + twbAsXml);
				File destDir = new File(destDirPath);

				FileUtils.cleanDirectory(destDir);
				FileUtils.deleteDirectory(destDir);
			} else if (conn.getContentType().equals("application/xml")) {
				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

				String tempReadLine = br.readLine();
				while (tempReadLine != null) {
					output.append(tempReadLine);
					tempReadLine = br.readLine();
				}

				if (output.toString().equals("")) {
					return null;
				}
				
				twbAsXml = output.toString();
			}

			

			if (conn != null) {
				conn.disconnect();
			}

		} catch (Exception e) {
			System.out.println("Twb file Extraction Exception:::" + e);
		}

		return twbAsXml;

	}

	private static String unzipAndGetTwbPath(String zipFilePath, String destDir) {
		String twbFilePath = "";
		File dir = new File(destDir);
		// create output directory if it doesn't exist
		if (!dir.exists())
			dir.mkdirs();
		FileInputStream fis;
		// buffer for read and write data to file
		byte[] buffer = new byte[1024];
		try {
			fis = new FileInputStream(zipFilePath);
			ZipInputStream zis = new ZipInputStream(fis);
			ZipEntry ze = zis.getNextEntry();
			while (ze != null) {
				String fileName = ze.getName();
				String fileExtension = fileName.substring(fileName.length() - 3);
				System.out.println("File name:::" + fileName);

				if (fileExtension.equals("twb")) {
					twbFilePath = destDir + File.separator + fileName;
				}

				File newFile = new File(destDir + File.separator + fileName);
				System.out.println("Unzipping to " + newFile.getAbsolutePath());
				// create directories for sub directories in zip
				new File(newFile.getParent()).mkdirs();
				FileOutputStream fos = new FileOutputStream(newFile);
				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				fos.close();
				// close this ZipEntry
				zis.closeEntry();
				ze = zis.getNextEntry();
			}
			// close last ZipEntry
			zis.closeEntry();
			zis.close();
			fis.close();
		} catch (IOException e) {
			System.out.println("File Unzip IOException:::" + e);
		} catch (Exception e) {
			System.out.println("File Unzip Exception:::" + e);
		}

		return twbFilePath;

	}

	private static String getTwbAsXml(String filePath) {
		String twbAsXml = "";

		File file = new File(filePath);

		try {
			twbAsXml = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
		} catch (Exception e) {
			System.out.println("File Conversion Exception:::" + e);
		}

		return twbAsXml;
	}

	private static List<TableauWorkbookInfoModel> fetchWorkbookInfo(JSONObject workbooksJSONObj) {
		List<TableauWorkbookInfoModel> tabWorkbookList = new ArrayList<TableauWorkbookInfoModel>();

		if (workbooksJSONObj.has("workbook")) {
			try {
				JSONObject workbookJSONObj = workbooksJSONObj.optJSONObject("workbook");
				if (workbookJSONObj != null)
				{
					TableauWorkbookInfoModel tabWorkbook = new TableauWorkbookInfoModel();

					if (workbookJSONObj.has("id")) {
						String id = workbookJSONObj.getString("id");
						tabWorkbook.setWorkbookID(id);
					}
					if (workbookJSONObj.has("name")) {
						String name = workbookJSONObj.getString("name");
						tabWorkbook.setWorkbookName(name);
					}
					if (workbookJSONObj.has("webpageUrl")) {
						String webpageUrl = workbookJSONObj.getString("webpageUrl");
						tabWorkbook.setWebPageUrl(webpageUrl);
					}
					if (workbookJSONObj.has("createdAt")) {
						String createdAt = workbookJSONObj.getString("createdAt");
						tabWorkbook.setCreatedDate(createdAt);
					}
					if (workbookJSONObj.has("updatedAt")) {
						String updatedAt = workbookJSONObj.getString("updatedAt");
						tabWorkbook.setUpdatedDate(updatedAt);
					}
					if (workbookJSONObj.has("project")) {
						JSONObject projectJSONObj = workbookJSONObj.getJSONObject("project");
						String projectId = projectJSONObj.getString("id");
						String projectName = projectJSONObj.getString("name");

						tabWorkbook.setProjectID(projectId);
						tabWorkbook.setProjectName(projectName);
					}
					tabWorkbookList.add(tabWorkbook);
				}
				else 
				{
					JSONArray workbookJSONArray = workbooksJSONObj.getJSONArray("workbook");

					for (Object wbJSONObj : workbookJSONArray) {
						JSONObject workbookJSONObj1 = (JSONObject) wbJSONObj;
						TableauWorkbookInfoModel tabWorkbook = new TableauWorkbookInfoModel();

						if (workbookJSONObj1.has("id")) {
							String id = workbookJSONObj1.getString("id");
							tabWorkbook.setWorkbookID(id);
						}
						if (workbookJSONObj1.has("name")) {
							String name = workbookJSONObj1.getString("name");
							tabWorkbook.setWorkbookName(name);
						}
						if (workbookJSONObj1.has("webpageUrl")) {
							String webpageUrl = workbookJSONObj1.getString("webpageUrl");
							tabWorkbook.setWebPageUrl(webpageUrl);
						}
						if (workbookJSONObj1.has("createdAt")) {
							String createdAt = workbookJSONObj1.getString("createdAt");
							tabWorkbook.setCreatedDate(createdAt);
						}
						if (workbookJSONObj1.has("updatedAt")) {
							String updatedAt = workbookJSONObj1.getString("updatedAt");
							tabWorkbook.setUpdatedDate(updatedAt);
						}
						if (workbookJSONObj1.has("project")) {
							JSONObject projectJSONObj = workbookJSONObj1.getJSONObject("project");
							String projectId = projectJSONObj.getString("id");
							String projectName = projectJSONObj.getString("name");

							tabWorkbook.setProjectID(projectId);
							tabWorkbook.setProjectName(projectName);
						}
						tabWorkbookList.add(tabWorkbook);
					}
				}
			} catch (Exception e) {
				System.out.println("Particular Workbook Info Exception:::" + e);
			}
		}

		return tabWorkbookList;
	}

	public Map<String, Object> fetchTabAnalyzerModelList(TableauReportConfigDTO config) {

		Map<String, Object> analyzerData = new HashMap<String, Object>();
		List<TableauAnalyzerModel> reportModelList = new ArrayList<TableauAnalyzerModel>();
		
		List<TableauSemanticModel> semanticModelList = new ArrayList<TableauSemanticModel>();
		Set<String> datasources = new HashSet<String>();
		
		List<TableauVisualizationModel> visualizationModelList = new ArrayList<TableauVisualizationModel>();
		
		List<TabNewReportComplexity> tabNewReportComplexityList = new ArrayList<TabNewReportComplexity>();
		
		List<String> workbookIdList = config.getWorkbooks();

		try {
			TABSERVERIP = config.getHostname();
			TABUSERID = config.getUsername();
			TABPASSWORD = config.getPassword();
			TAB_CONTENT_URL = config.getContentUrl();
			TAB_WS_PORT = config.getPort();
			TAB_CONNECTION_TYPE = config.getConnectionType();
			TAB_PATH = config.getPath();
			
			if (!TAB_PATH.isEmpty()) {
				
				String pathCheck = TAB_PATH.substring(TAB_PATH.length() - 3);
				
				if (pathCheck.equals("twb") || pathCheck.equals("xml"))
				{
					//Get file path
					
					String filePath = TAB_PATH;
					File file = new File(filePath);
					String inputXML = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

//					System.out.println("XML::"+inputXML);

					JSONObject reportJSON = XML.toJSONObject(inputXML);
//					System.out.println("JSON values::::"+reportJSON);

					JSONObject worksheet = reportJSON.getJSONObject("workbook").getJSONObject("worksheets");
					//

					try {
						JSONObject worksheetJSONObj = worksheet.optJSONObject("worksheet");
						
						if (worksheetJSONObj != null)
						{
							TableauAnalyzerModel tabAnalyzerModel = new TableauAnalyzerModel();
							int totalDataSourceCount = 0;

							tabAnalyzerModel = FetchTableauReportMetaData(worksheetJSONObj, tabAnalyzerModel);
							List<String> universes = tabAnalyzerModel.getUniverses();
							
							String workbookName = filePath.substring(filePath.lastIndexOf('\\') + 1);
							String folderPath = filePath.substring(0, filePath.lastIndexOf('\\'));
							
							tabAnalyzerModel.setReportFolderPath(folderPath);
							tabAnalyzerModel.setWorkbookName(workbookName);
							
							totalDataSourceCount = universes.size();
							tabAnalyzerModel.setTotalUniverseCount(totalDataSourceCount);
							datasources.addAll(universes);

							// System.out.println("TabAnalyzer Model::"+tabAnalyzerModel);
							reportModelList.add(tabAnalyzerModel);
							
							int numberOfFilters = fetchTotalTabFilters(worksheetJSONObj);
							
							//Code for adding Complexity details
							int numberOfVariables = getNumberOfTabVariables(worksheetJSONObj);
							int numberOfComplexCalculations = getNumberOfComplexCalculations(worksheetJSONObj);
							
							List<TabNewReportComplexity> complexityList = new ArrayList<TabNewReportComplexity>();
							
							TabNewReportComplexity tabNewReportComplexity = new TabNewReportComplexity();
							tabNewReportComplexity.setNumberOfDataSources(tabAnalyzerModel.getTotalUniverseCount());
							tabNewReportComplexity.setNumberOfFilters(numberOfFilters);
							tabNewReportComplexity.setNumberOfVariables(numberOfVariables);
							tabNewReportComplexity.setNumberOfComplexCalculations(numberOfComplexCalculations);
							tabNewReportComplexityList.add(tabNewReportComplexity);
							
							complexityList.add(tabNewReportComplexity);
							
							tabAnalyzerModel.setComplexityList(complexityList);
							
							//Code for getting visual model
							TableauVisualizationModel tabVisualizationModel = new TableauVisualizationModel();
							tabVisualizationModel = fetchTableauVisualizationData(worksheetJSONObj, tabVisualizationModel);
							System.out.println("TAB VIZ MODEL:::" + tabVisualizationModel);
							visualizationModelList.add(tabVisualizationModel);
						}
						else
						{
							JSONArray worksheetArray = worksheet.getJSONArray("worksheet");
							
							for (int i = 0; i < worksheetArray.length(); i++) {
								JSONObject worksheetJSONObj1 = worksheetArray.getJSONObject(i);
								TableauAnalyzerModel tabAnalyzerModel = new TableauAnalyzerModel();
								int totalDataSourceCount = 0;

								tabAnalyzerModel = FetchTableauReportMetaData(worksheetJSONObj1, tabAnalyzerModel);
								List<String> universes = tabAnalyzerModel.getUniverses();
								
								String workbookName = filePath.substring(filePath.lastIndexOf('\\') + 1);
								String folderPath = filePath.substring(0, filePath.lastIndexOf('\\'));
								
								tabAnalyzerModel.setReportFolderPath(folderPath);
								tabAnalyzerModel.setWorkbookName(workbookName);
								
								totalDataSourceCount = universes.size();
								tabAnalyzerModel.setTotalUniverseCount(totalDataSourceCount);
								datasources.addAll(universes);
								// System.out.println("Tab Model::"+tabAnalyzerModel);

								// System.out.println("getTableauReportVariableListJSON::"+tabAnalyzerModel.getTableauReportVariableListJSON());

								reportModelList.add(tabAnalyzerModel);
								
								int numberOfFilters = fetchTotalTabFilters(worksheetJSONObj1);
								
								//Code for adding Complexity details
								int numberOfVariables = getNumberOfTabVariables(worksheetJSONObj1);
								int numberOfComplexCalculations = getNumberOfComplexCalculations(worksheetJSONObj1);
								
								List<TabNewReportComplexity> complexityList = new ArrayList<TabNewReportComplexity>();
								
								TabNewReportComplexity tabNewReportComplexity = new TabNewReportComplexity();
								tabNewReportComplexity.setNumberOfDataSources(tabAnalyzerModel.getTotalUniverseCount());
								tabNewReportComplexity.setNumberOfFilters(numberOfFilters);
								tabNewReportComplexity.setNumberOfVariables(numberOfVariables);
								tabNewReportComplexity.setNumberOfComplexCalculations(numberOfComplexCalculations);
								tabNewReportComplexityList.add(tabNewReportComplexity);
								
								complexityList.add(tabNewReportComplexity);
								
								tabAnalyzerModel.setComplexityList(complexityList);
								
								//Code for getting visual model
								TableauVisualizationModel tabVisualizationModel = new TableauVisualizationModel();
								tabVisualizationModel = fetchTableauVisualizationData(worksheetJSONObj1, tabVisualizationModel);
								System.out.println("TAB VIZ MODEL:::" + tabVisualizationModel);
								visualizationModelList.add(tabVisualizationModel);

							}
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
					
					semanticModelList = fetchListOfDataProviders(datasources, config, true);
				}
				else if (pathCheck.equals("zip"))
				{
					String folderPath = TAB_PATH.substring(0, TAB_PATH.length()-3);
					File folder = new File(folderPath);
					
					File[] listOfFiles = folder.listFiles();
					
					for (File file : listOfFiles) {
					    if (file.isFile()) {
					        System.out.println("Absolute Path:: " + file.getAbsolutePath());
					        
					        String filePath = file.getAbsolutePath();
					        File file1 = new File(filePath);
							String inputXML = FileUtils.readFileToString(file1, StandardCharsets.UTF_8);

//							System.out.println("XML::"+inputXML);

							JSONObject reportJSON = XML.toJSONObject(inputXML);
//							System.out.println("JSON values::::"+reportJSON);

							JSONObject worksheet = reportJSON.getJSONObject("workbook").getJSONObject("worksheets");
							//

							try {
								JSONObject worksheetJSONObj = worksheet.optJSONObject("worksheet");
								
								if (worksheetJSONObj != null)
								{
									TableauAnalyzerModel tabAnalyzerModel = new TableauAnalyzerModel();
									int totalDataSourceCount = 0;

									tabAnalyzerModel = FetchTableauReportMetaData(worksheetJSONObj, tabAnalyzerModel);
									List<String> universes = tabAnalyzerModel.getUniverses();
									
									String workbookName = filePath.substring(filePath.lastIndexOf('\\') + 1);
									String folderPath1 = filePath.substring(0, filePath.lastIndexOf('\\'));
									
									tabAnalyzerModel.setReportFolderPath(folderPath1);
									tabAnalyzerModel.setWorkbookName(workbookName);
									
									totalDataSourceCount = universes.size();
									tabAnalyzerModel.setTotalUniverseCount(totalDataSourceCount);
									datasources.addAll(universes);

									// System.out.println("TabAnalyzer Model::"+tabAnalyzerModel);
									reportModelList.add(tabAnalyzerModel);
									
									int numberOfFilters = fetchTotalTabFilters(worksheetJSONObj);
									
									//Code for adding Complexity details
									int numberOfVariables = getNumberOfTabVariables(worksheetJSONObj);
									int numberOfComplexCalculations = getNumberOfComplexCalculations(worksheetJSONObj);
									
									List<TabNewReportComplexity> complexityList = new ArrayList<TabNewReportComplexity>();
									
									TabNewReportComplexity tabNewReportComplexity = new TabNewReportComplexity();
									tabNewReportComplexity.setNumberOfDataSources(tabAnalyzerModel.getTotalUniverseCount());
									tabNewReportComplexity.setNumberOfFilters(numberOfFilters);
									tabNewReportComplexity.setNumberOfVariables(numberOfVariables);
									tabNewReportComplexity.setNumberOfComplexCalculations(numberOfComplexCalculations);
									tabNewReportComplexityList.add(tabNewReportComplexity);
									
									complexityList.add(tabNewReportComplexity);
									
									tabAnalyzerModel.setComplexityList(complexityList);
									
									//Code for getting visual model
									TableauVisualizationModel tabVisualizationModel = new TableauVisualizationModel();
									tabVisualizationModel = fetchTableauVisualizationData(worksheetJSONObj, tabVisualizationModel);
									System.out.println("TAB VIZ MODEL:::" + tabVisualizationModel);
									visualizationModelList.add(tabVisualizationModel);
								}
								else
								{
									JSONArray worksheetArray = worksheet.getJSONArray("worksheet");
									
									for (int i = 0; i < worksheetArray.length(); i++) {
										JSONObject worksheetJSONObj1 = worksheetArray.getJSONObject(i);
										TableauAnalyzerModel tabAnalyzerModel = new TableauAnalyzerModel();
										int totalDataSourceCount = 0;

										tabAnalyzerModel = FetchTableauReportMetaData(worksheetJSONObj1, tabAnalyzerModel);
										List<String> universes = tabAnalyzerModel.getUniverses();
										
										String workbookName = filePath.substring(filePath.lastIndexOf('\\') + 1);
										String folderPath1 = filePath.substring(0, filePath.lastIndexOf('\\'));
										
										tabAnalyzerModel.setReportFolderPath(folderPath1);
										tabAnalyzerModel.setWorkbookName(workbookName);
										
										totalDataSourceCount = universes.size();
										tabAnalyzerModel.setTotalUniverseCount(totalDataSourceCount);
										datasources.addAll(universes);
										// System.out.println("Tab Model::"+tabAnalyzerModel);

										// System.out.println("getTableauReportVariableListJSON::"+tabAnalyzerModel.getTableauReportVariableListJSON());

										reportModelList.add(tabAnalyzerModel);
										
										int numberOfFilters = fetchTotalTabFilters(worksheetJSONObj1);
										
										//Code for adding Complexity details
										int numberOfVariables = getNumberOfTabVariables(worksheetJSONObj1);
										int numberOfComplexCalculations = getNumberOfComplexCalculations(worksheetJSONObj1);
										
										List<TabNewReportComplexity> complexityList = new ArrayList<TabNewReportComplexity>();
										
										TabNewReportComplexity tabNewReportComplexity = new TabNewReportComplexity();
										tabNewReportComplexity.setNumberOfDataSources(tabAnalyzerModel.getTotalUniverseCount());
										tabNewReportComplexity.setNumberOfFilters(numberOfFilters);
										tabNewReportComplexity.setNumberOfVariables(numberOfVariables);
										tabNewReportComplexity.setNumberOfComplexCalculations(numberOfComplexCalculations);
										tabNewReportComplexityList.add(tabNewReportComplexity);
										
										complexityList.add(tabNewReportComplexity);
										
										tabAnalyzerModel.setComplexityList(complexityList);
										
										//Code for getting visual model
										TableauVisualizationModel tabVisualizationModel = new TableauVisualizationModel();
										tabVisualizationModel = fetchTableauVisualizationData(worksheetJSONObj1, tabVisualizationModel);
										System.out.println("TAB VIZ MODEL:::" + tabVisualizationModel);
										visualizationModelList.add(tabVisualizationModel);

									}
								}

							} catch (Exception e) {
								e.printStackTrace();
							}
							
							semanticModelList = fetchListOfDataProviders(datasources, config, true);
					    }
					}
					
				}
				
				System.out.println("Visualization Model List::"+visualizationModelList);
				analyzerData.put("reports", reportModelList);
				analyzerData.put("datasources", semanticModelList);
				analyzerData.put("complexity", tabNewReportComplexityList);
				analyzerData.put("visualizations", visualizationModelList);
				
				return analyzerData;
			}

			Map<String, String> tokenAndSiteId = logonAndGetTokenAndSiteID(TABSERVERIP, TABUSERID, TABPASSWORD, TAB_CONTENT_URL, TAB_CONNECTION_TYPE);
			logonToken = tokenAndSiteId.get("token");
			siteId = tokenAndSiteId.get("siteID");
			
			Map<String, List<String>> workbookNameIdMap = fetchWorkbookNameId(config);
			
			System.out.println("Workbook Map:: " + workbookNameIdMap);
			System.out.println("workbookID List:: " + workbookIdList);
			
			for (String workbookId : workbookIdList)
			{
//				String filePath = "D:\\Tableau\\Workbooks\\main-servicenow-report\\LTT product Dashboard migration - Metadata[1].twb";
//				File file = new File(filePath);
//				String inputXML = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
				
				String inputXML = fetchTableauWorkbookTwbAsString(workbookId);

//				System.out.println("XML::"+inputXML);

				JSONObject reportJSON = XML.toJSONObject(inputXML);
//				System.out.println("JSON values::::"+reportJSON);

				JSONObject worksheet = reportJSON.getJSONObject("workbook").getJSONObject("worksheets");
				//

				try {
					JSONObject worksheetJSONObj = worksheet.optJSONObject("worksheet");
					
					if (worksheetJSONObj != null)
					{
						TableauAnalyzerModel tabAnalyzerModel = new TableauAnalyzerModel();
						int totalDataSourceCount = 0;

						tabAnalyzerModel = FetchTableauReportMetaData(worksheetJSONObj, tabAnalyzerModel);
						List<String> universes = tabAnalyzerModel.getUniverses();
						
						List<String> arr = workbookNameIdMap.get(workbookId);
						
						String workbookName = arr.get(0);
						String folderPath = arr.get(1);
						
						tabAnalyzerModel.setReportFolderPath(folderPath);
						tabAnalyzerModel.setWorkbookName(workbookName);
						
						totalDataSourceCount = universes.size();
						tabAnalyzerModel.setTotalUniverseCount(totalDataSourceCount);
						datasources.addAll(universes);

						// System.out.println("TabAnalyzer Model::"+tabAnalyzerModel);
						reportModelList.add(tabAnalyzerModel);
						
						int numberOfFilters = fetchTotalTabFilters(worksheetJSONObj);
						
						//Code for adding Complexity details
						int numberOfVariables = getNumberOfTabVariables(worksheetJSONObj);
						int numberOfComplexCalculations = getNumberOfComplexCalculations(worksheetJSONObj);
						
						List<TabNewReportComplexity> complexityList = new ArrayList<TabNewReportComplexity>();
						
						TabNewReportComplexity tabNewReportComplexity = new TabNewReportComplexity();
						tabNewReportComplexity.setNumberOfDataSources(tabAnalyzerModel.getTotalUniverseCount());
						tabNewReportComplexity.setNumberOfFilters(numberOfFilters);
						tabNewReportComplexity.setNumberOfVariables(numberOfVariables);
						tabNewReportComplexity.setNumberOfComplexCalculations(numberOfComplexCalculations);
						tabNewReportComplexityList.add(tabNewReportComplexity);
						
						complexityList.add(tabNewReportComplexity);
						
						tabAnalyzerModel.setComplexityList(complexityList);
						
						//Code for getting visual model
						TableauVisualizationModel tabVisualizationModel = new TableauVisualizationModel();
						tabVisualizationModel = fetchTableauVisualizationData(worksheetJSONObj, tabVisualizationModel);
						System.out.println("TAB VIZ MODEL:::" + tabVisualizationModel);
						visualizationModelList.add(tabVisualizationModel);
					}
					else 
					{
						JSONArray worksheetArray = worksheet.getJSONArray("worksheet");

						for (int i = 0; i < worksheetArray.length(); i++) {
							JSONObject worksheetJSONObj1 = worksheetArray.getJSONObject(i);
							TableauAnalyzerModel tabAnalyzerModel = new TableauAnalyzerModel();
							int totalDataSourceCount = 0;

							tabAnalyzerModel = FetchTableauReportMetaData(worksheetJSONObj1, tabAnalyzerModel);
							List<String> universes = tabAnalyzerModel.getUniverses();
							
							List<String> arr = workbookNameIdMap.get(workbookId);
							
							String workbookName = arr.get(0);
							String folderPath = arr.get(1);
							
							tabAnalyzerModel.setReportFolderPath(folderPath);
							tabAnalyzerModel.setWorkbookName(workbookName);
							
							totalDataSourceCount = universes.size();
							tabAnalyzerModel.setTotalUniverseCount(totalDataSourceCount);
							datasources.addAll(universes);
							// System.out.println("Tab Model::"+tabAnalyzerModel);

							// System.out.println("getTableauReportVariableListJSON::"+tabAnalyzerModel.getTableauReportVariableListJSON());

							reportModelList.add(tabAnalyzerModel);
							
							int numberOfFilters = fetchTotalTabFilters(worksheetJSONObj1);
							
							//Code for adding Complexity details
							int numberOfVariables = getNumberOfTabVariables(worksheetJSONObj1);
							int numberOfComplexCalculations = getNumberOfComplexCalculations(worksheetJSONObj1);
							
							List<TabNewReportComplexity> complexityList = new ArrayList<TabNewReportComplexity>();
							
							TabNewReportComplexity tabNewReportComplexity = new TabNewReportComplexity();
							tabNewReportComplexity.setNumberOfDataSources(tabAnalyzerModel.getTotalUniverseCount());
							tabNewReportComplexity.setNumberOfFilters(numberOfFilters);
							tabNewReportComplexity.setNumberOfVariables(numberOfVariables);
							tabNewReportComplexity.setNumberOfComplexCalculations(numberOfComplexCalculations);
							tabNewReportComplexityList.add(tabNewReportComplexity);
							
							complexityList.add(tabNewReportComplexity);
							
							tabAnalyzerModel.setComplexityList(complexityList);
							
							//Code for getting visual model
							TableauVisualizationModel tabVisualizationModel = new TableauVisualizationModel();
							tabVisualizationModel = fetchTableauVisualizationData(worksheetJSONObj1, tabVisualizationModel);
							System.out.println("TAB VIZ MODEL:::" + tabVisualizationModel);
							visualizationModelList.add(tabVisualizationModel);

						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				
				semanticModelList = fetchListOfDataProviders(datasources, config, false);
			}

			System.out.println("Visualization Model List::"+visualizationModelList);
			analyzerData.put("reports", reportModelList);
			analyzerData.put("datasources", semanticModelList);
			analyzerData.put("complexity", tabNewReportComplexityList);
			analyzerData.put("visualizations", visualizationModelList);
			
			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			logout(logonToken);
		}

		return analyzerData;

	}
	
	private Map<String, List<String>> fetchWorkbookNameId(TableauReportConfigDTO config) {
		Map<String, List<String>> workbookNameIdMap = new HashMap<String, List<String>>();
		StringBuilder output = new StringBuilder("");
		
		try {
			URL url = new URL(
					TAB_CONNECTION_TYPE + "://" + TABSERVERIP + "/api/3.12/sites/" + siteId + "/workbooks");
			System.out.println("URL:" + url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod(REQUEST_METHOD_GET);
			conn.setRequestProperty(REQUEST_PROPERTY_X_TABLEAU_AUTH, logonToken);

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String tempReadLine = br.readLine();
			while (tempReadLine != null) {
				output.append(tempReadLine);
				tempReadLine = br.readLine();
			}

			if (output.toString().equals("")) {
				return null;
			}

			System.out.println("output:::" + output.toString());
			
			JSONObject workbooksRequestJSONObj = XML.toJSONObject(output.toString());

			JSONObject workbooksJSONObj = workbooksRequestJSONObj.getJSONObject("tsResponse")
					.getJSONObject("workbooks");
			
			workbookNameIdMap = fetchWorkbookMapInfo(workbooksJSONObj);
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return workbookNameIdMap;
	}
	
	private Map<String, List<String>> fetchWorkbookMapInfo(JSONObject workbooksJSONObj) {
		Map<String, List<String>> workbookNameIdMap = new HashMap<String, List<String>>();
		
		try {
			JSONObject workbookJSONObj = workbooksJSONObj.optJSONObject("workbook");

			if (workbookJSONObj != null)
			{
				String workbookId = workbookJSONObj.getString("id");
				String workbookName = workbookJSONObj.getString("name");
				String folderPath = workbookJSONObj.getJSONObject("project").getString("name") + "/" + workbookName;
				List<String> arr = new ArrayList<String>();
				arr.add(workbookName);
				arr.add(folderPath);
				
				workbookNameIdMap.put(workbookId, arr);
			}
			else
			{
				JSONArray workbookJSONArray = workbooksJSONObj.getJSONArray("workbook");

				for (Object workbookObj : workbookJSONArray) {
					JSONObject workbookJSONObj1 = (JSONObject) workbookObj;
					
					String workbookId = workbookJSONObj1.getString("id");
					String workbookName = workbookJSONObj1.getString("name");
					String folderPath = workbookJSONObj1.getJSONObject("project").getString("name") + "/" + workbookName;
					List<String> arr = new ArrayList<String>();
					arr.add(workbookName);
					arr.add(folderPath);
					
					workbookNameIdMap.put(workbookId, arr);

				}
			}

		} catch (Exception e) {
			System.out.println("Workbook Id Name Map Exception:::" + e);
		}
		
		return workbookNameIdMap;
	}
	
	private Integer getNumberOfComplexCalculations(JSONObject worksheetJSONObj) {
		int numberOfComplexCalculations = 0;
		
		JSONObject viewJSONObj = worksheetJSONObj.getJSONObject("table").getJSONObject("view");
		
		try
		{
			JSONObject datasourceDependenciesJSONObj = viewJSONObj.optJSONObject("datasource-dependencies");
			
			if (datasourceDependenciesJSONObj != null)
			{
				try
				{
					JSONObject columnJSONObj = datasourceDependenciesJSONObj.optJSONObject("column");
					
					if (columnJSONObj != null)
					{
						if (columnJSONObj.has("calculation"))
						{
							JSONObject calculationJSONObj = columnJSONObj.getJSONObject("calculation");
							if (calculationJSONObj.has("formula"))
							{
								String formula = calculationJSONObj.get("formula").toString();
								if (formula.contains("EXCLUDE") || formula.contains("INCLUDE"))
								{
									numberOfComplexCalculations++;
								}
							}
						}
					}
					else
					{
						JSONArray columnJSONArray = datasourceDependenciesJSONObj.getJSONArray("column");

						for (Object columnObj : columnJSONArray)
						{
							JSONObject columnJSONObj1 = (JSONObject) columnObj;
							if (columnJSONObj1.has("calculation"))
							{
								JSONObject calculationJSONObj = columnJSONObj1.getJSONObject("calculation");
								if (calculationJSONObj.has("formula"))
								{
									String formula = calculationJSONObj.get("formula").toString();
									if (formula.contains("EXCLUDE") || formula.contains("INCLUDE"))
									{
										numberOfComplexCalculations++;
									}
								}
							}
						}
					}
					
				}
				catch (Exception e)
				{
					System.out.println("Number of complex calculations in column Exception:::" + e);
				}
			}
			else
			{
				JSONArray datasourceDependenciesJSONArray = viewJSONObj.getJSONArray("datasource-dependencies");

				for (Object datasourceDependenciesObj : datasourceDependenciesJSONArray)
				{
					JSONObject datasourceDependenciesJSONObj1 = (JSONObject) datasourceDependenciesObj;
					System.out.println("datasource dep json:::" + datasourceDependenciesJSONObj1);
					
					try
					{
						JSONObject columnJSONObj = datasourceDependenciesJSONObj1.optJSONObject("column");
						
						if (columnJSONObj != null)
						{
							System.out.println("cloumn obj json:::" + columnJSONObj);
							
							if (columnJSONObj.has("calculation"))
							{
								JSONObject calculationJSONObj = columnJSONObj.getJSONObject("calculation");
								if (calculationJSONObj.has("formula"))
								{
									String formula = calculationJSONObj.get("formula").toString();
									if (formula.contains("EXCLUDE") || formula.contains("INCLUDE"))
									{
										numberOfComplexCalculations++;
									}
								}
							}
						}
						else
						{
							JSONArray columnJSONArray = datasourceDependenciesJSONObj1.getJSONArray("column");
							
							for (Object columnObj : columnJSONArray)
							{
								JSONObject columnJSONObj1 = (JSONObject) columnObj;
								System.out.println("cloumn obj json:::" + columnJSONObj1);
								
								if (columnJSONObj1.has("calculation"))
								{
									JSONObject calculationJSONObj = columnJSONObj1.getJSONObject("calculation");
									if (calculationJSONObj.has("formula"))
									{
										String formula = calculationJSONObj.get("formula").toString();
										if (formula.contains("EXCLUDE") || formula.contains("INCLUDE"))
										{
											numberOfComplexCalculations++;
										}
									}
								}
							}
						}
					}
					catch (JSONException je2)
					{
						
						
					}
					catch (Exception e)
					{
						System.out.println("Number of complex calculations in column Exception:::" + e);
					}
				}
			}
		}
		catch (Exception e)
		{
			System.out.println("Number of complex calculations Exception:::" + e);
		}
		
		return numberOfComplexCalculations;
	}
	
	private Integer getNumberOfTabVariables (JSONObject worksheetJSONObj) {
		int numberOfVariables = 0;
		
		JSONObject viewJSONObj = worksheetJSONObj.getJSONObject("table").getJSONObject("view");
		
		try
		{
			JSONObject datasourceDependenciesJSONObj = viewJSONObj.optJSONObject("datasource-dependencies");
			
			if (datasourceDependenciesJSONObj != null)
			{
				if (datasourceDependenciesJSONObj.has("datasource"))
				{
					String datasource = datasourceDependenciesJSONObj.getString("datasource");
					if (datasource.equals("Parameters"))
					{
						try
						{
							JSONObject columnJSONObj = datasourceDependenciesJSONObj.optJSONObject("column");
							
							if (columnJSONObj != null)
							{
								numberOfVariables++;
							}
							else
							{
								JSONArray columnJSONArray = datasourceDependenciesJSONObj.getJSONArray("column");
								
								for (Object columnObj : columnJSONArray)
								{
									JSONObject columnJSONObj1 = (JSONObject) columnObj;
									numberOfVariables++;
								}
							}
							
						}
						catch (Exception e1)
						{
							System.out.println("Column count for variables Exception:::" + e1);
						}
					}
				}
			}
			else
			{
				JSONArray datasourceDependenciesJSONArray = viewJSONObj.getJSONArray("datasource-dependencies");
				
				for (Object datasourceDependenciesObj : datasourceDependenciesJSONArray)
				{
					JSONObject datasourceDependenciesJSONObj1 = (JSONObject) datasourceDependenciesObj;
					if (datasourceDependenciesJSONObj1.has("datasource"))
					{
						String datasource = datasourceDependenciesJSONObj1.getString("datasource");
						if (datasource.equals("Parameters"))
						{
							try
							{
								JSONObject columnJSONObj = datasourceDependenciesJSONObj1.optJSONObject("column");
								
								if (columnJSONObj != null)
								{
									numberOfVariables++;
								}
								else
								{
									JSONArray columnJSONArray = datasourceDependenciesJSONObj1.getJSONArray("column");
									
									for (Object columnObj : columnJSONArray)
									{
										JSONObject columnJSONObj1 = (JSONObject) columnObj;
										numberOfVariables++;
									}
								}
								
							}
							catch (Exception e1)
							{
								System.out.println("Column count for variables Exception:::" + e1);
							}
						}
					}
				}
			}
			
		}
		catch (Exception e)
		{
			System.out.println("Number of Parameters expection:::" + e);
		}
		
		return numberOfVariables;
	}
	
	private int fetchTotalTabFilters(JSONObject worksheetJSONObj) {
		int numberOfFilters = 0;
		
		JSONObject viewJSONObj = worksheetJSONObj.getJSONObject("table").getJSONObject("view");
		
		if (viewJSONObj.has("filter"))
		{
			try
			{
				JSONObject filterJSONObj = viewJSONObj.optJSONObject("filter");
				
				if (filterJSONObj != null)
				{
					numberOfFilters++;
				}
				else
				{
					JSONArray filterJSONArray = viewJSONObj.getJSONArray("filter");
					
					for (Object filterObj : filterJSONArray)
					{
						JSONObject filterJSONObj1 = (JSONObject) filterObj;
						numberOfFilters++;
					}
				}
				
			}
			catch (Exception e) {
				System.out.println("Number of filter exception:::" + e);
			}
		}
		
		
		return numberOfFilters;
	}
	
	private TableauVisualizationModel fetchTableauVisualizationData(JSONObject worksheetJSONObj, TableauVisualizationModel tabVisualizationModel) {
		
		String reportName = worksheetJSONObj.getString("name");
		tabVisualizationModel.setReportName(reportName);
		
		try
		{
			if (worksheetJSONObj.has("simple-id"))
			{
				String reportId = worksheetJSONObj.getJSONObject("simple-id").getString("uuid");
				tabVisualizationModel.setReportId(reportId);
			}
			
			JSONObject tableJSONObj = worksheetJSONObj.optJSONObject("table");
			
			if (tableJSONObj != null)
			{
				if (tableJSONObj.has("style"))
				{
					System.out.println("table json obj:::" + tableJSONObj);
					JSONObject styleJSONObj = tableJSONObj.optJSONObject("style");
					
					if (styleJSONObj != null)
					{
						List<TableauStyleRule> styleRuleList = fetchTabStyleRuleList(styleJSONObj);
						tabVisualizationModel.setStyleRuleList(styleRuleList);
					}
					
				}
				
				if (tableJSONObj.has("panes"))
				{
					JSONObject panesJSONObj = tableJSONObj.optJSONObject("panes");
					
					if (panesJSONObj != null)
					{
						List<TableauVisualElements> visualElementList = fetchTabVisualElementList(panesJSONObj);
						tabVisualizationModel.setVisualElementList(visualElementList);
					}
					
				}
			}
			
		}
		catch (Exception e)
		{
			System.out.println("visual details extraction exception:::" + e);
		}
		
		return tabVisualizationModel;
	}
	
	private List<TableauVisualElements> fetchTabVisualElementList(JSONObject panesJSONObj) {
		List<TableauVisualElements> visualElementList = new ArrayList<TableauVisualElements>();
		
		if (panesJSONObj.has("pane"))
		{
			try
			{
				TableauVisualElements visualElement = new TableauVisualElements();
				JSONObject paneJSONObj = panesJSONObj.optJSONObject("pane");
				
				if (paneJSONObj != null)
				{
					if (paneJSONObj.has("mark"))
					{
						String chartType = paneJSONObj.getJSONObject("mark").getString("class");
						visualElement.setChartType(chartType);
					}
					visualElementList.add(visualElement);
				}
				else
				{
					JSONArray panesJSONArray = panesJSONObj.getJSONArray("pane");
					
					for (Object paneObj : panesJSONArray)
					{
						JSONObject paneJSONObj1 = (JSONObject) paneObj;
						TableauVisualElements visualElement1 = new TableauVisualElements();
						if (paneJSONObj1.has("mark"))
						{
							String chartType = paneJSONObj1.getJSONObject("mark").getString("class");
							visualElement1.setChartType(chartType);
						}
						visualElementList.add(visualElement1);
					}
				}
				
			}
			catch (Exception e)
			{
				System.out.println("Visual Element List Exception:::" + e);
			}
		}
		
		return visualElementList;
	}
	
	private List<TableauStyleRule> fetchTabStyleRuleList(JSONObject styleJSONObj) {
		List<TableauStyleRule> styleRuleList = new ArrayList<TableauStyleRule>();
		
		if (styleJSONObj.has("style-rule"))
		{
			try
			{
				TableauStyleRule styleRule = new TableauStyleRule();
				JSONObject styleRuleJSONObj = styleJSONObj.optJSONObject("style-rule");
				
				if (styleRuleJSONObj != null)
				{
					if (styleRuleJSONObj.has("element")) 
					{
						String elementName = styleRuleJSONObj.getString("element");
						styleRule.setElementName(elementName);
					}
					
					//Fetching format info
					if (styleRuleJSONObj.has("format"))
					{
						List<TableauFormat> formatList = new ArrayList<TableauFormat>();
						try
						{
							JSONObject formatJSONObj = styleRuleJSONObj.optJSONObject("format");
							
							if (formatJSONObj != null)
							{
								TableauFormat tabFormat = fetchTabFormat(formatJSONObj);
								formatList.add(tabFormat);
							}
							else
							{
								JSONArray formatJSONArray = styleRuleJSONObj.getJSONArray("format");
								
								for (Object fmtObj : formatJSONArray)
								{
									JSONObject formatJSONObj1 = (JSONObject) fmtObj;
									TableauFormat tabFormat1 = fetchTabFormat(formatJSONObj1);
									formatList.add(tabFormat1);
								}
							}
							
						}
						catch (Exception e1)
						{
							System.out.println("Style-rule Format Exception:::" + e1);
						}
						styleRule.setFormatList(formatList);
					}
					
					if (styleRuleJSONObj.has("encoding"))
					{
						List<TableauEncoding> encodingList = new ArrayList<TableauEncoding>();
						try
						{
							JSONObject encodingJSONObj = styleRuleJSONObj.optJSONObject("encoding");
							
							if (encodingJSONObj != null)
							{
								TableauEncoding tabEncoding = fetchTabEncoding(encodingJSONObj);
								encodingList.add(tabEncoding);
							}
							else
							{
								JSONArray encodingJSONArray = styleRuleJSONObj.getJSONArray("encoding");
								
								for (Object encdObj : encodingJSONArray)
								{
									JSONObject encodingJSONObj1 = (JSONObject) encdObj;
									TableauEncoding tabEncoding1 = fetchTabEncoding(encodingJSONObj1);
									encodingList.add(tabEncoding1);
								}
							}
							
						}
						catch (Exception e1)
						{
							System.out.println("Style-rule Encoding Exception:::" + e1);
						}
						styleRule.setEncodingList(encodingList);
					}
					
					styleRuleList.add(styleRule);
				}
				else
				{
					JSONArray styleRuleJSONArray = styleJSONObj.getJSONArray("style-rule");
					
					for (Object styObj : styleRuleJSONArray)
					{
						JSONObject styleRuleJSONObj1 = (JSONObject) styObj;
						TableauStyleRule styleRule1 = new TableauStyleRule();
						
						if (styleRuleJSONObj1.has("element")) 
						{
							String elementName = styleRuleJSONObj1.getString("element");
							styleRule1.setElementName(elementName);
						}
						
						//Fetching format info
						if (styleRuleJSONObj1.has("format"))
						{
							List<TableauFormat> formatList = new ArrayList<TableauFormat>();
							try
							{
								JSONObject formatJSONObj = styleRuleJSONObj1.optJSONObject("format");
								
								if (formatJSONObj != null)
								{
									TableauFormat tabFormat = fetchTabFormat(formatJSONObj);
									formatList.add(tabFormat);
								}
								else
								{
									JSONArray formatJSONArray = styleRuleJSONObj1.getJSONArray("format");
									
									for (Object fmtObj : formatJSONArray)
									{
										JSONObject formatJSONObj1 = (JSONObject) fmtObj;
										TableauFormat tabFormat1 = fetchTabFormat(formatJSONObj1);
										formatList.add(tabFormat1);
									}
								}
								
								
							}
							catch (Exception e1)
							{
								System.out.println("Style-rule Format Exception:::" + e1);
							}
							styleRule1.setFormatList(formatList);
						}
						
						if (styleRuleJSONObj1.has("encoding"))
						{
							List<TableauEncoding> encodingList = new ArrayList<TableauEncoding>();
							try
							{
								JSONObject encodingJSONObj = styleRuleJSONObj1.optJSONObject("encoding");
								
								if (encodingJSONObj != null)
								{
									TableauEncoding tabEncoding = fetchTabEncoding(encodingJSONObj);
									encodingList.add(tabEncoding);
								}
								else
								{
									JSONArray encodingJSONArray = styleRuleJSONObj1.getJSONArray("encoding");
									
									for (Object encdObj : encodingJSONArray)
									{
										JSONObject encodingJSONObj1 = (JSONObject) encdObj;
										TableauEncoding tabEncoding1 = fetchTabEncoding(encodingJSONObj1);
										encodingList.add(tabEncoding1);
									}
								}
								
							}
							catch (Exception e1)
							{
								System.out.println("Style-rule Encoding Exception:::" + e1);
							}
							styleRule1.setEncodingList(encodingList);
						}
						styleRuleList.add(styleRule1);
					}
				}
				
			}
			catch(Exception e)
			{
				System.out.println("Style Rule List Exception:::" + e);
			}
		}
		
		return styleRuleList;
	}
	
	private TableauFormat fetchTabFormat(JSONObject formatJSONObj) {
		TableauFormat tabFormat = new TableauFormat();
		
		if (formatJSONObj.has("attr"))
		{
			String attr = formatJSONObj.getString("attr");
			tabFormat.setAttr(attr);
		}
		if (formatJSONObj.has("field"))
		{
			String field = formatJSONObj.getString("field");
			tabFormat.setField(field);
		}
		if (formatJSONObj.has("value"))
		{
			String value = formatJSONObj.get("value").toString();
			tabFormat.setValue(value);
		}
		if (formatJSONObj.has("data-class"))
		{
			String dataClass = formatJSONObj.getString("data-class");
			tabFormat.setDataclass(dataClass);
		}
		if (formatJSONObj.has("scope"))
		{
			String scope = formatJSONObj.getString("scope");
			tabFormat.setScope(scope);
		}
		
		return tabFormat;
	}
	
	private TableauEncoding fetchTabEncoding(JSONObject encodingJSONObj) {
		TableauEncoding tabEncoding = new TableauEncoding();
		
		if (encodingJSONObj.has("attr"))
		{
			String attr = encodingJSONObj.getString("attr");
			tabEncoding.setAttr(attr);
		}
		if (encodingJSONObj.has("field"))
		{
			String field = encodingJSONObj.getString("field");
			tabEncoding.setField(field);
		}
		if (encodingJSONObj.has("field-type"))
		{
			String fieldType = encodingJSONObj.getString("field-type");
			tabEncoding.setFieldType(fieldType);
		}
		if (encodingJSONObj.has("palette"))
		{
			String palette = encodingJSONObj.getString("palette");
			tabEncoding.setPalette(palette);
		}
		if (encodingJSONObj.has("type"))
		{
			String type = encodingJSONObj.getString("type");
			tabEncoding.setType(type);
		}
		
		return tabEncoding;
	}

	public List<TableauSemanticModel> fetchListOfDataProviders(Set<String> dataSourceNameSet, TableauReportConfigDTO config, boolean isPath) {

		List<TableauSemanticModel> semanticModelList = new ArrayList<TableauSemanticModel>();

		List<String> workbookIdList = config.getWorkbooks();

		try {
			
			if (isPath) {
				
				String pathCheck = config.getPath().substring(config.getPath().length() - 3);
				
				if (pathCheck.equals("twb") || pathCheck.equals("xml"))
				{
					String filePath = config.getPath();
					File file = new File(filePath);
					String inputXML = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
					
					// System.out.println("XML::"+inputXML);

					JSONObject reportJSON = XML.toJSONObject(inputXML);
					// System.out.println("JSON values::::"+reportJSON);

					JSONObject datasource = reportJSON.getJSONObject("workbook").getJSONObject("datasources");

					try {
						JSONObject datasourceJSONObj = datasource.optJSONObject("datasource");
						
						if (datasourceJSONObj != null)
						{
							TableauSemanticModel tabSemanticModel = new TableauSemanticModel();

							String dataSourceName = "";
							if (datasourceJSONObj.has("caption")) {
								dataSourceName = datasourceJSONObj.getString("caption");
							} else if (datasourceJSONObj.has("name")) {
								dataSourceName = datasourceJSONObj.getString("name");
							}

							if (dataSourceNameSet.contains(dataSourceName)) {

								tabSemanticModel = FetchTableauSemanticMetaData(datasourceJSONObj, tabSemanticModel);
								semanticModelList.add(tabSemanticModel);
							}
						}
						else
						{
							JSONArray datasourceJSONArray = datasource.getJSONArray("datasource");

							for (int i = 0; i < datasourceJSONArray.length(); i++) {
								TableauSemanticModel tabSemanticModel1 = new TableauSemanticModel();
								String dataSourceName = "";
								
								if (datasourceJSONArray.getJSONObject(i).has("caption")) {
									dataSourceName = datasourceJSONArray.getJSONObject(i).getString("caption");
								} else if (datasourceJSONArray.getJSONObject(i).has("name")) {
									dataSourceName = datasourceJSONArray.getJSONObject(i).getString("name");
								}

								if (dataSourceNameSet.contains(dataSourceName)) {
									JSONObject datasourceJSONObj1 = datasourceJSONArray.getJSONObject(i);
									tabSemanticModel1 = FetchTableauSemanticMetaData(datasourceJSONObj1, tabSemanticModel1);
									semanticModelList.add(tabSemanticModel1);
								}
							}
						}
						

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				else if (pathCheck.equals("zip"))
				{
					String folderPath = config.getPath().substring(0, config.getPath().length()-3);
					File folder = new File(folderPath);
					
					File[] listOfFiles = folder.listFiles();
					
					for (File file : listOfFiles) {
					    if (file.isFile()) {
					        System.out.println("Absolute File path:: " + file.getAbsolutePath());
					        String filePath = file.getAbsolutePath();
					        File file1 = new File(filePath);
							String inputXML = FileUtils.readFileToString(file1, StandardCharsets.UTF_8);
							
							// System.out.println("XML::"+inputXML);

							JSONObject reportJSON = XML.toJSONObject(inputXML);
							// System.out.println("JSON values::::"+reportJSON);

							JSONObject datasource = reportJSON.getJSONObject("workbook").getJSONObject("datasources");

							try {
								JSONObject datasourceJSONObj = datasource.optJSONObject("datasource");
								
								if (datasourceJSONObj != null)
								{
									TableauSemanticModel tabSemanticModel = new TableauSemanticModel();

									String dataSourceName = "";
									if (datasourceJSONObj.has("caption")) {
										dataSourceName = datasourceJSONObj.getString("caption");
									} else if (datasourceJSONObj.has("name")) {
										dataSourceName = datasourceJSONObj.getString("name");
									}

									if (dataSourceNameSet.contains(dataSourceName)) {

										tabSemanticModel = FetchTableauSemanticMetaData(datasourceJSONObj, tabSemanticModel);
										semanticModelList.add(tabSemanticModel);
									}
								}
								else
								{
									JSONArray datasourceJSONArray = datasource.getJSONArray("datasource");

									for (int i = 0; i < datasourceJSONArray.length(); i++) {
										TableauSemanticModel tabSemanticModel1 = new TableauSemanticModel();
										String dataSourceName = "";
										
										if (datasourceJSONArray.getJSONObject(i).has("caption")) {
											dataSourceName = datasourceJSONArray.getJSONObject(i).getString("caption");
										} else if (datasourceJSONArray.getJSONObject(i).has("name")) {
											dataSourceName = datasourceJSONArray.getJSONObject(i).getString("name");
										}

										if (dataSourceNameSet.contains(dataSourceName)) {
											JSONObject datasourceJSONObj1 = datasourceJSONArray.getJSONObject(i);
											tabSemanticModel1 = FetchTableauSemanticMetaData(datasourceJSONObj1, tabSemanticModel1);
											semanticModelList.add(tabSemanticModel1);
										}
									}
								}
								

							} catch (Exception e) {
								e.printStackTrace();
							}
					        
					    }
					}
					
				}
				
				return semanticModelList;
				
			}
			
			
			for (String workbookId : workbookIdList)
			{
//				String filePath = "D:\\Tableau\\Workbooks\\main-servicenow-report\\LTT product Dashboard migration - Metadata[1].twb";
//				File file = new File(filePath);
//				String inputXML = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
				
				String inputXML = fetchTableauWorkbookTwbAsString(workbookId);

				// System.out.println("XML::"+inputXML);

				JSONObject reportJSON = XML.toJSONObject(inputXML);
				// System.out.println("JSON values::::"+reportJSON);

				JSONObject datasource = reportJSON.getJSONObject("workbook").getJSONObject("datasources");

				try {
					JSONObject datasourceJSONObj = datasource.optJSONObject("datasource");
					
					if (datasourceJSONObj != null)
					{
						TableauSemanticModel tabSemanticModel = new TableauSemanticModel();

						String dataSourceName = "";
						if (datasourceJSONObj.has("caption")) {
							dataSourceName = datasourceJSONObj.getString("caption");
						} else if (datasourceJSONObj.has("name")) {
							dataSourceName = datasourceJSONObj.getString("name");
						}

						if (dataSourceNameSet.contains(dataSourceName)) {

							tabSemanticModel = FetchTableauSemanticMetaData(datasourceJSONObj, tabSemanticModel);
							semanticModelList.add(tabSemanticModel);
						}
					}
					else
					{
						JSONArray datasourceJSONArray = datasource.getJSONArray("datasource");

						for (int i = 0; i < datasourceJSONArray.length(); i++) {
							TableauSemanticModel tabSemanticModel1 = new TableauSemanticModel();
							String dataSourceName = "";
							
							if (datasourceJSONArray.getJSONObject(i).has("caption")) {
								dataSourceName = datasourceJSONArray.getJSONObject(i).getString("caption");
							} else if (datasourceJSONArray.getJSONObject(i).has("name")) {
								dataSourceName = datasourceJSONArray.getJSONObject(i).getString("name");
							}

							if (dataSourceNameSet.contains(dataSourceName)) {
								JSONObject datasourceJSONObj1 = datasourceJSONArray.getJSONObject(i);
								tabSemanticModel1 = FetchTableauSemanticMetaData(datasourceJSONObj1, tabSemanticModel1);
								semanticModelList.add(tabSemanticModel1);
							}
						}
					}

				} catch (Exception j1) {
					j1.printStackTrace();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return semanticModelList;

	}

	private TableauAnalyzerModel FetchTableauReportMetaData(JSONObject worksheetJSONObj,
			TableauAnalyzerModel tabAnalyzerModel) {

		// System.out.println("JSONOBJ::"+worksheetJSONObj);

		String reportName = worksheetJSONObj.getString("name");

		tabAnalyzerModel.setReportName(reportName);
		int rptId = reportId++;
		tabAnalyzerModel.setReportId(String.valueOf(rptId));
		
		//Tableau Universes data
		List<String> universeList = fetchDataSourceNameList(worksheetJSONObj);
		tabAnalyzerModel.setUniverses(universeList);
		
		List<String> columnNamesList = fetchColumnNameList(worksheetJSONObj);
		tabAnalyzerModel.setColumnNameList(columnNamesList);

		// Tableau Report Tab
		List<TableauReportTab> tableauReportList = fetchTableauReportTabDetails(worksheetJSONObj);

		tabAnalyzerModel.setTableauReportTabList(tableauReportList);

		// Tableau Query data

		List<TableauReportQuery> tableauQueryList = fetchTableauQueryTabDetails(worksheetJSONObj);

		tabAnalyzerModel.setTableauReportQueries(tableauQueryList);

		// Tableau Variable Data

		List<TableauVariable> tableauVariableList = fetchTableauVariableDetails(worksheetJSONObj);

		tabAnalyzerModel.setTableauVariableList(tableauVariableList);

		// System.out.println("Report ID:"+tabAnalyzerModel.getReportId()+" No of
		// Forumlas::"+noOfFormulas);

		tabAnalyzerModel.setNumberOfFormulas(noOfFormulas);
		tabAnalyzerModel.setNumberOfFilters(noOfFilters);
		tabAnalyzerModel.setNumberOfColumns(tabAnalyzerModel.getColumnNameList().size());
		// System.out.println("Report ID:"+tabAnalyzerModel.getReportId()+"No Of
		// Filters::"+noOfFilters);

		System.out.println("Report ID:" + tabAnalyzerModel.getReportId() + "No Of columns::"
				+ tabAnalyzerModel.getColumnNameList().size());

		noOfFormulas = 0;
		noOfFilters = 0;
		return tabAnalyzerModel;
	}

	private List<String> fetchDataSourceNameList(JSONObject worksheetJSONObj) {

		List<String> dataSourcesNameList = new ArrayList<>();

		JSONObject dataSourcesJsonObj = worksheetJSONObj.getJSONObject("table").getJSONObject("view")
				.getJSONObject("datasources");

		try {

			JSONObject dataSourceJsonObj = dataSourcesJsonObj.optJSONObject("datasource");
			
			if (dataSourceJsonObj != null)
			{
				if (dataSourceJsonObj.has("caption")) {
					String name = dataSourceJsonObj.getString("caption");
					dataSourcesNameList.add(name);
				} else if (dataSourceJsonObj.has("name")) {
					String name = dataSourceJsonObj.getString("name");
					dataSourcesNameList.add(name);
				}
			}
			else
			{
				JSONArray dataSourceJsonArray = dataSourcesJsonObj.getJSONArray("datasource");
				
				for (Object dsObj : dataSourceJsonArray) {

					JSONObject dataSourceJsonObj1 = (JSONObject) dsObj;
					if (dataSourceJsonObj1.has("caption")) {
						String name = dataSourceJsonObj1.getString("caption");
						dataSourcesNameList.add(name);
					} else if (dataSourceJsonObj1.has("name")) {
						String name = dataSourceJsonObj1.getString("name");
						dataSourcesNameList.add(name);
					}
				}
			}
			
		}
		catch (Exception ex) {

			ex.printStackTrace();
			System.out.println(ex);
		}
		System.out.println("$$$$$$$*********---------DataSourcesName "+ dataSourcesNameList);
		return dataSourcesNameList;
	}

	private TableauSemanticModel FetchTableauSemanticMetaData(JSONObject datasourceJSONObj,
			TableauSemanticModel tabSemanticModel) {

		String dataSourceName = "";
		
		if (datasourceJSONObj.has("caption")) {
			dataSourceName = datasourceJSONObj.getString("caption");
		} else if (datasourceJSONObj.has("name")) {
			dataSourceName = datasourceJSONObj.getString("name");
		}
		
		tabSemanticModel.setName(dataSourceName);
		
		String connectionClass = fetchTableauConnectionClass(datasourceJSONObj);
		tabSemanticModel.setConnectionClass(connectionClass);

		List<TableauTable> tableauTableList = fetchTableauTableList(datasourceJSONObj);
		tabSemanticModel.setTables(tableauTableList);
		System.out.println("tableau table list:::" + tableauTableList);

		Map<String, List<TableauItem>> items = fetchTableauItems(datasourceJSONObj);
		tabSemanticModel.setItems(items);
		System.out.println("tableau item list:::" + items);

		List<TableauFilter> filters = fetchTableauFilters(datasourceJSONObj);
		tabSemanticModel.setFilters(filters);
		System.out.println("tableau filters:::" + filters);

		return tabSemanticModel;
	}
	
	private String fetchTableauConnectionClass(JSONObject datasourceJSONObj) {
		String connectionClass = "";
		
		try {
			if (datasourceJSONObj.has("connection")) {
				JSONObject namedConnectionsJSONObj = datasourceJSONObj.getJSONObject("connection")
						.getJSONObject("named-connections");
				if (namedConnectionsJSONObj.has("named-connection")) {
					try {
						JSONObject namedConnectionJSONObj = namedConnectionsJSONObj.optJSONObject("named-connection");
						
						if (namedConnectionJSONObj != null)
						{
							if (namedConnectionJSONObj.has("connection")) {
								JSONObject connectionJSONObj = namedConnectionJSONObj.optJSONObject("connection");
								
								if (connectionJSONObj != null)
								{
									if (connectionJSONObj.has("class")) {
										connectionClass = connectionJSONObj.getString("class");
									}
								}
								
							}
						}
						else
						{
							JSONArray namedConnectionJSONArray = namedConnectionsJSONObj.getJSONArray("named-connection");
							
							for (Object namedConnObj : namedConnectionJSONArray) {
								JSONObject namedConnectionJSONObj1 = (JSONObject) namedConnObj;
								if (namedConnectionJSONObj1.has("connection")) {
									JSONObject connectionJSONObj1 = namedConnectionJSONObj1.optJSONObject("connection");
									
									if (connectionJSONObj1 != null)
									{
										if (connectionJSONObj1.has("class")) {
											connectionClass = connectionJSONObj1.getString("class");
										}
									}
									
								}
							}
						}
						
					}
					catch (Exception e) {
						System.out.println("Inside named-connection exception:: " + e);
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Inside connection class exception:: " + e);
		}
		
		return connectionClass;
	}

	private List<TableauTable> fetchTableauTableList(JSONObject datasourceJSONObj) {
		List<TableauTable> tabTableList = new ArrayList<TableauTable>();

		try {
			System.out.println("inside try");

			if (datasourceJSONObj.has("connection")) {
				JSONObject metadataRecordsDependency = datasourceJSONObj.getJSONObject("connection")
						.getJSONObject("metadata-records");
				if (metadataRecordsDependency.has("metadata-record")) {
//					List<TableauColumn> tabColumnList = new ArrayList<TableauColumn>();
					try {
						JSONObject metadataRecordObj = metadataRecordsDependency.optJSONObject("metadata-record");
						
						if (metadataRecordObj != null)
						{
							String recordClass = metadataRecordObj.getString("class");

							if (recordClass.equals("column")) {
								String tableName = metadataRecordObj.getString("parent-name");
								
								if (!tabTableList.isEmpty()) {
									int flag = 0;
									for (TableauTable tabDetails : tabTableList)
									{
										String tabDetailsTableName = tabDetails.getName();
										if (tabDetailsTableName.equals(tableName)) {
											flag = 1;
											List<TableauColumn> tabDetailsTableColumns = tabDetails.getColumns();
											List<TableauColumn> tabColumnList = new ArrayList<TableauColumn>();
											
											tabColumnList = fetchTableauColumnList(metadataRecordObj);
											List<TableauColumn> newList = new ArrayList<TableauColumn>(tabDetailsTableColumns);
											newList.addAll(tabColumnList);
											
											tabDetails.setColumns(newList);
										}
									}
									if (flag == 0) {
										List<TableauColumn> tabColumnList = new ArrayList<TableauColumn>();
										TableauTable tabTable = new TableauTable();
										
										tabColumnList = fetchTableauColumnList(metadataRecordObj);
										tabTable.setName(tableName);
										tabTable.setColumns(tabColumnList);
										
										tabTableList.add(tabTable);
									}
								}
								else {
									List<TableauColumn> tabColumnList = new ArrayList<TableauColumn>();
									TableauTable tabTable = new TableauTable();
									
									tabColumnList = fetchTableauColumnList(metadataRecordObj);
									tabTable.setName(tableName);
									tabTable.setColumns(tabColumnList);
									
									tabTableList.add(tabTable);
								}
								
							}
						}
						else
						{
							JSONArray metadataRecordsDependencyArray = datasourceJSONObj.getJSONObject("connection")
									.getJSONObject("metadata-records").getJSONArray("metadata-record");
							
							for (int i = 0; i < metadataRecordsDependencyArray.length(); i++) {
								JSONObject metadataRecordObj1 = metadataRecordsDependencyArray.getJSONObject(i);
								String recordClass = metadataRecordObj1.getString("class");

								if (recordClass.equals("column")) {
									String tableName = metadataRecordObj1.getString("parent-name");
									
									if (!tabTableList.isEmpty()) {
										int flag = 0;
										for (TableauTable tabDetails : tabTableList)
										{
											String tabDetailsTableName = tabDetails.getName();
											if (tabDetailsTableName.equals(tableName)) {
												flag = 1;
												List<TableauColumn> tabDetailsTableColumns = tabDetails.getColumns();
												List<TableauColumn> tabColumnList = new ArrayList<TableauColumn>();
												
												tabColumnList = fetchTableauColumnList(metadataRecordObj1);
												List<TableauColumn> newList = new ArrayList<TableauColumn>(tabDetailsTableColumns);
												newList.addAll(tabColumnList);
												
												tabDetails.setColumns(newList);
											}
										}
										if (flag == 0) {
											List<TableauColumn> tabColumnList = new ArrayList<TableauColumn>();
											TableauTable tabTable = new TableauTable();
											
											tabColumnList = fetchTableauColumnList(metadataRecordObj1);
											tabTable.setName(tableName);
											tabTable.setColumns(tabColumnList);
											
											tabTableList.add(tabTable);
										}
									}
									else {
										List<TableauColumn> tabColumnList = new ArrayList<TableauColumn>();
										TableauTable tabTable = new TableauTable();
										
										tabColumnList = fetchTableauColumnList(metadataRecordObj1);
										tabTable.setName(tableName);
										tabTable.setColumns(tabColumnList);
										
										tabTableList.add(tabTable);
									}
								}

							}
						}

					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}

		} catch (Exception e) {
			System.out.println("inside catch exception::: " + e);
		}

//		System.out.println("tab table list:::" + tabTableList);

		return tabTableList;
	}

	private List<TableauColumn> fetchTableauColumnList(JSONObject metadataRecordObj) {
		List<TableauColumn> tabColumnList = new ArrayList<TableauColumn>();
		TableauColumn tabColumn = new TableauColumn();

		try {
			if (metadataRecordObj.has("local-name")) {
				String localName = metadataRecordObj.getString("local-name");
				tabColumn.setName(localName);
			}
			if (metadataRecordObj.has("local-type")) {
				String localType = metadataRecordObj.getString("local-type");
				tabColumn.setDataType(localType);
			}
		} catch (Exception e) {
			System.out.println("Tableau Column Exception:::" + e);
		}

		tabColumnList.add(tabColumn);

//		System.out.println("tab column list:::" + tabColumnList);

		return tabColumnList;
	}

	private Map<String, List<TableauItem>> fetchTableauItems(JSONObject datasourceJSONObj) {
		Map<String, List<TableauItem>> items = new HashMap<String, List<TableauItem>>();
//		List<TableauItem> tabItemList = new ArrayList<TableauItem>();

//		List<TableauItem> measureItemList = new ArrayList<TableauItem>();

		if (datasourceJSONObj.has("column")) {
			try {
				JSONObject columnJSONObj = datasourceJSONObj.optJSONObject("column");
				
				if (columnJSONObj != null)
				{
					items = fetchTableauItemList(columnJSONObj, items);
				}
				else
				{
					JSONArray columnJSONArray = datasourceJSONObj.getJSONArray("column");

					for (Object colJSONObj : columnJSONArray) {
						JSONObject cc = (JSONObject) colJSONObj;
						items = fetchTableauItemList(cc, items);
					}
				}
				
			}
			catch (Exception e) {
				System.out.println("Column Exception:::" + e);
			}
		}

		if (datasourceJSONObj.has("folder")) {
			try {
				JSONObject folderJSONObj = datasourceJSONObj.optJSONObject("folder");
				
				if (folderJSONObj != null)
				{
					items = fetchTableauFolderList(folderJSONObj, items);
				}
				else
				{
					JSONArray folderJSONArray = datasourceJSONObj.getJSONArray("folder");

					for (Object folJSONObj : folderJSONArray) {
						JSONObject folderJSONObj1 = (JSONObject) folJSONObj;
						items = fetchTableauFolderList(folderJSONObj1, items);
					}
				}
				
			} catch (Exception e) {
				System.out.println("Folder Exception:::" + e);
			}
		}

		return items;
	}

	private Map<String, List<TableauItem>> fetchTableauItemList(JSONObject columnJSONObj,
			Map<String, List<TableauItem>> items) {

		try {
			if (columnJSONObj.has("role")) {
				String role = columnJSONObj.getString("role");

				if (role.equals("measure")) {
					if (items.containsKey("measures")) {
						List<TableauItem> tabItemMap = items.get("measures");

						TableauItem tabItem = new TableauItem();

						if (columnJSONObj.has("name")) {
							String name = columnJSONObj.getString("name");
							tabItem.setName(name);
						}
						if (columnJSONObj.has("datatype")) {
							String datatype = columnJSONObj.getString("datatype");
							tabItem.setDataType(datatype);
						}
						if (columnJSONObj.has("calculation")) {
							JSONObject calculationJSONObj = columnJSONObj.optJSONObject("calculation");
							
							if (calculationJSONObj != null)
							{
								if (calculationJSONObj.has("formula")) {
									String formula = columnJSONObj.getJSONObject("calculation").get("formula").toString();
									tabItem.setFormula(formula);
								}
							}
							
						}
						if (columnJSONObj.has("type")) {
							String type = columnJSONObj.getString("type");
							tabItem.setType(type);
						}

						tabItemMap.add(tabItem);

						items.put("measures", tabItemMap);

					} else {

						List<TableauItem> tabItemMap = new ArrayList<TableauItem>();

						TableauItem tabItem = new TableauItem();

						if (columnJSONObj.has("name")) {
							String name = columnJSONObj.getString("name");
							tabItem.setName(name);
						}
						if (columnJSONObj.has("datatype")) {
							String datatype = columnJSONObj.getString("datatype");
							tabItem.setDataType(datatype);
						}
						if (columnJSONObj.has("calculation")) {
							JSONObject calculationJSONObj = columnJSONObj.optJSONObject("calculation");
							
							if (calculationJSONObj != null)
							{
								if (calculationJSONObj.has("formula")) {
									String formula = columnJSONObj.getJSONObject("calculation").get("formula").toString();
									tabItem.setFormula(formula);
								}
							}
							
						}
						if (columnJSONObj.has("type")) {
							String type = columnJSONObj.getString("type");
							tabItem.setType(type);
						}

						tabItemMap.add(tabItem);

						items.put("measures", tabItemMap);

					}
				} else if (role.equals("dimension")) {
					if (items.containsKey("dimensions")) {
						List<TableauItem> tabItemMap = items.get("dimensions");

						TableauItem tabItem = new TableauItem();

						if (columnJSONObj.has("name")) {
							String name = columnJSONObj.getString("name");
							tabItem.setName(name);
						}
						if (columnJSONObj.has("datatype")) {
							String datatype = columnJSONObj.getString("datatype");
							tabItem.setDataType(datatype);
						}
						if (columnJSONObj.has("calculation")) {
							JSONObject calculationJSONObj = columnJSONObj.optJSONObject("calculation");
							
							if (calculationJSONObj != null)
							{
								if (calculationJSONObj.has("formula")) {
									String formula = columnJSONObj.getJSONObject("calculation").get("formula").toString();
									tabItem.setFormula(formula);
								}
							}
							
						}
						if (columnJSONObj.has("type")) {
							String type = columnJSONObj.getString("type");
							tabItem.setType(type);
						}

						tabItemMap.add(tabItem);

						items.put("dimensions", tabItemMap);
					} else {
						List<TableauItem> tabItemMap = new ArrayList<TableauItem>();

						TableauItem tabItem = new TableauItem();

						if (columnJSONObj.has("name")) {
							String name = columnJSONObj.getString("name");
							tabItem.setName(name);
						}
						if (columnJSONObj.has("datatype")) {
							String datatype = columnJSONObj.getString("datatype");
							tabItem.setDataType(datatype);
						}
						if (columnJSONObj.has("calculation")) {
							JSONObject calculationJSONObj = columnJSONObj.optJSONObject("calculation");
							
							if (calculationJSONObj != null)
							{
								if (calculationJSONObj.has("formula")) {
									String formula = columnJSONObj.getJSONObject("calculation").get("formula").toString();
									tabItem.setFormula(formula);
								}
							}
							
						}
						if (columnJSONObj.has("type")) {
							String type = columnJSONObj.getString("type");
							tabItem.setType(type);
						}

						tabItemMap.add(tabItem);

						items.put("dimensions", tabItemMap);
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Exception:::" + e);
		}

//		System.out.println("items:::" + items);

		return items;
	}

	private Map<String, List<TableauItem>> fetchTableauFolderList(JSONObject folderJSONObj,
			Map<String, List<TableauItem>> items) {

		List<TableauItem> tabItems = new ArrayList<TableauItem>();

		if (folderJSONObj.has("folder-item")) {
			try {
				TableauItem tabItem = new TableauItem();
				JSONObject folderItemJSONObj = folderJSONObj.optJSONObject("folder-item");
				
				if (folderItemJSONObj != null)
				{
					Map<String, String> folderItemsMap = fetchTableauFolderItemList(folderItemJSONObj);

					String itemName = folderItemsMap.get("itemName");
					String itemType = folderItemsMap.get("itemType");
					String folderName = folderJSONObj.getString("name");

					tabItem.setName(itemName);
					tabItem.setType(itemType);
					tabItem.setPath(folderName);

					tabItems.add(tabItem);
				}
				else
				{
					JSONArray folderItemJSONArray = folderJSONObj.getJSONArray("folder-item");

					for (Object folItemJSONObj : folderItemJSONArray) {
						TableauItem tabItem1 = new TableauItem();
						JSONObject folderItemJSONObj1 = (JSONObject) folItemJSONObj;
						Map<String, String> folderItemsMap = fetchTableauFolderItemList(folderItemJSONObj1);

						String itemName = folderItemsMap.get("itemName");
						String itemType = folderItemsMap.get("itemType");
						String folderName = folderJSONObj.getString("name");

						tabItem1.setName(itemName);
						tabItem1.setType(itemType);
						tabItem1.setPath(folderName);

						tabItems.add(tabItem1);
					}
				}

			} catch (Exception e) {
				System.out.println("Folder List Exception:::" + e);
			}
		}

		if (items.containsKey("folders")) {
			List<TableauItem> prevTabItems = items.get("folders");
			List<TableauItem> newList = new ArrayList<TableauItem>();

			newList.addAll(prevTabItems);
			newList.addAll(tabItems);

			items.put("folders", newList);
		} else {
			items.put("folders", tabItems);
		}

		return items;

	}

	private Map<String, String> fetchTableauFolderItemList(JSONObject folderItemJSONObj) {
		Map<String, String> folderItemsMap = new HashMap<String, String>();

		if (folderItemJSONObj.has("name")) {
			String itemName = folderItemJSONObj.getString("name");
			folderItemsMap.put("itemName", itemName);
		}
		if (folderItemJSONObj.has("type")) {
			String itemType = folderItemJSONObj.getString("type");
			folderItemsMap.put("itemType", itemType);
		}

		return folderItemsMap;
	}

	private List<TableauFilter> fetchTableauFilters(JSONObject datasourceJSONObj) {
		List<TableauFilter> tabFilters = new ArrayList<TableauFilter>();

		if (datasourceJSONObj.has("group")) {
			try {
				JSONObject groupJSONObj = datasourceJSONObj.optJSONObject("group");
				
				if (groupJSONObj != null) {
					TableauFilter tabFilter = new TableauFilter();

					String tabFilterName = groupJSONObj.getString("name");
					tabFilter.setName(tabFilterName);

					JSONObject groupFilterJSONObj = groupJSONObj.optJSONObject("groupfilter");

					if (groupFilterJSONObj != null)
					{
						String tabFilterFunctionName = groupFilterJSONObj.getString("function");
						tabFilter.setFunction(tabFilterFunctionName);

						List<String> tabFilterMembers = fetchTableauFilterMembers(groupFilterJSONObj);
						tabFilter.setMembers(tabFilterMembers);
					}

					tabFilters.add(tabFilter);
				}
				else
				{
					JSONArray groupJSONArray = datasourceJSONObj.getJSONArray("group");

					for (Object gpJSONObj : groupJSONArray) {
						JSONObject groupJSONObj1 = (JSONObject) gpJSONObj;
						TableauFilter tabFilter = new TableauFilter();

						String tabFilterName = groupJSONObj1.getString("name");
						tabFilter.setName(tabFilterName);

						JSONObject groupFilterJSONObj = groupJSONObj1.optJSONObject("groupfilter");

						if (groupFilterJSONObj != null)
						{
							String tabFilterFunctionName = groupFilterJSONObj.getString("function");
							tabFilter.setFunction(tabFilterFunctionName);

							List<String> tabFilterMembers = fetchTableauFilterMembers(groupFilterJSONObj);
							tabFilter.setMembers(tabFilterMembers);
						}

						tabFilters.add(tabFilter);
					}
				}

			} catch (Exception e) {
				System.out.println("Filters Exception:::" + e);
			}

		}

		return tabFilters;
	}

	private List<String> fetchTableauFilterMembers(JSONObject groupFilterJSONObj) {
		List<String> tabFilterMembers = new ArrayList<String>();

		if (groupFilterJSONObj.has("groupfilter")) {
			try {
				JSONObject gpFilterJSONObj = groupFilterJSONObj.optJSONObject("groupfilter");
				if (gpFilterJSONObj != null) {
					String filterMember = gpFilterJSONObj.getString("level");
					tabFilterMembers.add(filterMember);
				}
				else
				{
					JSONArray gpFilterJSONArray = groupFilterJSONObj.getJSONArray("groupfilter");

					for (Object gpFilterObj : gpFilterJSONArray) {
						JSONObject gpFilterMemberJSONObj1 = (JSONObject) gpFilterObj;
						
						if (gpFilterMemberJSONObj1.has("level")) {
							String filterMember = gpFilterMemberJSONObj1.getString("level");
							tabFilterMembers.add(filterMember);
						}
					}
				}
			} catch (Exception e) {
				System.out.println("Filter Members Exception:::" + e);
			}
		}

		return tabFilterMembers;
	}

	private List<String> fetchColumnNameList(JSONObject worksheetJSONObj) {
		// TODO Auto-generated method stub
		List<String> columnList = new ArrayList<String>();

		try {
			JSONObject dataSourceDependency = worksheetJSONObj.getJSONObject("table").getJSONObject("view")
					.getJSONObject("datasource-dependencies");

			columnList = fetchColumnsData(dataSourceDependency, columnList);

		} catch (JSONException j2) {
			// j2.printStackTrace();
			JSONArray dataSourceDependencyArray = worksheetJSONObj.getJSONObject("table").getJSONObject("view")
					.getJSONArray("datasource-dependencies");

			for (int i = 0; i < dataSourceDependencyArray.length(); i++) {
				JSONObject dataSourceDependency = dataSourceDependencyArray.getJSONObject(i);
				columnList = fetchColumnsData(dataSourceDependency, columnList);

			}

		}

		return columnList;
	}

	private List<String> fetchColumnsData(JSONObject dataSourceDependency, List<String> columnList) {
		// TODO Auto-generated method stub
		try {
			JSONObject columnJSONObj = dataSourceDependency.getJSONObject("column");
			String name = columnJSONObj.getString("name");
			columnList.add(name);
		} catch (JSONException j3) {
			// j3.printStackTrace();
			// System.out.println("Array::");
			JSONArray columnJSONArray = dataSourceDependency.getJSONArray("column");
			for (int i = 0; i < columnJSONArray.length(); i++) {
				JSONObject columnJSONObj = columnJSONArray.getJSONObject(i);
				// System.out.println("Column JSON::"+columnJSONObj);
				String name = columnJSONObj.getString("name");
				columnList.add(name);
			}
		}

		return columnList;
	}

	private List<TableauReportTab> fetchTableauReportTabDetails(JSONObject worksheetJSONObj) {
		// TODO Auto-generated method stub

		List<TableauReportTab> tabList = new ArrayList<TableauReportTab>();
		TableauReportTab tableauReportTabObj = new TableauReportTab();
		
		tabList.add(tableauReportTabObj);

		JSONObject viewJSONObj = worksheetJSONObj.getJSONObject("table").getJSONObject("view");
		if (viewJSONObj.has("filter")) {
			List<QueryFilter> queryFilterList = new ArrayList<QueryFilter>();
			try {
				JSONObject filterJSONObj = viewJSONObj.optJSONObject("filter");

				if (filterJSONObj != null)
				{
					QueryFilter queryFilterObj = FetchQueryFilterData(filterJSONObj);

					queryFilterList.add(queryFilterObj);

					tableauReportTabObj.setQueryFilters(queryFilterList);
				}
				else
				{
					JSONArray filterJSONArray = worksheetJSONObj.getJSONObject("table").getJSONObject("view")
							.getJSONArray("filter");

					for (int i = 0; i < filterJSONArray.length(); i++) {
						JSONObject filterJSONObj1 = filterJSONArray.getJSONObject(i);

						QueryFilter queryFilterObj = FetchQueryFilterData(filterJSONObj1);

						queryFilterList.add(queryFilterObj);
						tableauReportTabObj.setQueryFilters(queryFilterList);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			noOfFilters = queryFilterList.size();
		}

		// Extraction of Table Element
		List<TableauTableElement> tableauTableList = new ArrayList<TableauTableElement>();
		try {
			JSONObject dataSourceJSONObj = viewJSONObj.getJSONObject("datasources").optJSONObject("datasource");
			
			if (dataSourceJSONObj != null)
			{
				TableauTableElement tabTableElementObj = fetchTableElementDetails(dataSourceJSONObj, viewJSONObj);
				tableauTableList.add(tabTableElementObj);
				tableauReportTabObj.setTableauTableElements(tableauTableList);
			}
			else
			{
				JSONArray dataSourceJSONArray = viewJSONObj.getJSONObject("datasources").getJSONArray("datasource");
				
				for (int i = 0; i < dataSourceJSONArray.length(); i++) {
					JSONObject dataSourceJSONObj1 = dataSourceJSONArray.getJSONObject(i);

					TableauTableElement tabTableElementObj = fetchTableElementDetails(dataSourceJSONObj1, viewJSONObj);
					tableauTableList.add(tabTableElementObj);
					tableauReportTabObj.setTableauTableElements(tableauTableList);

				}
			}

		}
		catch(Exception e) {
			System.out.println("fetch report tab exception:::" + e);
		}

		tabList.add(tableauReportTabObj);

		return tabList;
	}

	private List<TableauVisualElements> fetchTableauVisualElementsList(JSONObject styleRuleJSONObj) {

		List<TableauVisualElements> tableauVisualElementsList = new ArrayList<>();
		TableauVisualElements tableauVisualElementsObj = new TableauVisualElements();

		try {

			tableauVisualElementsObj = fetchTableauVisualElements(styleRuleJSONObj);
			tableauVisualElementsList.add(tableauVisualElementsObj);

		} catch (JSONException j2) {
			// j2.printStackTrace();
			JSONArray styleRuleJsonArray = styleRuleJSONObj.getJSONObject("table").getJSONObject("style")
					.getJSONArray("style-rule");
			for (int i = 0; i < styleRuleJsonArray.length(); i++) {
				JSONObject styleRuleJsonObj = styleRuleJsonArray.getJSONObject(i);
				tableauVisualElementsObj = fetchTableauVisualElements(styleRuleJsonObj);
				tableauVisualElementsList.add(tableauVisualElementsObj);				
			}
		}
		return tableauVisualElementsList;
		
	}

 
 private TableauEncoding fetchTabStyleRuleEncodeData(JSONObject tablEncodeJsonObj) {
		// TODO Auto-generated method stub

		TableauEncoding tableauEncodingObj = new TableauEncoding(); 
		if (tablEncodeJsonObj.has("attr")) {
			String attr = tablEncodeJsonObj.getString("attr");
			tableauEncodingObj.setAttr(attr);
		}
		if (tablEncodeJsonObj.has("field")) {
			String field = tablEncodeJsonObj.getString("field");
			tableauEncodingObj.setField(field);
		}
		if (tablEncodeJsonObj.has("palette")) { 
			String palette = tablEncodeJsonObj.getString("palette");
			tableauEncodingObj.setPalette(palette);
		}

		if (tablEncodeJsonObj.has("type")) {
			String type = tablEncodeJsonObj.getString("type");
			tableauEncodingObj.setType(type);
		}

		return tableauEncodingObj;
	}

 private TableauVisualElements fetchTableauVisualElements(JSONObject styleRuleJSONObj) {

			TableauVisualElements tablVisualElmtsObj = new TableauVisualElements(); 
			if (styleRuleJSONObj.has("element")) {			
				String element = styleRuleJSONObj.getString("element");
				
				if(element.equals("type")) {
				tablVisualElmtsObj.setType(element);
				}
				else if(element.equals("category")) {
					tablVisualElmtsObj.setCategory(element);
				}
				else if(element.equals("elementName")) {
					tablVisualElmtsObj.setElementName(element);
				}
				else if(element.equals("elementId")) {
					tablVisualElmtsObj.setElementId(element);
				}
				else if(element.equals("parentId")) {
					tablVisualElmtsObj.setParentId(element);
				}
				else if(element.equals("alwaysFlag")) {
					tablVisualElmtsObj.setAlwaysFlag(element);
				}
				else if(element.equals("xPosition")) {
					tablVisualElmtsObj.setxPosition(element);
				}
				else if(element.equals("yPosition")) {
					tablVisualElmtsObj.setyPosition(element);
				}
				else if(element.equals("minimalWidth")) {
					tablVisualElmtsObj.setMinimalWidth(element);
				}
				else if(element.equals("minimalHeight")) {
					tablVisualElmtsObj.setMinimalHeight(element);
				}
				else if(element.equals("backgroundColor")) {
					tablVisualElmtsObj.setBackgroundColor(element);
				}
				else if(element.equals("backgroundColorAlpha")) {
					tablVisualElmtsObj.setBackgroundColorAlpha(element);
				}
				else if(element.equals("border")) {
					tablVisualElmtsObj.setBorder(element);
				}
				else if(element.equals("font")) {
					tablVisualElmtsObj.setFont(element);
				}
				else if(element.equals("allignment")) {
					tablVisualElmtsObj.setAllignment(element);
				}
				else if(element.equals("formula")) {
					tablVisualElmtsObj.setFormula(element);
				}
				else if(element.equals("chartType")) {
					tablVisualElmtsObj.setChartType(element);
				}
				else if(element.equals("chartName")) {
					tablVisualElmtsObj.setChartName(element);
				}
				else if(element.equals("chartTitle")) {
					tablVisualElmtsObj.setChartTitle(element);
				}
				else if(element.equals("chartLegend")) {
					tablVisualElmtsObj.setChartLegend(element);
				}
				else if(element.equals("chartPlotArea")) {
					tablVisualElmtsObj.setChartPlotArea(element);
				}
				else if(element.equals("chartLegend")) {
					tablVisualElmtsObj.setChartLegend(element);
				}
				else if(element.equals("chartAxes")) {
					List<Axes> axesList = new ArrayList();					
					tablVisualElmtsObj.setChartAxes(axesList);  // It's a List 
				}
				else if(element.equals("maxWidth")) {
					tablVisualElmtsObj.setMaxWidth(element);
				}
				else if(element.equals("maxHeight")) {
					tablVisualElmtsObj.setMaxHeight(element);
				}
			}
			
		return tablVisualElmtsObj;
	}


	private QueryFilter FetchQueryFilterData(JSONObject filterJSONObj) {
		// TODO Auto-generated method stub
		QueryFilter queryFilterObj = new QueryFilter();
		if (filterJSONObj.has("column")) {
			String column = filterJSONObj.getString("column");
			queryFilterObj.setColumn(column);
		}
		if (filterJSONObj.has("class")) {
			String className = filterJSONObj.getString("class");
			queryFilterObj.setClassName(className);
		}
		if (filterJSONObj.has("context")) {
			String context = String.valueOf(filterJSONObj.getBoolean("context"));
			queryFilterObj.setContext(context);
		}
		if (filterJSONObj.has("filter-group")) {
			String filterGroup = String.valueOf(filterJSONObj.getInt("filter-group"));
			queryFilterObj.setFilterGroup(filterGroup);
		}
		if (filterJSONObj.has("kind")) {
			String kind = filterJSONObj.getString("kind");
			queryFilterObj.setKind(kind);
		}
		if (filterJSONObj.has("level")) {
			String filterLevel = filterJSONObj.getString("level");
			queryFilterObj.setColumn(filterLevel);
		}
		if (filterJSONObj.has("user:ui-enumeration")) {
			String filterUserUiEnumearation = filterJSONObj.getString("user:ui-enumeration");
			queryFilterObj.setClassName(filterUserUiEnumearation);
		}
		if (filterJSONObj.has("user:ui-marker")) {
			String filterUserUiMarker = String.valueOf(filterJSONObj.getString("user:ui-marker"));
			queryFilterObj.setContext(filterUserUiMarker);
		}
		if (filterJSONObj.has("included-values")) {
			String filterIncludedValues = String.valueOf(filterJSONObj.getString("included-values"));
			queryFilterObj.setFilterGroup(filterIncludedValues);
		}
		if (filterJSONObj.has("user:ui-action-filter")) {
			String filterUserUiAction = filterJSONObj.getString("user:ui-action-filter");
			queryFilterObj.setKind(filterUserUiAction);
		}

		return queryFilterObj;
	}

	private TableauTableElement fetchTableElementDetails(JSONObject dataSourceJSONObj, JSONObject viewJSONObj) {
		// TODO Auto-generated method stub
		TableauTableElement tabTableElement = new TableauTableElement();

		// System.out.println("Data Source JSON::"+dataSourceJSONObj);

		if (dataSourceJSONObj.has("name")) {
			String name = dataSourceJSONObj.getString("name");
			tabTableElement.setTableName(name);
		}
		if (dataSourceJSONObj.has("caption")) {
			String caption = dataSourceJSONObj.getString("caption");
			tabTableElement.setCaption(caption);
		}

		try {
			JSONObject dataSourceDependenciesJSONObj = viewJSONObj.optJSONObject("datasource-dependencies");

			if (dataSourceDependenciesJSONObj != null)
			{
				String dataSourceName = dataSourceDependenciesJSONObj.getString("datasource");
				if (dataSourceName.equalsIgnoreCase(tabTableElement.getTableName())) {
					// System.out.println("Data Source
					// name::"+dataSourceDependenciesJSONObj.getString("datasource"));

					List<TableauReportColumn> columnList = fetchTableColumnList(dataSourceDependenciesJSONObj);
					tabTableElement.setColumnList(columnList);
					tabTableElement.setNumberOfColumns(columnList.size());

					List<TableauColumnInstance> columnInstanceList = fetchColumnInstanceList(dataSourceDependenciesJSONObj);
					tabTableElement.setColumnInstance(columnInstanceList);

				}
			}
			else
			{
				JSONArray dataSourceDependenciesJSONArray = viewJSONObj.getJSONArray("datasource-dependencies");
				
				for (int i = 0; i < dataSourceDependenciesJSONArray.length(); i++) {
					JSONObject dataSourceDependenciesJSONObj1 = dataSourceDependenciesJSONArray.getJSONObject(i);
					String dataSourceName = dataSourceDependenciesJSONObj1.getString("datasource");
					if (dataSourceName.equalsIgnoreCase(tabTableElement.getTableName())) {
						// System.out.println("Data Source name
						// Array::"+dataSourceDependenciesJSONObj.getString("datasource"));
						List<TableauReportColumn> columnList = fetchTableColumnList(dataSourceDependenciesJSONObj1);
						tabTableElement.setColumnList(columnList);
						tabTableElement.setNumberOfColumns(columnList.size());

						List<TableauColumnInstance> columnInstanceList = fetchColumnInstanceList(
								dataSourceDependenciesJSONObj1);
						tabTableElement.setColumnInstance(columnInstanceList);
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return tabTableElement;

	}

	private List<TableauReportColumn> fetchTableColumnList(JSONObject dataSourceDependenciesJSONObj) {
		// TODO Auto-generated method stub

		List<TableauReportColumn> colList = new ArrayList<TableauReportColumn>();

		try {
			JSONObject columnJSONObj = dataSourceDependenciesJSONObj.optJSONObject("column");

			if(columnJSONObj != null) 
			{
				TableauReportColumn tabReportColumn = new TableauReportColumn();
				if (columnJSONObj.has("name")) {
					String columnExpression = columnJSONObj.getString("name");
					tabReportColumn.setColumnExpression(columnExpression);
				}
				if (columnJSONObj.has("datatype")) {
					String dataType = columnJSONObj.getString("datatype");
					tabReportColumn.setColumnDataType(dataType);
				}
				if (columnJSONObj.has("role")) {
					String colRole = columnJSONObj.getString("role");
					tabReportColumn.setColumnExpression(colRole);
				}
				if (columnJSONObj.has("semantic-role")) {
					String colSemanticRole = columnJSONObj.getString("semantic-role");
					tabReportColumn.setColumnExpression(colSemanticRole);
				}

				if (columnJSONObj.has("caption")) {
					String colCaption = columnJSONObj.get("caption").toString();
					tabReportColumn.setColumnExpression(colCaption);
				}
				if (columnJSONObj.has("type")) {
					String colType = columnJSONObj.getString("type");
					tabReportColumn.setColumnExpression(colType);
				}
				if (columnJSONObj.has("default-format")) {
					String colDefaultFormat = columnJSONObj.getString("default-format");
					tabReportColumn.setColumnExpression(colDefaultFormat);
				}

				colList.add(tabReportColumn);
			}
			else
			{
				JSONArray columnJSONArray = dataSourceDependenciesJSONObj.getJSONArray("column");

				for (int i = 0; i < columnJSONArray.length(); i++) {
					JSONObject columnJSONObj1 = columnJSONArray.getJSONObject(i);

					TableauReportColumn tabReportColumn = new TableauReportColumn();
					if (columnJSONObj1.has("name")) {
						String columnExpression = columnJSONObj1.getString("name");
						tabReportColumn.setColumnExpression(columnExpression);
					}
					if (columnJSONObj1.has("datatype")) {
						String dataType = columnJSONObj1.getString("datatype");
						tabReportColumn.setColumnDataType(dataType);
					}
					if (columnJSONObj1.has("role")) {
						String colRole = columnJSONObj1.getString("role");
						tabReportColumn.setColumnExpression(colRole);
					}
					if (columnJSONObj1.has("semantic-role")) {
						String colSemanticRole = columnJSONObj1.getString("semantic-role");
						tabReportColumn.setColumnExpression(colSemanticRole);
					}

					if (columnJSONObj1.has("caption")) {
						String colCaption = columnJSONObj1.get("caption").toString();
						tabReportColumn.setColumnExpression(colCaption);
					}
					if (columnJSONObj1.has("type")) {
						String colType = columnJSONObj1.getString("type");
						tabReportColumn.setColumnExpression(colType);
					}
					if (columnJSONObj1.has("default-format")) {
						String colDefaultFormat = columnJSONObj1.getString("default-format");
						tabReportColumn.setColumnExpression(colDefaultFormat);
					}

					colList.add(tabReportColumn);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return colList;

	}

	private List<TableauColumnInstance> fetchColumnInstanceList(JSONObject dataSourceDependenciesJSONObj) {
		// TODO Auto-generated method stub

		List<TableauColumnInstance> colList = new ArrayList<TableauColumnInstance>();

		if (dataSourceDependenciesJSONObj.has("column-instance")) {
			try {
				JSONObject columnInstanceJSONObj = dataSourceDependenciesJSONObj.optJSONObject("column-instance");

				if (columnInstanceJSONObj != null)
				{
					TableauColumnInstance columnInstanceObj = setColumnInstance(columnInstanceJSONObj);
					colList.add(columnInstanceObj);
				}
				else
				{
					JSONArray columnInstanceJSONArray = dataSourceDependenciesJSONObj.getJSONArray("column-instance");
					
					for (int i = 0; i < columnInstanceJSONArray.length(); i++) {
						JSONObject columnInstanceJSONObj1 = columnInstanceJSONArray.getJSONObject(i);

						TableauColumnInstance columnInstanceObj = setColumnInstance(columnInstanceJSONObj1);
						colList.add(columnInstanceObj);
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return colList;
	}

	private TableauColumnInstance setColumnInstance(JSONObject columnInstanceJSONObj) {
		// TODO Auto-generated method stub

		TableauColumnInstance columnInstanceObj = new TableauColumnInstance();

		if (columnInstanceJSONObj.has("column")) {
			String column = columnInstanceJSONObj.getString("column");
			columnInstanceObj.setColumn(column);
		}
		if (columnInstanceJSONObj.has("derivation")) {
			String derivation = columnInstanceJSONObj.getString("derivation");
			columnInstanceObj.setDerivation(derivation);
		}
		if (columnInstanceJSONObj.has("name")) {
			String columnName = columnInstanceJSONObj.getString("name");
			columnInstanceObj.setColumnName(columnName);
		}
		if (columnInstanceJSONObj.has("pivot")) {
			String pivot = columnInstanceJSONObj.getString("pivot");
			columnInstanceObj.setPivot(pivot);
		}
		if (columnInstanceJSONObj.has("type")) {
			String type = columnInstanceJSONObj.getString("type");
			columnInstanceObj.setType(type);
		}

		return columnInstanceObj;

	}

	// Tableau Query List Data

	private List<TableauReportQuery> fetchTableauQueryTabDetails(JSONObject worksheetJSONObj) {
		// TODO Auto-generated method stub

		List<TableauReportQuery> queryReportList = new ArrayList<TableauReportQuery>();
		TableauReportQuery tableauQueryTabObj = new TableauReportQuery();

		JSONObject viewJSONObj = worksheetJSONObj.getJSONObject("table").getJSONObject("view");

		try {
			JSONObject dataSourceDependenciesJSONObj = viewJSONObj.optJSONObject("datasource-dependencies");

			if (dataSourceDependenciesJSONObj != null)
			{
				List<TableauReportQueryColumn> tabReportQueryColumns = fetchColumnDetailsForQuery(
						dataSourceDependenciesJSONObj);
				tableauQueryTabObj.setTabReportQueryColumns(tabReportQueryColumns);
			}
			else
			{
				JSONArray dataSourceDependenciesJSONArray = viewJSONObj.getJSONArray("datasource-dependencies");

				for (int i = 0; i < dataSourceDependenciesJSONArray.length(); i++) {
					JSONObject dataSourceDependenciesJSONObj1 = dataSourceDependenciesJSONArray.getJSONObject(i);
					List<TableauReportQueryColumn> tabReportQueryColumns = fetchColumnDetailsForQuery(
							dataSourceDependenciesJSONObj1);
					tableauQueryTabObj.setTabReportQueryColumns(tabReportQueryColumns);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		queryReportList.add(tableauQueryTabObj);

		return queryReportList;
	}

	private List<TableauReportQueryColumn> fetchColumnDetailsForQuery(JSONObject dataSourceDependenciesJSONObj) {
		// TODO Auto-generated method stub

		// System.out.println("1111");
		List<TableauReportQueryColumn> queryColumnList = new ArrayList<TableauReportQueryColumn>();

		try {
			JSONObject columnJSONObj = dataSourceDependenciesJSONObj.optJSONObject("column");

			if (columnJSONObj != null)
			{
				TableauReportQueryColumn tabReportQueryColumn = setColumnQueryValues(columnJSONObj);

				queryColumnList.add(tabReportQueryColumn);
			}
			else
			{
				JSONArray columnJSONArray = dataSourceDependenciesJSONObj.getJSONArray("column");

				for (int i = 0; i < columnJSONArray.length(); i++) {
					JSONObject columnJSONObj1 = columnJSONArray.getJSONObject(i);
					// JSONObject columnJSONObj = columnJSONArray.getJSONObject(i);
					// System.out.println("Column json::"+columnJSONObj);
					TableauReportQueryColumn tabReportQueryColumn = setColumnQueryValues(columnJSONObj1);
					queryColumnList.add(tabReportQueryColumn);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return queryColumnList;
	}

	private TableauReportQueryColumn setColumnQueryValues(JSONObject columnJSONObj) {
		// TODO Auto-generated method stub

		// System.out.println("12345");

		TableauReportQueryColumn tabReportQueryColumn = new TableauReportQueryColumn();

		// System.out.println("Column JSON:"+columnJSONObj);

		if (columnJSONObj.has("name")) {
			// System.out.println("NAME::"+ columnJSONObj.getString("name"));
			String columnExpression = columnJSONObj.getString("name");
			tabReportQueryColumn.setColumnExpression(columnExpression);
		}
		if (columnJSONObj.has("datatype")) {
			String dataType = columnJSONObj.getString("datatype");
			tabReportQueryColumn.setColumnDataType(dataType);
		}
		if (columnJSONObj.has("caption")) {
			if (!columnJSONObj.isNull("caption")) {
				String caption = columnJSONObj.getString("caption");
				tabReportQueryColumn.setColumnName(caption);
			}
		}
		if (columnJSONObj.has("aggregation")) {
			String aggregation = columnJSONObj.getString("aggregation");
			tabReportQueryColumn.setAggregateFunction(aggregation);
		}
		if (columnJSONObj.has("type")) {
			String type = columnJSONObj.getString("type");
			tabReportQueryColumn.setColumnType(type);
		}
		if (columnJSONObj.has("role")) {
			String role = columnJSONObj.getString("role");
			tabReportQueryColumn.setColumnQualification(role);
		}
		return tabReportQueryColumn;
	}

	private List<TableauVariable> fetchTableauVariableDetails(JSONObject worksheetJSONObj) {
		// TODO Auto-generated method stub

		List<TableauVariable> tableauVariableList = new ArrayList<TableauVariable>();

		JSONObject viewJSONObj = worksheetJSONObj.getJSONObject("table").getJSONObject("view");

		try {
			JSONObject dataSourceDependenciesJSONObj = viewJSONObj.optJSONObject("datasource-dependencies");

			if (dataSourceDependenciesJSONObj != null)
			{
				// System.out.println("Data Source Dependencies
				// Obj::"+dataSourceDependenciesJSONObj);

				tableauVariableList = fetchColumnDetailsForVariables(dataSourceDependenciesJSONObj, tableauVariableList);
				// tableauVariableList.add(tabVariableObj);
			}
			else
			{
				JSONArray dataSourceDependenciesJSONArray = viewJSONObj.getJSONArray("datasource-dependencies");

				for (int i = 0; i < dataSourceDependenciesJSONArray.length(); i++) {

					JSONObject dataSourceDependenciesJSONObj1 = dataSourceDependenciesJSONArray.getJSONObject(i);
					// System.out.println("Data Source Dependencies
					// Array::"+dataSourceDependenciesJSONObj);
					tableauVariableList = fetchColumnDetailsForVariables(dataSourceDependenciesJSONObj1,
							tableauVariableList);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.println("Tab Variable List 1111
		// Size::"+tableauVariableList.size());
		return tableauVariableList;
	}

	private List<TableauVariable> fetchColumnDetailsForVariables(JSONObject dataSourceDependenciesJSONObj,
			List<TableauVariable> tableauVariableList) {
		// TODO Auto-generated method stub

		// TableauVariable tabVariableObj = new TableauVariable();

		// List<TableauVariable> tabVarList = new ArrayList<TableauVariable>();
		// System.out.println("111");

		try {
			JSONObject columnJSONObj = dataSourceDependenciesJSONObj.optJSONObject("column");
			
			if (columnJSONObj != null)
			{
				tableauVariableList = fetchFormulaDetails(columnJSONObj, tableauVariableList);
			}
			else
			{
				JSONArray columnJsonArray = dataSourceDependenciesJSONObj.getJSONArray("column");

				for (int i = 0; i < columnJsonArray.length(); i++) {

					JSONObject columnJSONObj1 = columnJsonArray.getJSONObject(i);

					// System.out.println("Column JSON Obj::"+columnJSONObj);

					tableauVariableList = fetchFormulaDetails(columnJSONObj1, tableauVariableList);
					// System.out.println("Tab List Size::"+tableauVariableList.size());

				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		// System.out.println("Tab Var List in 111::"+tabVarList);
		return tableauVariableList;
	}

	private List<TableauVariable> fetchFormulaDetails(JSONObject columnJSONObj, List<TableauVariable> tabVarList) {
		// TODO Auto-generated method stub

		// System.out.println("Fetch Formula Details::");
		TableauVariable tabObj = new TableauVariable();

		try {
			if (columnJSONObj.has("calculation")) {

				noOfFormulas++;

				if (columnJSONObj.has("name")) {
					String formulaLanguageId = columnJSONObj.getString("name");
					tabObj.setFormulaLanguageId(formulaLanguageId);
				}
				if (columnJSONObj.has("caption")) {
					if (!columnJSONObj.isNull("caption")) {

						String name = columnJSONObj.get("caption").toString();

						// System.out.println("Caption::"+name);

						tabObj.setName(name);
					}

				}
				if (columnJSONObj.has("datatype")) {
					String dataType = columnJSONObj.getString("datatype");
					tabObj.setDataType(dataType);
				}
				if (columnJSONObj.has("role")) {
					String qualification = columnJSONObj.getString("role");
					tabObj.setQualification(qualification);
				}

				JSONObject formulaJSONObj = columnJSONObj.optJSONObject("calculation");

				if (formulaJSONObj != null)
				{
					if (formulaJSONObj.has("class")) {
						String className = formulaJSONObj.getString("class");
						tabObj.setClassName(className);
					}
					if (formulaJSONObj.has("formula")) {
						if (formulaJSONObj.get("formula") instanceof Integer) {
							String formula = String.valueOf(formulaJSONObj.getInt("formula"));
							tabObj.setDefinition(formula);
						} else if (formulaJSONObj.get("formula") instanceof String) {
							String formula = formulaJSONObj.getString("formula");
							tabObj.setDefinition(formula);
						}
					}
				}

				tabVarList.add(tabObj);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return tabVarList;
	}

	public Map<String, List<ReportData>> extractReportPathData(TableauReportConfigDTO config)
			throws MalformedURLException, IOException {
		StringBuilder output = new StringBuilder("");
		Map<String, List<ReportData>> reportPathsMap = new HashMap<String, List<ReportData>>();

		try {
			TABSERVERIP = config.getHostname();
			TABUSERID = config.getUsername();
			TABPASSWORD = config.getPassword();
			TAB_CONTENT_URL = config.getContentUrl();
			TAB_WS_PORT = config.getPort();
			TAB_CONNECTION_TYPE = config.getConnectionType();
			
			System.out.println("TAB_CONN:: " + TAB_CONNECTION_TYPE);
			
			Map<String, String> tokenAndSiteIdMap = logonAndGetTokenAndSiteID(TABSERVERIP, TABUSERID, TABPASSWORD, TAB_CONTENT_URL, TAB_CONNECTION_TYPE);
			logonToken = tokenAndSiteIdMap.get("token");
			siteId = tokenAndSiteIdMap.get("siteID");

//			String token = "v9HvD4lGS1C3OMOHPxIiKw|9udM7CMgrRB2PKCPlFBh9yESv8GL0HKA";
//			String siteId = "f821b569-7b67-433d-b206-dcd56c7e4489";

			URL url = new URL(
					TAB_CONNECTION_TYPE + "://" + TABSERVERIP + "/api/3.12/sites/" + siteId + "/workbooks");
			System.out.println("URL:" + url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod(REQUEST_METHOD_GET);
			conn.setRequestProperty(REQUEST_PROPERTY_X_TABLEAU_AUTH, logonToken);

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String tempReadLine = br.readLine();
			while (tempReadLine != null) {
				output.append(tempReadLine);
				tempReadLine = br.readLine();
			}

			if (output.toString().equals("")) {
//				return null;
			}

//			System.out.println("output:::" + output.toString());

			JSONObject viewsRequestJSONObj = XML.toJSONObject(output.toString());
			JSONObject viewsJSONObj = viewsRequestJSONObj.getJSONObject("tsResponse").getJSONObject("workbooks");

			List<ReportData> reportsPathDataList = fetchReportPathData(viewsJSONObj);
			System.out.println("reports path data:::" + reportsPathDataList);

			Map<String, String> projectsMap = extractProjectInfo();

			for (ReportData reportPathData : reportsPathDataList) {
				String projectId = reportPathData.getProjectId();
				String projectName = projectsMap.get(projectId);

				String contentUrl = reportPathData.getContentUrl();
//				String ss = contentUrl.substring(0, contentUrl.lastIndexOf("/"));
//				String lastPath = ss.substring(0, ss.lastIndexOf("/"));

//				String reportPath = projectName + "/" + lastPath;
				
				String reportPath = projectName + "/" + contentUrl;

				if (reportPathsMap.containsKey(reportPath)) {
					List<ReportData> rpDataList = reportPathsMap.get(reportPath);
					rpDataList.add(reportPathData);
					reportPathsMap.put(reportPath, rpDataList);
				} else {
					List<ReportData> rpDataList = new ArrayList<ReportData>();
					rpDataList.add(reportPathData);
					reportPathsMap.put(reportPath, rpDataList);
				}
			}

			System.out.println("report paths map:::" + reportPathsMap);

			if (conn != null) {
				conn.disconnect();
				br.close();
			}
		} 
		catch (Exception ex) {
			System.out.println("Extract Report Path Data Exception:::" + ex);
		}
		finally {
			logout(logonToken);
		}

		return reportPathsMap;

	}
	
	public String fetchWorkbookId(String viewId, TableauReportConfigDTO config)
	{
		String workbookId = "";
		StringBuilder output = new StringBuilder("");
		
		try
		{
			TABSERVERIP = config.getHostname();
			TABUSERID = config.getUsername();
			TABPASSWORD = config.getPassword();
			TAB_CONTENT_URL = config.getContentUrl();
			TAB_WS_PORT = config.getPort();
			TAB_CONNECTION_TYPE = config.getConnectionType();
			
			Map<String, String> tokenAndSiteIdMap = logonAndGetTokenAndSiteID(TABSERVERIP, TABUSERID, TABPASSWORD, TAB_CONTENT_URL, TAB_CONNECTION_TYPE);
			logonToken = tokenAndSiteIdMap.get("token");
			siteId = tokenAndSiteIdMap.get("siteID");

//			String token = "v9HvD4lGS1C3OMOHPxIiKw|9udM7CMgrRB2PKCPlFBh9yESv8GL0HKA";
//			String siteId = "f821b569-7b67-433d-b206-dcd56c7e4489";

			URL url = new URL(
					TAB_CONNECTION_TYPE + "://" + TABSERVERIP + "/api/3.12/sites/" + siteId + "/views");
			System.out.println("URL:" + url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod(REQUEST_METHOD_GET);
			conn.setRequestProperty(REQUEST_PROPERTY_X_TABLEAU_AUTH, logonToken);

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String tempReadLine = br.readLine();
			while (tempReadLine != null) {
				output.append(tempReadLine);
				tempReadLine = br.readLine();
			}

			if (output.toString().equals("")) {
//				return null;
			}

//			System.out.println("output:::" + output.toString());

			JSONObject viewsRequestJSONObj = XML.toJSONObject(output.toString());
			JSONObject viewsJSONObj = viewsRequestJSONObj.getJSONObject("tsResponse").getJSONObject("views");
			
			try
			{
				JSONObject viewJSONObj = viewsJSONObj.optJSONObject("view");
				
				if (viewJSONObj != null)
				{
					if (viewJSONObj.has("id"))
					{
						String rptId = viewJSONObj.getString("id");
						if (rptId.equals(viewId))
						{
							workbookId = viewJSONObj.getJSONObject("workbook").getString("id");
							return workbookId;
						}
					}
				}
				else
				{
					JSONArray viewJSONArray = viewsJSONObj.getJSONArray("view");
					
					for (Object viewObj : viewJSONArray)
					{
						JSONObject viewJSONObj1 = (JSONObject) viewObj;
						if (viewJSONObj1.has("id"))
						{
							String rptId = viewJSONObj1.getString("id");
							if (rptId.equals(viewId))
							{
								workbookId = viewJSONObj1.getJSONObject("workbook").getString("id");
								return workbookId;
							}
						}
					}
				}
				
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally {
			logout(logonToken);
		}
		
		return workbookId;
	}

	private static List<ReportData> fetchReportPathData(JSONObject viewsJSONObj) throws IOException {
		List<ReportData> reportsPathData = new ArrayList<ReportData>();

		try {
			JSONObject viewJSONObj = viewsJSONObj.optJSONObject("workbook");
			
			if (viewJSONObj != null)
			{
				ReportData reportPathData = new ReportData();

				if (viewJSONObj.has("id")) {
					String id = viewJSONObj.getString("id");
					reportPathData.setId(id);
				}
				if (viewJSONObj.has("name")) {
					String name = viewJSONObj.getString("name");
					reportPathData.setName(name);
				}
				if (viewJSONObj.has("contentUrl")) {
					String contentUrl = viewJSONObj.getString("contentUrl");
					reportPathData.setContentUrl(contentUrl);
				}
				if (viewJSONObj.has("createdAt")) {
					String createdAt = viewJSONObj.getString("createdAt");
					reportPathData.setCreatedAt(createdAt);
				}
				if (viewJSONObj.has("updatedAt")) {
					String updatedAt = viewJSONObj.getString("updatedAt");
					reportPathData.setUpdatedAt(updatedAt);
				}
//				if (viewJSONObj.has("workbook")) {
//					String workbookId = viewJSONObj.getJSONObject("workbook").getString("id");
//					String filePath = "D:\\Tableau\\Workbooks\\main-servicenow-report\\LTT product Dashboard migration - Metadata[1].twb";
//					File file = new File(filePath);
//					String inputXML = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
//					
////					String inputXML = fetchTableauWorkbookTwbAsString(workbookId);
//					String workbookName = viewJSONObj.getString("name");
	//
//					Set<String> universes = fetchReportDatasources(inputXML, workbookName);
	//
//					reportPathData.setUniverses(universes);
//				}
				if (viewJSONObj.has("project")) {
					String projectId = viewJSONObj.getJSONObject("project").getString("id");
					reportPathData.setProjectId(projectId);
				}

				reportsPathData.add(reportPathData);
			}
			else
			{
				JSONArray viewJSONArray = viewsJSONObj.getJSONArray("workbook");

				for (Object viewObj : viewJSONArray) {
					JSONObject viewJSONObj1 = (JSONObject) viewObj;
					ReportData reportPathData = new ReportData();

					if (viewJSONObj1.has("id")) {
						String id = viewJSONObj1.getString("id");
						reportPathData.setId(id);
					}
					if (viewJSONObj1.has("name")) {
						String name = viewJSONObj1.getString("name");
						reportPathData.setName(name);
					}
					if (viewJSONObj1.has("contentUrl")) {
						String contentUrl = viewJSONObj1.getString("contentUrl");
						reportPathData.setContentUrl(contentUrl);
					}
					if (viewJSONObj1.has("createdAt")) {
						String createdAt = viewJSONObj1.getString("createdAt");
						reportPathData.setCreatedAt(createdAt);
					}
					if (viewJSONObj1.has("updatedAt")) {
						String updatedAt = viewJSONObj1.getString("updatedAt");
						reportPathData.setUpdatedAt(updatedAt);
					}
//					if (viewJSONObj.has("workbook")) {
//						String workbookId = viewJSONObj.getJSONObject("workbook").getString("id");
//						String filePath = "D:\\Tableau\\Workbooks\\main-servicenow-report\\LTT product Dashboard migration - Metadata[1].twb";
//						File file = new File(filePath);
//						String inputXML = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
////						String inputXML = fetchTableauWorkbookTwbAsString(workbookId);
//						String workbookName = viewJSONObj.getString("name");
	//
//						Set<String> universes = fetchReportDatasources(inputXML, workbookName);
	//
//						reportPathData.setUniverses(universes);
//					}
					if (viewJSONObj1.has("project")) {
						String projectId = viewJSONObj1.getJSONObject("project").getString("id");
						reportPathData.setProjectId(projectId);
					}

					reportsPathData.add(reportPathData);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return reportsPathData;
	}

	private static Map<String, String> extractProjectInfo() throws IOException {
		Map<String, String> projectsMap = new HashMap<String, String>();

		StringBuilder output = new StringBuilder("");

		try {
//			Map<String, String> tokenAndSiteIdMap = logonAndGetTokenAndSiteID("prod-apnortheast-a.online.tableau.com","authentictech447@gmail.com", "Kpra16is@cmr", "recast1");
//			String token = tokenAndSiteIdMap.get("token");
//			String siteId = tokenAndSiteIdMap.get("siteID");

//			String token = "v9HvD4lGS1C3OMOHPxIiKw|9udM7CMgrRB2PKCPlFBh9yESv8GL0HKA";
//			String siteId = "f821b569-7b67-433d-b206-dcd56c7e4489";

			URL url = new URL(
					TAB_CONNECTION_TYPE + "://" + TABSERVERIP + "/api/3.12/sites/" + siteId + "/workbooks");
			System.out.println("URL:" + url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod(REQUEST_METHOD_GET);
			conn.setRequestProperty(REQUEST_PROPERTY_X_TABLEAU_AUTH, logonToken);

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String tempReadLine = br.readLine();
			while (tempReadLine != null) {
				output.append(tempReadLine);
				tempReadLine = br.readLine();
			}

			if (output.toString().equals("")) {
//				return null;
			}

//			System.out.println("output:::" + output.toString());

			JSONObject workbooksRequestJSONObj = XML.toJSONObject(output.toString());

			JSONObject workbooksJSONObj = workbooksRequestJSONObj.getJSONObject("tsResponse")
					.getJSONObject("workbooks");

			projectsMap = fetchProjectInfo(workbooksJSONObj);

			if (conn != null) {
				conn.disconnect();
				br.close();
			}
		} catch (Exception ex) {
			System.out.println("Extract Project Info Exception:::" + ex);
		}

		return projectsMap;
	}

	private static Map<String, String> fetchProjectInfo(JSONObject workbooksJSONObj) {

		Map<String, String> projectInfoMap = new HashMap<String, String>();

		try {
			JSONObject workbookJSONObj = workbooksJSONObj.optJSONObject("workbook");

			if (workbookJSONObj != null)
			{
				JSONObject projectJSONObj = workbookJSONObj.optJSONObject("project");
				
				if (projectJSONObj != null)
				{
					String projectId = projectJSONObj.getString("id");
					String projectName = projectJSONObj.getString("name");
					
					if (!projectInfoMap.containsKey(projectId)) {
						projectInfoMap.put(projectId, projectName);
					}
				}

			}
			else
			{
				JSONArray workbookJSONArray = workbooksJSONObj.getJSONArray("workbook");

				for (Object workbookObj : workbookJSONArray) {
					JSONObject workbookJSONObj1 = (JSONObject) workbookObj;

					JSONObject projectJSONObj = workbookJSONObj1.optJSONObject("project");
					
					if (projectJSONObj != null)
					{
						String projectId = projectJSONObj.getString("id");
						String projectName = projectJSONObj.getString("name");

						if (!projectInfoMap.containsKey(projectId)) {
							projectInfoMap.put(projectId, projectName);
						}
					}

				}
			}

		} catch (Exception e) {
			System.out.println("Project Extraction Exception:::" + e);
		}

		return projectInfoMap;
	}

	private static Set<String> fetchReportDatasources(String inputXML, String workbookName) {
		Set<String> universes = new HashSet<String>();

		JSONObject reportJSON = XML.toJSONObject(inputXML);
		// System.out.println("JSON values::::"+reportJSON);

		JSONObject worksheet = reportJSON.getJSONObject("workbook").getJSONObject("worksheets");

		try {
			JSONObject worksheetJSONObj = worksheet.getJSONObject("worksheet");
			String wsheetName = worksheetJSONObj.getString("name");

			if (wsheetName.equals(workbookName)) {
				JSONObject datasources = worksheetJSONObj.getJSONObject("table").getJSONObject("view")
						.getJSONObject("datasources");
				universes = fetchDatasourceNames(datasources);
			}

		} catch (JSONException j1) {
			JSONArray worksheetJSONArray = worksheet.getJSONArray("worksheet");

			for (Object wkObj : worksheetJSONArray) {
				JSONObject worksheetJSONObj = (JSONObject) wkObj;
				String wsheetName = worksheetJSONObj.getString("name");

				if (wsheetName.equals(workbookName)) {
					JSONObject datasources = worksheetJSONObj.getJSONObject("table").getJSONObject("view")
							.getJSONObject("datasources");
					universes = fetchDatasourceNames(datasources);
				}
			}
		} catch (Exception e) {
			System.out.println("Report Datasource Exception:::" + e);
		}

		return universes;
	}

	private static Set<String> fetchDatasourceNames(JSONObject datasources) {
		Set<String> universes = new HashSet<String>();

		try {
			JSONObject datasourceJSONObj = datasources.optJSONObject("datasource");
			
			if (datasourceJSONObj != null)
			{
				if (datasourceJSONObj.has("name")) {
					String name = datasourceJSONObj.getString("name");
					universes.add(name);
				}
			}
			else
			{
				JSONArray datasourceJSONArray = datasources.getJSONArray("datasource");

				for (Object dtObj : datasourceJSONArray) {
					JSONObject datasourceJSONObj1 = (JSONObject) dtObj;
					if (datasourceJSONObj1.has("name")) {
						String name = datasourceJSONObj1.getString("name");
						universes.add(name);
					}
				}
			}

		} catch (Exception e) {
			System.out.println("Report Datasource Exception Internal:::" + e);
		}

		return universes;
	}

	public void logout(String token) {
		try {
			URL url = new URL(TAB_CONNECTION_TYPE + "://" + TABSERVERIP + "/api/3.12/auth/signout");
//			System.out.println("URL:"+url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(REQUEST_METHOD_POST);
			conn.setRequestProperty(REQUEST_PROPERTY_X_TABLEAU_AUTH, token);
			conn.connect();

			int statusCode = conn.getResponseCode();

			if (statusCode == 204) {
				System.out.println("logged out!");
			}
			if (conn != null) {
				conn.disconnect();
			}
		} catch (Exception ex) {
			System.out.println("Logout exception:::" + ex);
		}
	}

}
