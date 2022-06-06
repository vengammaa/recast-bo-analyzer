package com.recast.recast.bo.analyzer.util;


import com.recast.recast.bo.analyzer.entity.AnalysisReport;
import com.recast.recast.bo.analyzer.entity.CommonalityReport;
import com.recast.recast.bo.analyzer.entity.ComplexityReport;
import com.recast.recast.bo.analyzer.entity.FolderTask;
import com.recast.recast.bo.analyzer.entity.PrjRptAnalysis;
import com.recast.recast.bo.analyzer.entity.PrjRptConParams;
import com.recast.recast.bo.analyzer.entity.PrjRptMigrator;
import com.recast.recast.bo.analyzer.entity.Project;
import com.recast.recast.bo.analyzer.entity.ProjectReportCon;
import com.recast.recast.bo.analyzer.entity.ReportType;
import com.recast.recast.bo.analyzer.entity.Role;
import com.recast.recast.bo.analyzer.entity.RptConParamType;
import com.recast.recast.bo.analyzer.entity.Status;
import com.recast.recast.bo.analyzer.entity.TaskStatus;
import com.recast.recast.bo.analyzer.entity.UniverseReport;
import com.recast.recast.bo.analyzer.entity.User;
import com.recast.recast.bo.analyzer.entity.UserProfile;
import com.recast.recast.bo.analyzer.model.AnalysisReportModel;
import com.recast.recast.bo.analyzer.model.CommonalityReportModel;
import com.recast.recast.bo.analyzer.model.ComplexityReportModel;
import com.recast.recast.bo.analyzer.model.PrjRptAnalysisModel;
import com.recast.recast.bo.analyzer.model.PrjRptConParamsModel;
import com.recast.recast.bo.analyzer.model.PrjRptMigratorModel;
import com.recast.recast.bo.analyzer.model.ProjectModel;
import com.recast.recast.bo.analyzer.model.ProjectReportConModel;
import com.recast.recast.bo.analyzer.model.ReportData;
import com.recast.recast.bo.analyzer.model.ReportPathDataModel;
import com.recast.recast.bo.analyzer.model.ReportPathModel;
import com.recast.recast.bo.analyzer.model.ReportTypeModel;
import com.recast.recast.bo.analyzer.model.RoleModel;
import com.recast.recast.bo.analyzer.model.RptConParamTypeModel;
import com.recast.recast.bo.analyzer.model.StatusModel;
import com.recast.recast.bo.analyzer.model.TaskSelectedReports;
import com.recast.recast.bo.analyzer.model.TaskStatusModel;
import com.recast.recast.bo.analyzer.model.UniverseReportModel;
import com.recast.recast.bo.analyzer.model.UserModel;
import com.recast.recast.bo.analyzer.model.UserProfileModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
public class ModelBuilder {

	

