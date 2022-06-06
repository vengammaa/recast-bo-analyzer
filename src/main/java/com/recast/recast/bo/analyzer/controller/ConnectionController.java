package com.recast.recast.bo.analyzer.controller;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.recast.recast.bo.analyzer.entity.ProjectReportCon;
import com.recast.recast.bo.analyzer.model.ProjectModel;
import com.recast.recast.bo.analyzer.model.ProjectReportConModel;
import com.recast.recast.bo.analyzer.model.ReportPathModel;
import com.recast.recast.bo.analyzer.model.ReportTypeModel;
import com.recast.recast.bo.analyzer.model.RptConParamTypeModel;
import com.recast.recast.bo.analyzer.repository.ProjectReportConRepository;
import com.recast.recast.bo.analyzer.service.ComplexityService;
import com.recast.recast.bo.analyzer.service.ConnectionService;
import com.recast.recast.bo.analyzer.service.ReportPathService;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/connections")
public class ConnectionController {
	private static final Logger logger = LoggerFactory.getLogger(ConnectionController.class);
	
	@Autowired(required = false)
	private ConnectionService connectionService;
	
	@Autowired(required = false)
	private ConnectionReportController connectionReportController;
	
	@Autowired(required = false)
	ReportPathService reportPathService;
	
	@Autowired(required = false)
	private ProjectReportConRepository projectReportConRepository;
	
	@Autowired(required = false)
	private ComplexityService complexityService;
	

	@GetMapping("/reportTypes")
	public List<ReportTypeModel> getAllReportTypes() {
		logger.info("---Inside ConnectionController -> reportTypes---");
		return connectionService.getAllReportTypes();
	}

	@GetMapping("/params/{code}")
	public List<RptConParamTypeModel> getParams(@PathVariable String code) {
		return connectionService.getParams(code);
	}

	@PostMapping("/addConnection/{projectId}")
	public ProjectModel addConnection(@RequestBody ProjectReportConModel p, @PathVariable int projectId) {
		return connectionService.save(p, projectId);
	}
	
	@GetMapping("getConnections/{projectId}")
	public List<ProjectReportConModel> getConnection(@PathVariable int projectId) {
		System.out.println("API Called::");
		return connectionService.getConnections(projectId);
	}
	
	@DeleteMapping("deleteConnection/{connectionId}")
	public String deleteConnection(@PathVariable int connectionId) {
		return connectionService.delete(connectionId);
	}
	
	@GetMapping("getConnectionDetails/{connectionId}")
	public ProjectReportConModel getConnectionDetails(@PathVariable int connectionId) {
		logger.debug("Inside get connection details");
		return connectionService.getConnectionDetails(connectionId);
	}
	
	@PutMapping("editConnection/{projectId}")
	public ProjectModel editConnection(@RequestBody ProjectReportConModel pm, @PathVariable int projectId)
	{
		logger.info("Inside edit connection");
		connectionService.delete(pm.getId());
		return connectionService.save(pm, projectId);
	}
	
	
	@PostMapping("/uploadFile")
	public ResponseEntity<String> uploadFile(@RequestPart("file") MultipartFile file) {
		if (null == file.getOriginalFilename()) {
			return new ResponseEntity<>("Failed to upload" ,HttpStatus.BAD_REQUEST);
		}
		
		Path path = null;
		
		try {
			byte[] bytes = file.getBytes();
			
			FileReader fileReader = new FileReader(getClass().getClassLoader().getResource("").getPath().substring(1).replaceAll("%20", " ")+"/tableau/tableau.properties");
			Properties p=new Properties();
			p.load(fileReader);
			  
			// System.out.println(p.getProperty("CSVOutputPath"));
			String twbOutputPath = p.getProperty("TableauOutputPath");
			String extraFolder = getAlphaNumericString(8);
			
			File dir = new File(twbOutputPath + extraFolder + "//");
			// create output directory if it doesn't exist
			if (!dir.exists())
				dir.mkdirs();
			
			path = Paths.get(twbOutputPath + extraFolder + "//" + file.getOriginalFilename());
			
			Files.write(path, bytes);
			System.out.println(path.getFileName());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return new ResponseEntity<>(path.toString(), HttpStatus.OK);
	}

	
	
	@GetMapping("/testConnection/{connectionId}/{reportType}")
	public String testConnection(@PathVariable int connectionId,@PathVariable String reportType)
	{
		
		String res = connectionService.getTestConnectionData(connectionId,reportType);
		ProjectReportCon prjRptDetails = projectReportConRepository.findById(connectionId).get();
		
		if (res.equals("Fail For Path")) {
			prjRptDetails.setStatus(res);
			projectReportConRepository.save(prjRptDetails);
			return "Failed!";
		} else if (res.equals("Success For Path")) {
			prjRptDetails.setStatus(res);
			projectReportConRepository.save(prjRptDetails);
			return "Success!";
		}
		
		prjRptDetails.setStatus(res);
		projectReportConRepository.save(prjRptDetails);
		connectionReportController.getreportdata(connectionId,reportType);	
		return res;
		
	}
	
	
	@GetMapping("/reportPath/{connectionId}/{reportType}")
	public List<ReportPathModel> getReportFolderStructure(@PathVariable int connectionId, @PathVariable String reportType)
	{
		List<ReportPathModel> reportModelList = reportPathService.fetchReportFolderPath(connectionId, reportType);
		return reportModelList;
	}
	
	static String getAlphaNumericString(int n)
    {
  
        // length is bounded by 256 Character
        byte[] array = new byte[256];
        new Random().nextBytes(array);
  
        String randomString
            = new String(array, Charset.forName("UTF-8"));
  
        // Create a StringBuffer to store the result
        StringBuffer r = new StringBuffer();
  
        // Append first 20 alphanumeric characters
        // from the generated random String into the result
        for (int k = 0; k < randomString.length(); k++) {
  
            char ch = randomString.charAt(k);
  
            if (((ch >= 'a' && ch <= 'z')
                 || (ch >= 'A' && ch <= 'Z')
                 || (ch >= '0' && ch <= '9'))
                && (n > 0)) {
  
                r.append(ch);
                n--;
            }
        }
  
        // return the resultant string
        return r.toString();
    }
	
	@GetMapping("/logComplex")
    public String divisionNum() {
        return complexityService.classifyBOComplexityReport("Hi");
    }
	
}
	
	
	
	
	

