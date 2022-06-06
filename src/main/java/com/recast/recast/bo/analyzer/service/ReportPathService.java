package com.recast.recast.bo.analyzer.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.recast.recast.bo.analyzer.dto.BoReportConfigDTO;
import com.recast.recast.bo.analyzer.dto.ReportConfigDTO;
import com.recast.recast.bo.analyzer.entity.ConnectionPath;
import com.recast.recast.bo.analyzer.entity.PrjRptConParams;
import com.recast.recast.bo.analyzer.entity.ReportPath;
import com.recast.recast.bo.analyzer.model.ReportData;
import com.recast.recast.bo.analyzer.model.ReportPathModel;
import com.recast.recast.bo.analyzer.reportanalyser.ReportAnalyzerFactory;
import com.recast.recast.bo.analyzer.repository.ConnectionPathRepository;
import com.recast.recast.bo.analyzer.repository.PrjRptConParamsRepository;
import com.recast.recast.bo.analyzer.repository.ReportPathRepository;
import com.recast.recast.bo.analyzer.util.BOConstants;
import com.recast.recast.bo.analyzer.util.BOReportAnalyzer;
import com.recast.recast.bo.analyzer.util.ModelBuilder;
import com.recast.recast.bo.analyzer.util.ReportConstants;

@Service
public class ReportPathService {

	
	@Autowired(required = false)
	private PrjRptConParamsRepository prjRptConParamsRepository;
	
	@Autowired(required = false)
	private ConnectionPathRepository connectionPathRepository;
	
	@Autowired(required = false)
	private ReportPathRepository reportPathRepository;
	
	@Transactional
	public List<ReportPathModel> fetchReportPath(String reportType, int connectionId) {
		// TODO Auto-generated method stub
		
		List<ReportPathModel> pathModelList = new LinkedList<ReportPathModel>();
		
		
		List<PrjRptConParams> conParams = prjRptConParamsRepository.findByProjectReportConId(connectionId);
		Map<String, String> conn = new HashMap<String, String>();
		conParams.forEach(x -> conn.put(x.getRptConParamType().getCode(), x.getRptConParamValue()));
		try
		{
			if(reportType.equalsIgnoreCase(ReportConstants.REPORT_TYPE_BO))
			{
				BoReportConfigDTO boConfigDTO = new BoReportConfigDTO();
				boConfigDTO.setReportType(ReportConstants.REPORT_TYPE_BO);
				boConfigDTO.setHostname(conn.get(BOConstants.HOST_NAME));
				boConfigDTO.setPort(conn.get(BOConstants.PORT));
				boConfigDTO.setUsername(conn.get(BOConstants.USERNAME));
				boConfigDTO.setPassword(conn.get(BOConstants.PASSWORD));
				//boConfigDTO.setPath(conn.get(BOConstants.PATH));
				
				/* Create Analyzer instance */
				BOReportAnalyzer analyzer = (BOReportAnalyzer) ReportAnalyzerFactory.getInstance().getReportAnalyzer((ReportConfigDTO) boConfigDTO);
				
				Map<String,List<ReportData>> reportPathMap = analyzer.reportPathExtraction();
				
				System.out.println("Report Map in service::"+reportPathMap);
				
				for (Map.Entry<String, List<ReportData>> entry: reportPathMap.entrySet())
				{
				 
				    String reportPath = entry.getKey();
				    List<ReportData> reportData = entry.getValue();
				    
				    ReportPathModel reportPathModel = ModelBuilder.reportPathModelBuilder(reportData, reportPath);
				    
				    pathModelList.add(reportPathModel);
				}
				
				
			}
		}
		catch (Exception e) {
			e.printStackTrace();
				//	return "Fail";	
		}
		
		
		return pathModelList;

	}
	