	public static TaskStatusModel taskStatusModelBuilder(TaskStatus t) {
		TaskStatusModel tm = new TaskStatusModel();
		tm.setName(t.getName());
		tm.setCode(t.getCode());
		return tm;
	}
	
	
	public static AnalysisReportModel analysisReportModelBuilder(AnalysisReport ar) {
		AnalysisReportModel am = new AnalysisReportModel();
		am.setId(ar.getId());
		am.setPrjRptAnalysisId(ar.getPrjRptAnalysisId());
		am.setColumnNames(ar.getColumnNames());
		am.setReportId(ar.getReportId());
		am.setReportName(ar.getReportName());
		am.setReportUpdatedDate(ar.getReportUpdatedDate());
		am.setReportCreationDate(ar.getReportCreationDate());
		am.setReportType(ar.getReportType());
		am.setFolderPath(ar.getFolderPath());
		if(ar.getNumberOfInstances()!=null)
		{
			am.setNumberOfInstances(ar.getNumberOfInstances());
		}
		else
		{
			am.setNumberOfInstances(1);
		}
		
		am.setNumberOfInstances(ar.getNumberOfInstances());
		am.setNumberOfRecurringInstances(ar.getNumberOfRecurringInstances());
		am.setAverageRunTime(ar.getAverageRunTime());
		if(ar.getTotalSize()!=null)
		{
			am.setTotalSize(ar.getTotalSize());
		}
		else
		{
			am.setTotalSize(1);
		}
		
		am.setTotalUniverseCount(ar.getTotalUniverseCount());
		am.setNumberOfBlocks(ar.getNumberOfBlocks());
		am.setNumberOfFormulas(ar.getNumberOfFormulas());
		am.setNumberOfFilters(ar.getNumberOfFilters());
		am.setNumberOfTabs(ar.getNumberOfTabs());
		am.setNumberOfColumns(ar.getNumberOfColumns());
		am.setNumberOfQuery(ar.getNumberOfQuery());
		am.setUniverseId(ar.getUniverseId());
		am.setUniverseName(ar.getUniverseName());
		am.setUniversePath(ar.getUniversePath());
		am.setNumberOfFailures(ar.getNumberOfFailures());
		am.setNumberOfRows(ar.getNumberOfRows());
		am.setReportUser(ar.getReportUser());
		am.setCommonalityFactor(ar.getCommonalityFactor());
		am.setTableColumnMap(ar.getTableColumnMap());
		am.setQueryList(ar.getQueryList());
		am.setTableSet(ar.getTableSet());
		am.setChartSet(ar.getChartSet());
		am.setNumberOfReportPages(ar.getNumberOfReportPages());
		am.setNumberOfVariables(ar.getNumberOfVariables());
		am.setNumberOfConditionalBlocks(ar.getNumberOfConditionalBlocks());
		am.setPivotTableSet(ar.getPivotTableSet());
		am.setReportScheduled(ar.isReportScheduled());
		am.setReportPublished(ar.isReportPublished());
		am.setActivelyUsed(ar.isActivelyUsed());
		am.setExceptionReport(ar.getExceptionReport());
		// BO Exclusive
		am.setTabList(ar.getTabList());
		am.setNumberOfImages(ar.getNumberOfImages());
		am.setNumberOfEmbeddedElements(ar.getNumberOfEmbeddedElements());
		am.setComplexity(ar.getComplexity());
		am.setVariableList(ar.getVariableList());
		
		if(ar.getInputControl()!=null)
		{
			am.setInputControl(ar.getInputControl());
		}
		if(ar.getAlerters()!=null)
		{
			am.setAlerters(ar.getAlerters());
		}
		if(ar.getDataFilters()!=null)
		{
			am.setDataFilters(ar.getDataFilters());
		}
		
		if(ar.getQueryFilters()!=null)
		{
			am.setQueryFilters(ar.getQueryFilters());
		}
		
		am.setPageContainer(ar.getPageContainer());
		am.setPageCount(ar.getPageCount());
		am.setContainerCount(ar.getContainerCount());
		am.setPromptCount(ar.getPromptCount());
		am.setPromptPages(ar.getPromptPages());
		
		am.setConditionalBlocks(ar.getConditionalBlocks());
		
		am.setWorkbookName(ar.getWorkbookName());
		
		return am;
	}
	
	public static CommonalityReportModel commonalityReportModelBuilder(CommonalityReport c) {
		CommonalityReportModel cm = new CommonalityReportModel();
		cm.setId(c.getId());
		cm.setPrjRptAnalysisId(c.getPrjRptAnalysisId());
		cm.setAnalysisReport1(analysisReportModelBuilder(c.getAnalysisReport1()));
		cm.setAnalysisReport2(analysisReportModelBuilder(c.getAnalysisReport2()));
		cm.setCommonality(c.getCommonality());
		cm.setIdentical(c.isIdentical());
		return cm;
	}
	
	public static UniverseReportModel universeReportModelBuilder(UniverseReport u) {
		UniverseReportModel um = new UniverseReportModel();
		um.setId(u.getId());
		um.setName(u.getName());
		um.setDescription(u.getDescription());
		um.setUniverseSourceId(u.getUniverseSourceId());
		um.setPrjRptAnalysisId(u.getPrjRptAnalysisId());
		um.setItems(u.getItems());
		um.setTables(u.getTables());
		um.setJoins(u.getJoins());
		um.setDataSources(u.getDataSources());
		return um;
	}
	
	

