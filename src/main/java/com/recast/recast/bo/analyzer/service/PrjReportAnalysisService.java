package com.recast.recast.bo.analyzer.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.recast.recast.bo.analyzer.dto.BoReportConfigDTO;
import com.recast.recast.bo.analyzer.dto.ReportConfigDTO;
import com.recast.recast.bo.analyzer.dto.TableauReportConfigDTO;
import com.recast.recast.bo.analyzer.entity.AnalysisReport;
import com.recast.recast.bo.analyzer.entity.AnalysisReportsTable;
import com.recast.recast.bo.analyzer.entity.AnalysisSemanticColumns;
import com.recast.recast.bo.analyzer.entity.CommonalityParams;
import com.recast.recast.bo.analyzer.entity.CommonalityReport;
import com.recast.recast.bo.analyzer.entity.ComplexityReport;
import com.recast.recast.bo.analyzer.entity.FolderTask;
import com.recast.recast.bo.analyzer.entity.PrjRptAnalysis;
import com.recast.recast.bo.analyzer.entity.PrjRptConParams;
import com.recast.recast.bo.analyzer.entity.Project;
import com.recast.recast.bo.analyzer.entity.UniverseReport;
import com.recast.recast.bo.analyzer.entity.VisualDetails;
import com.recast.recast.bo.analyzer.model.AnalysisReportModel;
import com.recast.recast.bo.analyzer.model.BOAnalyzerModel;
import com.recast.recast.bo.analyzer.model.BOCommonalityParams;
import com.recast.recast.bo.analyzer.model.BOItem;
import com.recast.recast.bo.analyzer.model.BOReportVisualizationTab;
import com.recast.recast.bo.analyzer.model.BOUniverse;
import com.recast.recast.bo.analyzer.model.BOVisualElements;
import com.recast.recast.bo.analyzer.model.BOVisualizationModel;
import com.recast.recast.bo.analyzer.model.CommonalityReportModel;
import com.recast.recast.bo.analyzer.model.ComplexityReportModel;
import com.recast.recast.bo.analyzer.model.PrjRptAnalysisModel;
import com.recast.recast.bo.analyzer.model.TableauDatasourceMap;
import com.recast.recast.bo.analyzer.model.TableauReportDetailsModel;
import com.recast.recast.bo.analyzer.model.TaskSelectedReports;
import com.recast.recast.bo.analyzer.model.UniverseReportModel;
import com.recast.recast.bo.analyzer.reportanalyser.ReportAnalyzerFactory;
import com.recast.recast.bo.analyzer.repository.AnalysisReportRepository;
import com.recast.recast.bo.analyzer.repository.AnalysisReportTableRepository;
import com.recast.recast.bo.analyzer.repository.AnalysisSemanticColumnRepository;
import com.recast.recast.bo.analyzer.repository.CommonalityParamsRepository;
import com.recast.recast.bo.analyzer.repository.CommonalityReportRepository;
import com.recast.recast.bo.analyzer.repository.ComplexityReportsRepository;
import com.recast.recast.bo.analyzer.repository.PrjRptConParamsRepository;
import com.recast.recast.bo.analyzer.repository.ProjectRepository;
import com.recast.recast.bo.analyzer.repository.ProjectRptAnalysisRepository;
import com.recast.recast.bo.analyzer.repository.TaskStatusRepository;
import com.recast.recast.bo.analyzer.repository.UniverseReportRepository;
import com.recast.recast.bo.analyzer.repository.VisualDetailsRepository;
import com.recast.recast.bo.analyzer.repository.prjFolderRepository;
import com.recast.recast.bo.analyzer.tableau.model.TableauAnalyzerModel;
import com.recast.recast.bo.analyzer.tableau.model.TableauSemanticModel;
import com.recast.recast.bo.analyzer.tableau.model.TableauVisualElements;
import com.recast.recast.bo.analyzer.tableau.model.TableauVisualizationModel;
import com.recast.recast.bo.analyzer.util.BOConstants;
import com.recast.recast.bo.analyzer.util.BOReportAnalyzer;
import com.recast.recast.bo.analyzer.util.EntityBuilder;
import com.recast.recast.bo.analyzer.util.ModelBuilder;
import com.recast.recast.bo.analyzer.util.ReportConstants;
import com.recast.recast.bo.analyzer.util.TableauConstants;
import com.recast.recast.bo.analyzer.util.TableauReportAnalyzer;
import com.sap.sl.sdk.authoring.connection.ConnectionService;