	@Transactional
	public List<ReportPathModel> fetchReportFolderPath(int connectionId, String reportType) {
		List<ReportPathModel> pathModelList = new LinkedList<ReportPathModel>();
		
		
		
		List<ConnectionPath> rowDetails = connectionPathRepository.findByConnectionId(connectionId);
		
		try 
		{
			if (reportType.equalsIgnoreCase(ReportConstants.REPORT_TYPE_BO))
			{
				Map<String,List<ReportData>> reportPathMap = new LinkedHashMap<String,List<ReportData>>(); 
				for(int i=0;i<rowDetails.size();i++)
				{
					ConnectionPath connectionPath = rowDetails.get(i);
					int pathId = connectionPath.getPathId();
					String reportFolderPathName = connectionPath.getPathName();
					List<ReportPath> reportRowDetails = reportPathRepository.findByPathId(pathId);
					List<ReportData> reportDataList = new ArrayList<ReportData>();
					for(int j=0;j<reportRowDetails.size();j++)
					{
						ReportPath reportPath =reportRowDetails.get(j);
						ReportData reportData = new ReportData();
						reportData.setReportId(reportPath.getReportId());
						reportData.setReportName(reportPath.getReportName());
						reportData.setSize(reportPath.getReportSize());
						reportData.setDate(reportPath.getReportDate());
						String[] universes = reportPath.getUniverses().split(",");
						Set<String> reportUniverses = new HashSet<>(Arrays.asList(universes));
						reportData.setUniverses(reportUniverses);
						reportDataList.add(reportData);
					}
					reportPathMap.put(reportFolderPathName, reportDataList);
				}
				System.out.println("Report Map in service::"+reportPathMap);
				
				for (Map.Entry<String, List<ReportData>> entry: reportPathMap.entrySet())
				{
				 
				    String reportPath = entry.getKey();
				    List<ReportData> reportData = entry.getValue();
				    
				    ReportPathModel reportPathModel = ModelBuilder.reportPathModelBuilder(reportData, reportPath);
				    
				    pathModelList.add(reportPathModel);
				}
			}
			else if (reportType.equalsIgnoreCase(ReportConstants.REPORT_TYPE_TABLEAU))
			{
				Map<String,List<com.recast.recast.bo.analyzer.tableau.model.ReportData>> reportPathMap = new LinkedHashMap<String,List<com.recast.recast.bo.analyzer.tableau.model.ReportData>>(); 
				for (int i=0; i<rowDetails.size();i++)
				{
					ConnectionPath connectionPath = rowDetails.get(i);
					int pathId = connectionPath.getPathId();
					String reportFolderPathName = connectionPath.getPathName();
					List<ReportPath> reportRowDetails = reportPathRepository.findByPathId(pathId);
					List<com.recast.recast.bo.analyzer.tableau.model.ReportData> reportDataList = new ArrayList<com.recast.recast.bo.analyzer.tableau.model.ReportData>();
					for (int j=0;j<reportRowDetails.size();j++)
					{
						ReportPath reportPath =reportRowDetails.get(j);
						com.recast.recast.bo.analyzer.tableau.model.ReportData reportData = new com.recast.recast.bo.analyzer.tableau.model.ReportData();
						reportData.setId(reportPath.getReportId());
						reportData.setName(reportPath.getReportName());
						reportData.setSize(reportPath.getReportSize());
						reportData.setUpdatedAt(reportPath.getReportDate());
						String[] universes = reportPath.getUniverses().split(",");
						Set<String> reportUniverses = new HashSet<>(Arrays.asList(universes));
						reportData.setUniverses(reportUniverses);
						reportDataList.add(reportData);
					}
					reportPathMap.put(reportFolderPathName, reportDataList);
				}
				System.out.println("Report Map in service::"+reportPathMap);
				
				for (Map.Entry<String, List<com.recast.recast.bo.analyzer.tableau.model.ReportData>> entry: reportPathMap.entrySet())
				{
				 
				    String reportPath = entry.getKey();
				    List<com.recast.recast.bo.analyzer.tableau.model.ReportData> reportData = entry.getValue();
				    
				    ReportPathModel reportPathModel = ModelBuilder.reportPathModelBuilderTableau(reportData, reportPath);
				    
				    pathModelList.add(reportPathModel);
				}
			}
		}
			catch (Exception e) {
				e.printStackTrace();
			}

		return pathModelList;
	}
	
	

}