	public static PrjRptAnalysisModel projectReportAnalysisModelBuilder(PrjRptAnalysis p) {
		PrjRptAnalysisModel pm = new PrjRptAnalysisModel();
		pm.setId(p.getId());
		pm.setProjectId(p.getProject().getId());
		pm.setTaskStatus(taskStatusModelBuilder(p.getTaskStatus()));
		pm.setReportTypeCode(p.getReportTypeCode());
		pm.setProjectReportConId(p.getProjectReportConId());
		pm.setTaskName(p.getTaskName());
		pm.setComment(p.getComment());
		pm.setSelectedReportsList(folderPathModel(p.getTaskFolderdetails()));
		return pm;
	}
	private static List<TaskSelectedReports> folderPathModel(List<FolderTask> taskFolderdetails) {
		// TODO Auto-generated method stub
	
		List<TaskSelectedReports> folderTaskList = new ArrayList<TaskSelectedReports>();
		Iterator<FolderTask> folderIterator = taskFolderdetails.iterator();
		
		while(folderIterator.hasNext())
		{
			TaskSelectedReports taskSelectedReportObj = new TaskSelectedReports();
			FolderTask folderTaskObj  = folderIterator.next();
			taskSelectedReportObj.setReportid(folderTaskObj.getReportId());
			taskSelectedReportObj.setReportname(folderTaskObj.getReportName());
			taskSelectedReportObj.setPath(folderTaskObj.getReportPath());
			folderTaskList.add(taskSelectedReportObj);
			
		}
		return folderTaskList;
			
	}
	
	public static ComplexityReportModel complexityReportModelBuilder(ComplexityReport c) {
		ComplexityReportModel cm = new ComplexityReportModel();
		
		cm.setComplexityId(c.getComplexityId());
		cm.setComplexityParameter(c.getComplexityParameter());
		cm.setComplexityStatus(c.getComplexityStatus());
		cm.setReportId(c.getReportId());
		cm.setReportName(c.getReportName());
		cm.setTaskId(c.getTaskId());
		
		return cm;
	}

	
	public static ReportTypeModel reportTypeModelBuilder(ReportType r) {
		ReportTypeModel rm = new ReportTypeModel();
		rm.setName(r.getName());
		rm.setCode(r.getCode());
		rm.setStatus(statusModelBuilder(r.getStatus()));
		return rm;
	}
	
	public static StatusModel statusModelBuilder(Status e) {
		StatusModel m = new StatusModel();
		m.setCode(e.getCode());
		m.setName(e.getName());
		return m;
	}
	
	public static RptConParamTypeModel rptConParamTypeModelBuilder(RptConParamType r) {
		RptConParamTypeModel rm = new RptConParamTypeModel();
		rm.setCode(r.getCode());
		rm.setName(r.getName());
		rm.setReportType(reportTypeModelBuilder(r.getReportType()));
		return rm;
	}
	
	public static ProjectReportConModel projectReportConModelBuilder(ProjectReportCon p) {
		ProjectReportConModel pm = new ProjectReportConModel();
		pm.setId(p.getId());
		pm.setProject(p.getProject().getName());
		pm.setName(p.getName());
		pm.setReportType(reportTypeModelBuilder(p.getReportType()));
		pm.setStatus(p.getStatus());
		pm.setPrjRptConParams(p.getPrjRptConParams().stream().map(ModelBuilder::prjRptConParamsModelBuilder).collect(Collectors.toSet()));
		return pm;
	}
	
	public static PrjRptConParamsModel prjRptConParamsModelBuilder(PrjRptConParams p) {
		PrjRptConParamsModel pm = new PrjRptConParamsModel();
		pm.setId(p.getId());
		pm.setProjectReportCon(p.getProjectReportCon().getName());
		pm.setRptConParamType(rptConParamTypeModelBuilder(p.getRptConParamType()));
		pm.setRptConParamValue(p.getRptConParamValue());
		return pm;
	}
	
	public static ProjectModel projectModelBuilder(Project p) {
		ProjectModel pm = new ProjectModel();
		pm.setId(p.getId());
		pm.setName(p.getName());
		pm.setStartDate(p.getStartDate());
		pm.setEndDate(p.getEndDate());
		pm.setStatus(statusModelBuilder(p.getStatus()));
		pm.setUsers(p.getUsers().stream().map(ModelBuilder::userModelBuilder).collect(Collectors.toList()));
		pm.setProjectReportCons(p.getProjectReportCons().stream().map(ModelBuilder::projectReportConModelBuilder).collect(Collectors.toSet()));
		return pm;
	}
	
