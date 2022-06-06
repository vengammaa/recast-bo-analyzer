package com.recast.recast.bo.analyzer.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.recast.recast.bo.analyzer.model.ReportTabModel;
import com.recast.recast.bo.analyzer.repository.VisualDetailsRepository;

@Service
public class ReportTabService {
	
	@Autowired(required = false)
	private VisualDetailsRepository visualDetailsRepository;
	
	@Transactional
	public List<ReportTabModel> getReportElementList(int taskId) throws Exception {
		
		List<ReportTabModel> reportTabList = new ArrayList<ReportTabModel>();
		try {
		
		
		List<List<String>> visualDetailsList = visualDetailsRepository.findByReportTab(taskId);
		
		Map<String,String> elementIdMap = new HashMap<>();	
		for(int j=0;j<visualDetailsList.size();j++) {	
			List<String> visualDetails =visualDetailsList.get(j);	
			elementIdMap.put(visualDetails.get(9), visualDetails.get(2));	
		}
		
		for(int i=0;i<visualDetailsList.size();i++)
		{
			List<String> visualDetails =visualDetailsList.get(i);
			ReportTabModel reportTabModel = new ReportTabModel();
			reportTabModel.setReportId(Integer.parseInt(visualDetails.get(0)));
			reportTabModel.setReportTabName(visualDetails.get(1));
			reportTabModel.setElementName(visualDetails.get(2));
			reportTabModel.setElementType(visualDetails.get(3));
			int idx1,idx2;
			
			if(visualDetails.get(4).contains("role")) {	
				JSONArray json = new JSONArray(visualDetails.get(4));	
				int size=json.length();	
				String role="";	
				for(int j=0;j<size;j++) {	
					if(visualDetails.get(3).contains("HTable"))	
						role+="Rows";	
					else if(visualDetails.get(3).contains("VTable"))	
						role+="Columns";	
					else	
					role+=json.getJSONObject(j).get("name") ;	
					role+=" : ";	
						
					String formula=json.getJSONObject(j).get("formulaList").toString();	
					
					if(formula=="null") {	
						role+="<< Null >>";	
					}	
					else {	
						JSONArray formulaList = new JSONArray(formula);	
						int size2=formulaList.length();	
							
						for(int k=0;k<size2;k++) {	
							role+="\"";	
							role+=formulaList.getJSONObject(k).get("name");	
							role+="\"";	
							if(k<size2-1) role+=", ";	
							//System.out.print(formulaList.getJSONObject(k).get("name"));	
						}	
					}	
						
//					if(formulaList.length()==0) {	
//						role+="<<NULL>>";	
//						//System.out.print("<<NULL>>");	
//					}	
////					if(json.getJSONObject(j).getString("formulaList").isEmpty())	
////						System.out.print("<<NULL>>");	
					role+=" ";	
					if(j<size-1) role+="\r\n";	
				}	
					
//				JSONParser parser = new JSONParser();  	
//				JSONArray json = (JSONArray) parser.parse(visualDetails.get(4));	
//				int size=json.size();	
//				for(int j=0;j<size;j++) {	
//					json.get(j)	
//				}	
				//idx1= visualDetails.get(4).indexOf("{");	
				//idx2= visualDetails.get(4).indexOf(":");	
				reportTabModel.setDataAxisInfo(role);	
			}	
			else if(visualDetails.get(4).contains("formula")) {
				System.out.println(visualDetails.get(4).toString());
				if(visualDetails.get(4).contains("=")) {
				idx1= visualDetails.get(4).indexOf("=");
				}
				else if(visualDetails.get(4).contains("\""))
				idx1=visualDetails.get(4).indexOf("\"");
				else
				idx1=11;

				idx2= visualDetails.get(4).indexOf(",");
				
				if(visualDetails.get(4).length()>idx1 && visualDetails.get(4).length()>idx2 && idx1<idx2)
				reportTabModel.setDataAxisInfo(" "+visualDetails.get(4).substring(idx1, idx2));
				

				}	
			else if(visualDetails.get(4).contains("[]")) {	
				reportTabModel.setDataAxisInfo("<< Null >>");
			}	
			else if(visualDetails.get(2).contains("Unknown")){	
				reportTabModel.setDataAxisInfo("<< This report element is not supported in current version of Recast. Manual intervention required. >>");
			}
			else {	
				reportTabModel.setDataAxisInfo("<< Not Applicable >>");	
			}	
				
			reportTabModel.setLayoutDetails("X Position: " + visualDetails.get(5) +	
					", " + "Y Position: " + visualDetails.get(6) + ", " + "Height: " + 	
					visualDetails.get(7) + ", " + "Width: " + visualDetails.get(8));	
			
				
//			//System.out.println(visualDetails.get(10).isBlank());	
//			System.out.println(visualDetails.get(10)==null);	
//			System.out.println(visualDetails.get(10)=="null");	
//			//System.out.println(visualDetails.get(10).isEmpty());	
//			System.out.println(visualDetails.get(10).length());	
				
				
			if(visualDetails.get(10)!=null)	
				reportTabModel.setParentElementName(elementIdMap.get(visualDetails.get(10)));	
			else	
				reportTabModel.setParentElementName(visualDetails.get(2));
			reportTabList.add(reportTabModel);
		}
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return reportTabList;	
	}

}
