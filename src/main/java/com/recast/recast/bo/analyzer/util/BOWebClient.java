package com.recast.recast.bo.analyzer.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class BOWebClient {
	
	private static Logger logger = Logger.getLogger(BOWebClient.class);
	private String token;
	private String baseUrl;
	private String logOffURL;
	private static final String REQUEST_METHOD_POST = "POST";
	private static final String REQUEST_METHOD_GET = "GET";
	private static final String REQUEST_PROPERTY_ACCEPT = "Accept";
	private static final String REQUEST_PROPERTY_APPL_XML = "application/xml";
	private static final String REQUEST_PROPERTY_APPL_JSON = "application/json";
	private static final String REQUEST_PROPERTY_CONTENT_TYPE = "Content-Type";
	private static final String REQUEST_PROPERTY_X_SAP_LOGONTOKEN = "X-SAP-LogonToken";
	private static String REQUEST_PROPERTY_X_SAP_LOGONTOKEN_VALUE;
	private static final String WEBI_REPORT_TYPE = "Webi";
	
	public BOWebClient(String host, String port, String username, String password) {
		try {
			token = logonAndGetToken(host, port, username, password);
			System.out.println("Logon Token Test 2::"+token);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		baseUrl = "http://" + host + ":" + port + "/biprws/raylight/v1";
		logOffURL = "http://" + host + ":" + port + "/biprws/logoff";
	}
	
	public String get(String uri, String content, String accept) {
		StringBuilder output = new StringBuilder();
		try {
			URL url = new URL(baseUrl + uri);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(REQUEST_METHOD_GET);
			conn.setRequestProperty(REQUEST_PROPERTY_CONTENT_TYPE, content);
			conn.setRequestProperty(REQUEST_PROPERTY_ACCEPT, accept);
			
			//System.out.println("Token Inside get method:::"+token);
			
			conn.setRequestProperty(REQUEST_PROPERTY_X_SAP_LOGONTOKEN,"\""+token+"\"");
			
			//System.out.println("REQUEST_PROPERTY_X_SAP_LOGONTOKEN"+REQUEST_PROPERTY_X_SAP_LOGONTOKEN);
			
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			
			String temp;
			while ((temp = br.readLine()) != null) {
				//System.out.println(temp);
				output.append(temp);
			}
			if(conn != null){
				conn.disconnect();
				br.close();
			}
		} 
		catch(Exception ex) {
			//logger.error(ex);
			//ex.printStackTrace();
			//ex.printStackTrace();
		}
		return output.toString();
	}
	
	
	
	public String logonAndGetToken(String host, String port, String username, String password) throws MalformedURLException,IOException {
		
		StringBuilder output = new StringBuilder("");
		
		try {
			URL url = new URL("http://"+ host +":"+ port +"/biprws/logon/long");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod(REQUEST_METHOD_POST);
			conn.setRequestProperty(REQUEST_PROPERTY_CONTENT_TYPE, REQUEST_PROPERTY_APPL_XML);
			conn.setRequestProperty(REQUEST_PROPERTY_ACCEPT,REQUEST_PROPERTY_APPL_XML);
			String input = "<attrs xmlns=\"http://www.sap.com/rws/bip\">"
					+ "<attr name=\"userName\" type=\"string\">" + username
							+ "</attr><attr name=\"password\" type=\"string\">" + password
									+ "</attr><attr name=\"auth\" type=\"string\" possibilities=\"secEnterprise,secLDAP,secWinAD,secSAPR3\">"
											+ AuthMode.ENTERPRISE.getAuthMode()
											+ "</attr>"
											+ "</attrs>";
			
			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();
	
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
	
			String tempReadLine = br.readLine();
			while ( tempReadLine != null) {
				output.append(tempReadLine);
				tempReadLine = br.readLine();
			}
			if("".equals(output)){
				return null;
			}
			String strToken = output.toString().substring(output.toString().indexOf("logonToken") + 26, output.toString().indexOf("</attr>"));
			String strOutput = strToken.toString().replaceAll("amp;", "");
			output.setLength(0);
			output.append(strOutput);
			
			if(conn != null){
				conn.disconnect();
				br.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		//System.out.println("Token: " + output.toString());
		return output.toString();
	}
	
	public String fetchListOfUniverses(Integer limit, Integer offset) {
		String out = "";
		try {
			String uri = "/universes?offset=" + offset + "&limit=" + limit;
			out = get(uri, REQUEST_PROPERTY_APPL_XML, REQUEST_PROPERTY_APPL_XML);
			
		} 
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return out;
	}
	
	public String fetchUniverseData(String universeId) {
		String out = "";
		try {
			String uri = "/universes/" + universeId + "?aggregated=true";
			out = get(uri, REQUEST_PROPERTY_APPL_XML, REQUEST_PROPERTY_APPL_XML);
		} 
		catch(Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
		}
		return out;
	}
	
	public String fetchConnections() {
		String out = "";
		try {
			String uri = "/connections";
			out = get(uri, REQUEST_PROPERTY_APPL_XML, REQUEST_PROPERTY_APPL_XML);
			
		} 
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return out;
	}
	
	public String fetchConnectionsData(String id) {
		String out = "";
		try {
			String uri = "/connections/" + id;
			out = get(uri, REQUEST_PROPERTY_APPL_XML, REQUEST_PROPERTY_APPL_XML);
			
		} 
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return out;
	}
	

	

	
//	public List<BOVariable> fetchVariables() {
//		
//	}
	
	
	public static void main(String args[]) throws Exception {
		BOWebClient webClient = new BOWebClient("dl5802.Ltisap.com", "8080", "10663796", "4pplemUffin");
		String ip = webClient.fetchUniverseData("5676");
		//String ip = webClient.fetchListOfUniverses(50, 0);
		//String ip = webClient.fetchConnections();
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = builder.parse(new InputSource(new StringReader(ip)));
		
//		NodeList list = doc.getElementsByTagName("universe");
//		for(int i = 0; i < list.getLength(); i++) {
//			Node node = list.item(i);
//			System.out.println("Current Node: " + node.getNodeName());
//			if (node.getNodeType() == Node.ELEMENT_NODE) {
//				Element e = (Element) node;
//				//System.out.println(webClient.fetchUniverseData(e.getAttribute("id")));
//				String id = e.getElementsByTagName("id")
//		                  .item(0)
//		                  .getTextContent();
//				System.out.println("Current ID : " + id);
//				System.out.println(webClient.fetchUniverseData(id));
//			}
//			System.out.println("================================================================");
//		}
		
//		Map<String, List<BOItem>> itemMap = webClient.fetchDetails(doc);
//		itemMap.forEach((k, v) -> {
//			System.out.println("============================ " + k + " List ============================");
//			v.forEach(x -> {
//				System.out.println(x.toString());
//			});
//		});
	}

public void logOut(){
		
		System.out.println("Log off Web API called");
		
		try {
			URL url = new URL(logOffURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(REQUEST_METHOD_POST);
			conn.setRequestProperty(REQUEST_PROPERTY_CONTENT_TYPE, REQUEST_PROPERTY_APPL_XML);
			conn.setRequestProperty(REQUEST_PROPERTY_ACCEPT, REQUEST_PROPERTY_APPL_XML);
			conn.setRequestProperty(REQUEST_PROPERTY_X_SAP_LOGONTOKEN, "\""+token+"\"");
			
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			
			String output;
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
			if(conn != null){
				conn.disconnect();
				br.close();
			}
		} catch(Exception ex) {
			logger.error(ex);
		}
	}
}