	public static UserModel userModelBuilder(User appUser) {
		if (appUser == null) {
			return null;
		}
		UserModel userModel = new UserModel();
		userModel.setUserName(appUser.getUserName());
		userModel.setPassword(appUser.getPassword());
		userModel.setAccountEnabled(appUser.isAccountEnabled());
		userModel.setAccountLocked(appUser.isAccountLocked());
		userModel.setRoles(appUser.getRoles().stream().map(ModelBuilder::roleModelBuilder).collect(Collectors.toList()));
		userModel.setUserProfile(ModelBuilder.userProfileModelBuilder(appUser.getUserProfile()));
		userModel.setProjects(appUser.getProjects().stream().map(x -> x.getName()).collect(Collectors.toList()));
		return userModel;
	}
	public static UserProfileModel userProfileModelBuilder(UserProfile userProfile) {
		if (userProfile == null) {
			return null;
		}
		UserProfileModel userProfileModel = new UserProfileModel();
		userProfileModel.setId(userProfile.getId());
		userProfileModel.setName(userProfile.getName());
		userProfileModel.setEmailid(userProfile.getEmailid());
		userProfileModel.setMobileNo(userProfile.getMobileNo());		
		return userProfileModel;
	}
	
	public static ReportPathModel reportPathModelBuilder(List<ReportData> reportData, String path)
	{
		ReportPathModel rpm = new ReportPathModel();
		List<ReportPathDataModel> list = new LinkedList<ReportPathDataModel>();
		
		rpm.setPath(path);
		
		Iterator<ReportData> iter = reportData.iterator();
		
		while(iter.hasNext())
		{
			ReportData rData = iter.next();
			ReportPathDataModel dataModel = new ReportPathDataModel();
			
			dataModel.setDate(rData.getDate());
			dataModel.setReportId(rData.getReportId());
			dataModel.setReportName(rData.getReportName());
			dataModel.setSize(rData.getSize());
			dataModel.setUniverses(rData.getUniverses());
			list.add(dataModel);
		}
		rpm.setReports(list);
		
		
		return rpm;
		
	}
	
	
	
	public static ReportPathModel reportPathModelBuilderTableau(List<com.recast.recast.bo.analyzer.tableau.model.ReportData> reportData, String path)
	{
		ReportPathModel rpm = new ReportPathModel();
		List<ReportPathDataModel> list = new LinkedList<ReportPathDataModel>();
		
		rpm.setPath(path);
		
		Iterator<com.recast.recast.bo.analyzer.tableau.model.ReportData> iter = reportData.iterator();
		
		while(iter.hasNext())
		{
			com.recast.recast.bo.analyzer.tableau.model.ReportData rData = iter.next();
			ReportPathDataModel dataModel = new ReportPathDataModel();
			
			dataModel.setDate(rData.getUpdatedAt());
			dataModel.setReportId(rData.getId());
			dataModel.setReportName(rData.getName());
			dataModel.setSize(rData.getSize());
			dataModel.setUniverses(rData.getUniverses());
			list.add(dataModel);
		}
		rpm.setReports(list);

		return rpm;
	}

	
	public static RoleModel roleModelBuilder(Role role) {
		RoleModel roleModel = new RoleModel();
		roleModel.setRoleName(role.getRoleName());
		roleModel.setDescription(role.getDescription());
		return roleModel;
	}
	
	public static PrjRptMigratorModel projectReportMigratorModelBuilder(PrjRptMigrator p) {
		PrjRptMigratorModel pm = new PrjRptMigratorModel();
		pm.setId(p.getId());
		pm.setProjectId(p.getProject().getId());
		pm.setTaskStatus(taskStatusModelBuilder(p.getTaskStatus()));
		pm.setSourceReportConId(p.getProjectReportSourceConId());
		pm.setTargetReportConId(p.getProjectReportTargetConId());
		pm.setSourceReportTypeCode(p.getSourceReportTypeCode());
		pm.setTargetReportTypeCode(p.getTargetReportTypeCode());
		pm.setTaskName(p.getTaskName());
		pm.setComment(p.getComment());
		pm.setSourceTaskName(p.getSourceTaskName());
		pm.setSourceUniverseName(p.getSourceUniverseName());
		pm.setSourceUniverseDesc(p.getSourceUniverseDesc());
		return pm;
	}
}