@Service
public class PrjReportAnalysisService {
	private static final Logger logger = LoggerFactory.getLogger(ConnectionService.class);
	
	//Added by kalpesh for cognos universe source id
	static int i=100;
	
	@Autowired(required = false)
	private ProjectRptAnalysisRepository projReportAnalysisRepo;
	
	@Autowired(required = false)
	private ProjectRepository projectRepository;
	
	@Autowired(required = false)
	private PrjRptConParamsRepository prjRptConParamsRepository;
	
	@Autowired(required = false)
	private TaskStatusRepository taskStatusRepository;
	
	@Autowired(required = false)
	private AnalysisReportRepository analysisReportRepository;
	
	@Autowired(required = false)
	private CommonalityReportRepository commonalityReportRepository;
	
	@Autowired(required = false)
	private UniverseReportRepository universeReportRepository;
	
	@Autowired(required = false)
	private prjFolderRepository prjFolderRepository;

	@Autowired(required = false)
	private ComplexityReportsRepository complexityReportRepository;
	
	@Autowired(required = false)
	private AnalysisReportTableRepository analysisReportTableRepository;
	
	@Autowired(required = false)
	private VisualDetailsRepository visualDetailsRepository;
	
	@Autowired(required = false)
	private AnalysisSemanticColumnRepository semanticColumnRepository;
	
	@Autowired(required = false)
	private ComplexityService complexityService;
	
