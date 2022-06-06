package com.recast.recast.bo.analyzer.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recast.recast.bo.analyzer.dto.BoReportConfigDTO;
import com.recast.recast.bo.analyzer.dto.ReportConfigDTO;
import com.recast.recast.bo.analyzer.dto.TableauReportConfigDTO;
import com.recast.recast.bo.analyzer.entity.ConnectionPath;
import com.recast.recast.bo.analyzer.entity.PrjRptConParams;
import com.recast.recast.bo.analyzer.entity.ReportPath;
import com.recast.recast.bo.analyzer.model.ReportData;
import com.recast.recast.bo.analyzer.reportanalyser.ReportAnalyzerFactory;
import com.recast.recast.bo.analyzer.repository.ConnectionPathRepository;
import com.recast.recast.bo.analyzer.repository.PrjRptConParamsRepository;
import com.recast.recast.bo.analyzer.repository.ReportPathRepository;
import com.recast.recast.bo.analyzer.util.BOConstants;
import com.recast.recast.bo.analyzer.util.BOReportAnalyzer;
import com.recast.recast.bo.analyzer.util.ReportConstants;
import com.recast.recast.bo.analyzer.util.TableauConstants;
import com.recast.recast.bo.analyzer.util.TableauReportAnalyzer;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class ConnectionReportController {
	@Autowired(required = false)
	private ConnectionPathRepository connectionPathRepository;
	
	@Autowired(required = false)
	private PrjRptConParamsRepository prjRptConParamsRepository;
	
	@Autowired(required = false)
	private ReportPathRepository reportPathRepository;
	
	
	//@GetMapping(value="/getFolder/{connectionId}/{reportType}")

	public void getreportdata(int connectionId,String reportType)
	{
		List<ConnectionPath> check = connectionPathRepository.findByConnectionId(connectionId);
		if(check.isEmpty())
		{
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
				
				BOReportAnalyzer analyzer = (BOReportAnalyzer) ReportAnalyzerFactory.getInstance().getReportAnalyzer((ReportConfigDTO) boConfigDTO);
		
		Map<String,List<ReportData>> map = analyzer.reportPathExtraction();
		
		for(String key : map.keySet())
		{
			ConnectionPath connectionPath = new ConnectionPath();
			connectionPath.setConnectionId(connectionId);
			connectionPath.setPathName(key);
			connectionPathRepository.save(connectionPath);
			List<ConnectionPath> tableDetails = connectionPathRepository.findByConnectionIdAndPathName(connectionId,key);
			ConnectionPath cp = tableDetails.get(0);
			Integer pathId = cp.getPathId();
			List<ReportData> l = map.get(key);
			for(int i=0;i<l.size();i++)
			{
				ReportData reportData = l.get(i);
				ReportPath reportPath = new ReportPath();
				reportPath.setPathId(pathId);
				reportPath.setReportId(reportData.getReportId());
				reportPath.setReportName(reportData.getReportName());
				reportPath.setReportSize(reportData.getSize());
				reportPath.setReportDate(reportData.getDate());
				
				List<String> reportUniversesList = new ArrayList<>(reportData.getUniverses());
				 String delim = ",";
				 
			        StringBuilder sb = new StringBuilder();
			 
			        int j = 0;
			        while (j < reportUniversesList.size() - 1)
			        {
			            sb.append(reportUniversesList.get(j));
			            sb.append(delim);
			            j++;
			        }
			        sb.append(reportUniversesList.get(j));
			        
			        String reportUniverses = sb.toString();
			        reportPath.setUniverses(reportUniverses);
			        reportPathRepository.save(reportPath);
			}
			
		}
			}
			else if (reportType.equalsIgnoreCase(ReportConstants.REPORT_TYPE_TABLEAU))
			{
				TableauReportConfigDTO tabConfigDTO = new TableauReportConfigDTO();
				tabConfigDTO.setReportType(ReportConstants.REPORT_TYPE_TABLEAU);
				tabConfigDTO.setHostname(conn.get(TableauConstants.HOST_NAME));
				tabConfigDTO.setPort(conn.get(TableauConstants.PORT));
				tabConfigDTO.setUsername(conn.get(TableauConstants.USERNAME));
				tabConfigDTO.setPassword(conn.get(TableauConstants.PASSWORD));
				tabConfigDTO.setPath(conn.get(TableauConstants.PATH));
				tabConfigDTO.setContentUrl(conn.get(TableauConstants.CONTENT_URL));
				tabConfigDTO.setConnectionType(conn.get(TableauConstants.CONNECTION_TYPE));
				
//				Creating analyzer instance
				TableauReportAnalyzer analyzer = (TableauReportAnalyzer) ReportAnalyzerFactory.getInstance().getReportAnalyzer((ReportConfigDTO) tabConfigDTO);
				
				Map<String,List<com.recast.recast.bo.analyzer.tableau.model.ReportData>> map = analyzer.reportPathExtraction();
				
				System.out.println("report data map in crc:::" + map);
				
				for (String key : map.keySet())
				{
					ConnectionPath connectionPath = new ConnectionPath();
					connectionPath.setConnectionId(connectionId);
					connectionPath.setPathName(key);
					connectionPathRepository.save(connectionPath);
					List<ConnectionPath> tableDetails = connectionPathRepository.findByConnectionIdAndPathName(connectionId,key);
					ConnectionPath cp = tableDetails.get(0);
					Integer pathId = cp.getPathId();
					List<com.recast.recast.bo.analyzer.tableau.model.ReportData> l = map.get(key);
					for (int i=0;i<l.size();i++)
					{
						com.recast.recast.bo.analyzer.tableau.model.ReportData reportData = l.get(i);
						ReportPath reportPath = new ReportPath();
						reportPath.setPathId(pathId);
						reportPath.setReportId(reportData.getId());
						reportPath.setReportName(reportData.getName());
						reportPath.setReportSize(reportData.getSize());
						reportPath.setReportDate(reportData.getUpdatedAt());
						
//						List<String> reportUniversesList = new ArrayList<>(reportData.getUniverses());
						List<String> reportUniversesList = new ArrayList<>();
						reportUniversesList.add("test datasource");
						
						String delim = ",";
						StringBuilder sb = new StringBuilder();
						
						System.out.println("report data:::" + reportData);
						 
				        int j = 0;
				        System.out.println("report univ size:::" +  reportUniversesList.size());
				        if (reportUniversesList.size() != 0)
				        {
				        	while (j < reportUniversesList.size() - 1)
					        {
					        	System.out.println("j:::" + j);
					            sb.append(reportUniversesList.get(j));
					            sb.append(delim);
					            j++;
					        }
				        	sb.append(reportUniversesList.get(j));
					        
					        String reportUniverses = sb.toString();
					        reportPath.setUniverses(reportUniverses);
					        reportPathRepository.save(reportPath);
				        }
				        
				        reportPathRepository.save(reportPath);
						
					}
				}
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		}
	}

}
