package com.recast.recast.bo.analyzer.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.recast.recast.bo.analyzer.dto.BoReportConfigDTO;
import com.recast.recast.bo.analyzer.dto.ReportConfigDTO;
import com.recast.recast.bo.analyzer.dto.TableauReportConfigDTO;
import com.recast.recast.bo.analyzer.entity.PrjRptConParams;
import com.recast.recast.bo.analyzer.entity.Project;
import com.recast.recast.bo.analyzer.entity.ProjectReportCon;
import com.recast.recast.bo.analyzer.model.ProjectModel;
import com.recast.recast.bo.analyzer.model.ProjectReportConModel;
import com.recast.recast.bo.analyzer.model.ReportTypeModel;
import com.recast.recast.bo.analyzer.model.RptConParamTypeModel;
import com.recast.recast.bo.analyzer.reportanalyser.ReportAnalyzerFactory;
import com.recast.recast.bo.analyzer.repository.PrjRptConParamsRepository;
import com.recast.recast.bo.analyzer.repository.ProjectReportConRepository;
import com.recast.recast.bo.analyzer.repository.ProjectRepository;
import com.recast.recast.bo.analyzer.repository.ReportTypeRepository;
import com.recast.recast.bo.analyzer.repository.RptConParamTypeRepository;
import com.recast.recast.bo.analyzer.util.BOConstants;
import com.recast.recast.bo.analyzer.util.BOReportAnalyzer;
import com.recast.recast.bo.analyzer.util.EntityBuilder;
import com.recast.recast.bo.analyzer.util.ModelBuilder;
import com.recast.recast.bo.analyzer.util.ReportConstants;
import com.recast.recast.bo.analyzer.util.TableauConstants;
import com.recast.recast.bo.analyzer.util.TableauReportAnalyzer;

@Service
public class ConnectionService {
	private static final Logger logger = LoggerFactory.getLogger(ConnectionService.class);
	@Autowired(required = false)
	private ReportTypeRepository reportTypeRepository;
	
	@Autowired(required = false)
	private ProjectRepository projectRepository;
	
	@Autowired(required = false)
	private RptConParamTypeRepository rptConParamTypeRepository;
	
	@Autowired(required = false)
	private ProjectReportConRepository projectReportConRepository;
	
	@Autowired(required = false)
	private PrjRptConParamsRepository prjRptConParamsRepository;
	
		
	public List<ReportTypeModel> getAllReportTypes() {
		return reportTypeRepository.findAll().stream().map(ModelBuilder::reportTypeModelBuilder).collect(Collectors.toList());
	}
	
	public List<RptConParamTypeModel> getParams(String code) {
		return rptConParamTypeRepository.findByReportTypeCode(code).stream().map(ModelBuilder::rptConParamTypeModelBuilder).collect(Collectors.toList());
	}
	
	@Transactional
	public ReportTypeModel getReportTypeByCode(String id) {
		return ModelBuilder.reportTypeModelBuilder(reportTypeRepository.getOne(id));
	}
	
	@Transactional
	public RptConParamTypeModel getRptConParamTypeModelByCode(String code) {
		return ModelBuilder.rptConParamTypeModelBuilder(rptConParamTypeRepository.getOne(code));
	}
	
	@Transactional
	public List<ProjectReportConModel> getConnections(int projectId) {
		return projectRepository.getOne(projectId).getProjectReportCons().stream().map(ModelBuilder::projectReportConModelBuilder).collect(Collectors.toList());
	}
	
	@Transactional
	public ProjectModel save(ProjectReportConModel pm, int projectId) {
		logger.debug("Inside Connection Service -> Save");
		Project p = projectRepository.getOne(projectId);
		pm.setReportType(getReportTypeByCode(pm.getReportType().getCode()));
		pm.getPrjRptConParams().forEach(x -> x.setRptConParamType(getRptConParamTypeModelByCode(x.getRptConParamType().getCode())));
		ProjectReportCon projectReportCon = EntityBuilder.projectReportConEntityBuilder(pm);
		projectReportCon.setProject(p);
		projectReportCon.getPrjRptConParams().forEach(x -> x.setProjectReportCon(projectReportCon));
		p.addProjectReportCon(projectReportCon);
		p = projectRepository.save(p);
		return ModelBuilder.projectModelBuilder(p);
	}
	