	@Autowired(required = false)
	private CommonalityParamsRepository commonalityParamsRepository;
	
	
	@Transactional
	public PrjRptAnalysisModel save(PrjRptAnalysisModel pm) {
		logger.debug("Inside report analysis Service -> Save");
		logger.debug("Setting status as: " + pm.getTaskStatus().getCode());
		Project p = projectRepository.getOne(pm.getProjectId());
		PrjRptAnalysis analysisTask = EntityBuilder.prjRptanalysisEntityBuilder(pm);
		analysisTask.setProject(p);
		analysisTask = projReportAnalysisRepo.save(analysisTask);
		
		PrjRptAnalysis p1 = projReportAnalysisRepo.findById(analysisTask.getId()).get();
		
		List<TaskSelectedReports> selectedReports = pm.getSelectedReportsList();
		List<FolderTask> folderTaskList = new ArrayList<FolderTask>();
		selectedReports.forEach(x->{
			
			FolderTask folderTask = EntityBuilder.folderReportEntityBuilder(x);
			folderTask.setPrjFolderAnalysisId(p1.getId());
			folderTaskList.add(folderTask);
		});
		prjFolderRepository.saveAll(folderTaskList);
		
		analysisTask.setTaskFolderdetails(folderTaskList);

		return ModelBuilder.projectReportAnalysisModelBuilder(analysisTask);
	}
	
	
	@SuppressWarnings("unchecked")
	@Async
	@Transactional
	public CompletableFuture<String> analyzeConnection(PrjRptAnalysisModel pm) {
		
		PrjRptAnalysis p = projReportAnalysisRepo.findById(pm.getId()).get();
		p.setTaskStatus(taskStatusRepository.findById("INPROG").get());
		projReportAnalysisRepo.saveAndFlush(p);
		logger.debug("Status of " + p.getTaskName() + " is now set to " + p.getTaskStatus().getName() + ".");
		
		List<PrjRptConParams> conParams = prjRptConParamsRepository.findByProjectReportConId(pm.getProjectReportConId());
		Map<String, String> conn = new HashMap<String, String>();
		conParams.forEach(x -> conn.put(x.getRptConParamType().getCode(), x.getRptConParamValue()));
		List<AnalysisReport> analysisReports = new ArrayList<AnalysisReport>();
		List<CommonalityReport> commonalityReports = new ArrayList<CommonalityReport>();
		List<UniverseReport> universeReports = new ArrayList<UniverseReport>();
		
		List<AnalysisReportsTable> analysisReportsTableList = new ArrayList<AnalysisReportsTable>();
		List<VisualDetails> visualDetailsList = new ArrayList<VisualDetails>();
		List<AnalysisSemanticColumns> semanticColumnList = new ArrayList<AnalysisSemanticColumns>();
		
		List<CommonalityParams> commonalityParamsList =  new ArrayList<CommonalityParams>();
		
		
		List<ComplexityReport> complexityReportList = new ArrayList<ComplexityReport>();
		
		
			
		logger.debug("REPORT TYPE: " + pm.getReportTypeCode());
		try {
		
				
			 if(pm.getReportTypeCode().equals(ReportConstants.REPORT_TYPE_BO)) {
				
				/* Set all necessary config parameters */
				BoReportConfigDTO boConfigDTO = new BoReportConfigDTO();
				boConfigDTO.setReportType(ReportConstants.REPORT_TYPE_BO);
				boConfigDTO.setHostname(conn.get(BOConstants.HOST_NAME));
				boConfigDTO.setPort(conn.get(BOConstants.PORT));
				boConfigDTO.setUsername(conn.get(BOConstants.USERNAME));
				boConfigDTO.setPassword(conn.get(BOConstants.PASSWORD));
			//	boConfigDTO.setPath(conn.get(BOConstants.PATH));
				
				List<String> reportsIdList = new ArrayList<String>(); 
				Iterator<TaskSelectedReports> iter = pm.getSelectedReportsList().iterator();
				
				while(iter.hasNext())
				{
					TaskSelectedReports t = iter.next();
					reportsIdList.add(t.getReportid());
				}
				boConfigDTO.setReports(reportsIdList);
				
			
				
				/* Create Analyzer instance */
				BOReportAnalyzer analyzer = (BOReportAnalyzer) ReportAnalyzerFactory.getInstance().getReportAnalyzer((ReportConfigDTO) boConfigDTO);
				
				/* Separate analyzer results into report-level and universe-level */
				Map<String,Object> analyzerData = analyzer.analyze();
				
				List<BOAnalyzerModel> rawReports = (List<BOAnalyzerModel>) analyzerData.get("reports");
				List<BOUniverse> rawUniverses = (List<BOUniverse>) analyzerData.get("universes");
				List<BOVisualizationModel> visualizationModelList = (List<BOVisualizationModel>) analyzerData.get("visualization");
				
				List<BOCommonalityParams> boCommonalityParamsList = (List<BOCommonalityParams>) analyzerData.get("commonality");
				
				//Map<Integer,BOVisualizationModel> visualizationModelMap =  (Map<Integer, BOVisualizationModel>) analyzerData.get("visualization");
				
				/* Build analysis report entites using BO analysis objects and save */
				rawReports.forEach(x -> {
					AnalysisReport a = EntityBuilder.analysisReportEntityBuilder(x);
				   a.setPrjRptAnalysisId(p.getId());
					//logger.debug(a.toString());
					
					
					analysisReports.add(a);
				});
				analysisReportRepository.saveAll(analysisReports);
				
				/* Build universe report entites using BO universe objects and save */
				
				System.out.println("Raw Universes::"+rawUniverses);
				rawUniverses.forEach(x -> {
					UniverseReport u = EntityBuilder.universeReportEntityBuilder(x);
					u.setPrjRptAnalysisId(p.getId());
					//logger.debug(u.toString());
					universeReports.add(u);
				});
				universeReportRepository.saveAll(universeReports);
				
				/* Create a map of report id -> report to help create commonality entites */
				//Map<Integer, AnalysisReport> analysisReportMap = new HashMap<Integer, AnalysisReport>();
				Map<String, AnalysisReport> analysisReportMap = new HashMap<String, AnalysisReport>();
				analysisReportRepository.findByPrjRptAnalysisId(p.getId()).forEach(x -> analysisReportMap.put(x.getReportId().toString(), x));
				
				
				boCommonalityParamsList.forEach(x -> {
					CommonalityParams c = EntityBuilder.commonalityParamsEntityBuilder(x);
					c.setTaskId(p.getId());
					//logger.debug(u.toString());
					commonalityParamsList.add(c);
				});
				commonalityParamsRepository.saveAll(commonalityParamsList);

				/* Create commonality entities and save them */
				analyzer.computeCommonality(rawReports).forEach(x -> {
					CommonalityReport y = EntityBuilder.commonalityReportEntityBuilder(x);
					y.setPrjRptAnalysisId(p.getId());
					y.setAnalysisReport1(analysisReportMap.get(y.getAnalysisReport1().getReportId()));
					y.setAnalysisReport2(analysisReportMap.get(y.getAnalysisReport2().getReportId()));
					commonalityReports.add(y);
				});
				commonalityReportRepository.saveAll(commonalityReports);
				
				//Complexity Details
				rawReports.forEach(x -> {
					//logger.debug(x.toString());
					ComplexityReport a = EntityBuilder.ComplexityReportEntityBuilder(x);
					a.setTaskId(p.getId());
				//	analysisReports.add(a);
				
						String complexityStatus = complexityService.classifyBOComplexityReport(a.getComplexityParameter());
						a.setComplexityStatus(complexityStatus);
					 
						complexityReportList.add(a);
					
				});
				complexityReportRepository.saveAll(complexityReportList);
						

				//Query Parsing code and storing the data

				analyzer.queryParser(rawReports).forEach(x -> {
						
					AnalysisReportsTable analysisReportTable = EntityBuilder.analysisReportTableEnityBuilder(x);
					analysisReportTable.setTaskId(p.getId());
					analysisReportsTableList.add(analysisReportTable);
						
					});
				
				analysisReportTableRepository.saveAll(analysisReportsTableList);
				
				//Code for Visualization
				
				visualizationModelList.forEach(x->{

					String reportId = String.valueOf(x.getReportId());
					
					String reportName = x.getReportName();
					
					List<BOReportVisualizationTab> reportTabVisualizationList = x.getBoVisualizationTab();
					
					reportTabVisualizationList.forEach(y->{
						String reportTabId = y.getReportTabId();
						String reportTabName = y.getReportTabName();
						
						List<BOVisualElements> visualElementList = y.getBoVisualElements();
						
						//System.out.println("Visual List::"+visualElementList);
						
						visualElementList.forEach(z->{
							
							VisualDetails visualDetails = EntityBuilder.visualDetailsEntityBuider(z);
							visualDetails.setReportId(reportId);
							visualDetails.setReportName(reportName);
							visualDetails.setReportTabId(reportTabId);
							visualDetails.setReportTabName(reportTabName);
							visualDetails.setTaskId(p.getId());
							visualDetailsList.add(visualDetails);
						});
						
					});
	
				});
				visualDetailsRepository.saveAll(visualDetailsList);
				
//				analysisReportRepository.findByPrjRptAnalysisId(p.getId()).forEach(x->{
//					System.out.println("Report Id::"+x.getReportId());
//					
//					System.out.println("Visulization Model::"+visualizationModelMap.get(x.getReportId()));
//					
//					
//				});
				
				//Store the universe Column Details in table
				
				rawUniverses.forEach(x -> {
					
					int universeId = x.getId();
					String universeName = x.getName();

					Map<String,Set<BOItem>> universeColumnMap = x.getItems();
					
					universeColumnMap.entrySet().forEach(y->{
						String columnQualification = y.getKey();
						
						if(columnQualification.equalsIgnoreCase("measures") || columnQualification.equalsIgnoreCase("attributes") || columnQualification.equalsIgnoreCase("dimensions"))
						{
							Set<BOItem> boItems = y.getValue();
							
							boItems.forEach(z->{
								
								AnalysisSemanticColumns semanticColumns = EntityBuilder.analysisSematicEntityBuilder(z);
								semanticColumns.setColumnQualification(columnQualification);
								semanticColumns.setTaskId(p.getId());
								semanticColumns.setSemanticId(universeId);
								semanticColumns.setSemanticName(universeName);
								semanticColumnList.add(semanticColumns);
							});
						}
					});
					
				});
				
				semanticColumnRepository.saveAll(semanticColumnList);
//				
				
				
				
				/* Set status of task as completed, save and flush the changes */
				p.setTaskStatus(taskStatusRepository.findById("FIN").get());
				p.setComment("Success");
				projReportAnalysisRepo.saveAndFlush(p);
				logger.debug("Status of " + p.getTaskName() + " is now set to " + p.getTaskStatus().getName() + ".");				
			}
			
			else if(pm.getReportTypeCode().equals(ReportConstants.REPORT_TYPE_TABLEAU)) {
				
				TableauReportConfigDTO tableauConfigDTO = new TableauReportConfigDTO();
				
				tableauConfigDTO.setReportType(ReportConstants.REPORT_TYPE_TABLEAU);
				tableauConfigDTO.setPath(conn.get(TableauConstants.PATH));
				tableauConfigDTO.setHostname(conn.get(TableauConstants.HOST_NAME));
				tableauConfigDTO.setPassword(conn.get(TableauConstants.PASSWORD));
				tableauConfigDTO.setUsername(conn.get(TableauConstants.USERNAME));
				tableauConfigDTO.setConnectionType(conn.get(TableauConstants.CONNECTION_TYPE));
				tableauConfigDTO.setPort(conn.get(TableauConstants.PORT));
				tableauConfigDTO.setContentUrl(conn.get(TableauConstants.CONTENT_URL));
				List<String> workbooksIdList = new ArrayList<String>(); 
				Iterator<TaskSelectedReports> iter = pm.getSelectedReportsList().iterator();
				
				TableauAnalyzerAPI tabAnalyzerApi = new TableauAnalyzerAPI();
				
				if (tableauConfigDTO.getPath().trim().isEmpty()) {
					while(iter.hasNext())
					{
						TaskSelectedReports t = iter.next();
						String viewId = t.getReportid();
//						String workbookId = tabAnalyzerApi.fetchWorkbookId(viewId, tableauConfigDTO);
						workbooksIdList.add(viewId);
					}
				}
				
				tableauConfigDTO.setWorkbooks(workbooksIdList);
				
				/* Create Analyzer instance */
				TableauReportAnalyzer analyzer = (TableauReportAnalyzer) ReportAnalyzerFactory.getInstance().getReportAnalyzer((ReportConfigDTO) tableauConfigDTO);
				
				Map<String,Object> analyzerData = analyzer.analyze();
				List<TableauAnalyzerModel> rawReports = (List<TableauAnalyzerModel>) analyzerData.get("reports");
				List<TableauSemanticModel> rawDatasources = (List<TableauSemanticModel>) analyzerData.get("datasources");
				List<TableauVisualizationModel> visualizationModelList = (List<TableauVisualizationModel>) analyzerData.get("visualizations");
//				List<TabNewReportComplexity> complexityList = (List<TabNewReportComplexity>) analyzerData.get("complexity");
				
				System.out.println("Report::"+rawReports);
				/* Build analysis report entites using BO analysis objects and save */
				rawReports.forEach(x -> {
					AnalysisReport a = EntityBuilder.analysisReportEntityBuilder(x);
					a.setPrjRptAnalysisId(p.getId());
					//logger.debug(a.toString());
					analysisReports.add(a);
				});
				analysisReportRepository.saveAll(analysisReports);
				
				System.out.println("Datasource::" + rawDatasources);
				/* Build datasource entites using Tableau semantic objects and save */
				rawDatasources.forEach(x -> {
					UniverseReport u = EntityBuilder.universeReportEntityBuilder(x);
					u.setPrjRptAnalysisId(p.getId());
					
					String universeSourceId = String.valueOf(i);
					u.setUniverseSourceId(universeSourceId);
					i++;
					//logger.debug(u.toString());
					universeReports.add(u);
				});
				i=100;
				
				universeReportRepository.saveAll(universeReports);
				
				//Complexity Details
				
				rawReports.forEach(x -> {
					ComplexityReport a = EntityBuilder.ComplexityReportEntityBuilder(x);
					a.setTaskId(p.getId());
					String complexityStatus = complexityService.classifyComplexityTableau(a.getComplexityParameter());
					a.setComplexityStatus(complexityStatus);
					complexityReportList.add(a);
					
				});
				complexityReportRepository.saveAll(complexityReportList);
				
				//Code for Visualization
				visualizationModelList.forEach(x -> {
					String reportId = x.getReportId();
					String reportName = x.getReportName();
					
					List<TableauVisualElements> visualElementList = x.getVisualElementList();
					System.out.println("Visual List::"+visualElementList);
					
					visualElementList.forEach(y -> {
						VisualDetails visualDetails = EntityBuilder.visualDetailsEntityBuilder(y);
						visualDetails.setReportId(reportId);
						visualDetails.setReportName(reportName);
						visualDetails.setStyleRules(x.getStyleRuleJSON());
						visualDetails.setTaskId(p.getId());
						visualDetailsList.add(visualDetails);
					});
					
				});
				
				visualDetailsRepository.saveAll(visualDetailsList);
				
				p.setTaskStatus(taskStatusRepository.findById("FIN").get());
				p.setComment("Success");
				projReportAnalysisRepo.saveAndFlush(p);
				logger.debug("Status of " + p.getTaskName() + " is now set to " + p.getTaskStatus().getName() + ".");		
	
			}
			
			
			

			else {
				logger.error("Report type is invalid");
				p.setTaskStatus(taskStatusRepository.findById("FAIL").get());
				p.setComment("Report type is invalid");
				projReportAnalysisRepo.saveAndFlush(p);
				logger.debug("Status of " + p.getTaskName() + " is now set to " + p.getTaskStatus().getName() + ".");
			}
		}
		
		catch (Exception e) {
			e.printStackTrace();
			p.setTaskStatus(taskStatusRepository.findById("FAIL").get());
			p.setComment(e.getMessage());
			projReportAnalysisRepo.saveAndFlush(p);
			logger.debug("Status of " + p.getTaskName() + " is now set to " + p.getTaskStatus().getName() + ".");
			
		}
		
		String op = "Status of " + p.getTaskName() + " is now set to " + p.getTaskStatus().getName() + ".";
		return CompletableFuture.completedFuture(op);
	}
	
	@Transactional
	public void deleteTask(int id) {
		logger.info("Deleting : " + id);
		//logger.info(projReportAnalysisRepo.findById(id).get().toString());
		commonalityReportRepository.deleteByPrjRptAnalysisId(id);
		//complexityDetailsRepository.deleteByTaskId(id);
		//analysisReportTableRepository.deleteByTaskId(id);
		projReportAnalysisRepo.deleteById(id);
	}
	
	@Transactional
	public List<AnalysisReportModel> getAnalysis(int id) {
		return analysisReportRepository.findByPrjRptAnalysisId(id).stream().map(ModelBuilder::analysisReportModelBuilder).collect(Collectors.toList());	
	}
	
	@Transactional
	public List<CommonalityReportModel> getCommonality(int id) {
		return commonalityReportRepository.findByPrjRptAnalysisId(id).stream().map(ModelBuilder::commonalityReportModelBuilder).collect(Collectors.toList());
	}
	
	@Transactional
	public List<UniverseReportModel> getUniverse(int id) {
		return universeReportRepository.findByPrjRptAnalysisId(id).stream().map(ModelBuilder::universeReportModelBuilder).collect(Collectors.toList());
	}
	
	
	
	




	public List<ComplexityReportModel> getComplexity(int id) {
		// TODO Auto-generated method stub
		return complexityReportRepository.findBytaskId(id).stream().map(ModelBuilder::complexityReportModelBuilder).collect(Collectors.toList());
	}
	
	
	@Transactional
	public List<TableauDatasourceMap> getTableauReportDetails(int taskId) {
		System.out.println("Inside analysis tableau service");
		List<List<String>> reportDetails = analysisReportRepository.findTableauReportDetailsByTaskId(taskId);
		List<List<String>> tabDatasourceDetails = universeReportRepository.getDatasourceDetails(taskId);
		
		List<TableauReportDetailsModel> tabReportDetailsList = new ArrayList<TableauReportDetailsModel>();
		for (List<String> reportDetail : reportDetails) {
			TableauReportDetailsModel tabReportDetailModel = new TableauReportDetailsModel();
			tabReportDetailModel.setReportPath(reportDetail.get(0));
			tabReportDetailModel.setWorksheetName(reportDetail.get(1));
			String tempStr = reportDetail.get(2).toString();
			tempStr = tempStr.replaceAll("/", "-");
			tempStr = tempStr.replaceAll("#", "No.");
			System.out.println("temp Str:: " + tempStr);
			JSONArray colJSONArr = new JSONArray(tempStr);
			System.out.println("Col arr JSON:: " + colJSONArr);
			String[] columnDetails = new String[colJSONArr.length()];
			
			for (int i=0; i<colJSONArr.length(); i++) {
				System.out.println("JSONArrr:: " + "[" + colJSONArr.getJSONArray(i).get(0).toString() + "]");
				columnDetails[i] = "[" + colJSONArr.getJSONArray(i).get(0).toString() + "]";
			}
			
			
			tabReportDetailModel.setColumnNames(columnDetails);
			String[] datasourceDetails = reportDetail.get(3).split(",");
			tabReportDetailModel.setDatasourceNames(datasourceDetails);
			tabReportDetailsList.add(tabReportDetailModel);
		}

		List<TableauDatasourceMap> dtList = new ArrayList<TableauDatasourceMap>();
		for (List<String> tabDatasourceDetail : tabDatasourceDetails) {
			String datasourceName = tabDatasourceDetail.get(0);
			String tablesColInfo = tabDatasourceDetail.get(1);
			String connectionClass = tabDatasourceDetail.get(2);
			JSONArray jsonArr = new JSONArray(tablesColInfo);
			
			for (Object tabInfo : jsonArr) {
				JSONObject tabInfoJSONObj = (JSONObject) tabInfo;
				String tableName = tabInfoJSONObj.getString("name");
				JSONArray colJSONArr = tabInfoJSONObj.getJSONArray("columns");
				System.out.println("colJSONArr::" + colJSONArr );
				
				for (Object colObj : colJSONArr) {
					JSONObject colJSONObj = (JSONObject) colObj;
					String colName = colJSONObj.getString("name");
					String dataType = colJSONObj.getString("dataType");	
					TableauDatasourceMap tabDtMap = new TableauDatasourceMap();
					tabDtMap.setTableName(tableName);
					tabDtMap.setDatasourceName(datasourceName);
					tabDtMap.setDataType(dataType);
					tabDtMap.setConnectionClass(connectionClass);
					tabDtMap.setColumnName(colName);
					dtList.add(tabDtMap);
					
				}
			}
			System.out.println("\n");
		}

		return dtList;
		
	}

}