	@Transactional
	public String delete(int id) {
		ProjectReportCon p = projectReportConRepository.getOne(id);
		String name = p.getName();
		projectReportConRepository.deleteById(id);
		projectReportConRepository.flush();
		return "Connection: '" + name + "' deleted successfully.";
	}
	
	@Transactional
	public ProjectReportConModel getConnectionDetails(int connectionId) {
		return ModelBuilder.projectReportConModelBuilder(projectReportConRepository.findById(connectionId).get());
	}

	@Transactional
	public String getTestConnectionData(int connectionId,String reportType) {
		// TODO Auto-generated method stub
		
		/*ReportTypeModel reportTypeModel = pm.getReportType();
		
		String reportType = reportTypeModel.getCode();*/
		
		List<PrjRptConParams> conParams = prjRptConParamsRepository.findByProjectReportConId(connectionId);
		/*Set<PrjRptConParamsModel> connectionParamaSet = pm.getPrjRptConParams();
		
		Iterator<PrjRptConParamsModel> iter = connectionParamaSet.iterator();
		Map<String, String> conn = new HashMap<String, String>();
		
		while(iter.hasNext())
		{
			PrjRptConParamsModel data = iter.next();
		//	System.out.println("Connection Parameters:"+data);
			conn.put(data.getRptConParamType().getCode(), data.getRptConParamValue());	
		}
		*/
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
				
			//	System.out.println("BO Config::"+boConfigDTO.getHostname()+" "+boConfigDTO.getPath());
				
				/* Create Analyzer instance */
				BOReportAnalyzer analyzer = (BOReportAnalyzer) ReportAnalyzerFactory.getInstance().getReportAnalyzer((ReportConfigDTO) boConfigDTO);
				String res = analyzer.testBOAnalyzerConnection();
				return res;
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
				
				if (!tabConfigDTO.getPath().isEmpty()) {
					final String regex = "^([a-zA-Z]:)?(\\\\[^<>:\"/\\\\|?*]+)+\\\\?$";
					Pattern pattern = Pattern.compile(regex); 
					Matcher matcher = pattern.matcher(tabConfigDTO.getPath());
					boolean found = false;
					while (matcher.find()) {    
		                found = true;    
		            }    
		            if(!found){    
		                return "Fail For Path";    
		            } else {
		            	String fileExtension = tabConfigDTO.getPath().substring(tabConfigDTO.getPath().length()-3);
		            	if (fileExtension.equals("twb") || fileExtension.equals("xml")) {
		            		return "Success For Path";
		            	}
		            	else if (fileExtension.equals("zip")) {
		            		String filePath = tabConfigDTO.getPath().substring(0, tabConfigDTO.getPath().length()-3);
		            		System.out.println("File path:: " + filePath);
		            		return unzipFile(tabConfigDTO.getPath(), filePath);
		            		
		            	}
		            	return "Fail For Path";
		            }
				}
				
//				Creating analyzer instance
				TableauReportAnalyzer analyzer = (TableauReportAnalyzer) ReportAnalyzerFactory.getInstance().getReportAnalyzer((ReportConfigDTO) tabConfigDTO);
				String res = analyzer.testTableauAnalyzerConnection();
				return res;
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
			return "Fail";	
		}

		return "Success";
		
	}
	
	private static String unzipFile(String zipFilePath, String destDir) {
		File dir = new File(destDir);
		// create output directory if it doesn't exist
		if (!dir.exists())
			dir.mkdirs();
		FileInputStream fis;
		// buffer for read and write data to file
		byte[] buffer = new byte[1024];
		
		try
		{
			fis = new FileInputStream(zipFilePath);
			ZipInputStream zis = new ZipInputStream(fis);
			ZipEntry ze = zis.getNextEntry();
			
//			String finalFolder = getAlphaNumericString(6);
			
			while (ze != null) 
			{
				String fileName = ze.getName();
				String fileExtension = fileName.substring(fileName.length() - 3);
				System.out.println("File name:::" + fileName);
				
				String finalFileName = fileName.substring(fileName.lastIndexOf("/"));
				
				if (!(fileExtension.equals("twb") || fileExtension.equals("xml"))) {
					return "Fail For Path";
				}
				
				File newFile = new File(destDir + File.separator + finalFileName);
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
			
		}
		catch (IOException e)
		{
			System.out.println("File Unzip IOException in Service:::" + e);
		}
		catch (Exception e)
		{
			System.out.println("File Unzip Exception in Service:::" + e);
		}
		
		return "Success For Path";
	}
		
}
