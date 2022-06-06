package com.recast.recast.bo.analyzer.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JarGeneration {
	
	public static void main(String[] args) throws FileNotFoundException {
	
		File file = new File("C:\\Users\\10693394\\Documents\\dumps\\deployed_lib\\text.txt");
		  BufferedReader br
          = new BufferedReader(new FileReader(file));
		try {
			String st;
			while((st=br.readLine())!=null)
			{
				
				String jars ="<dependency>\r\n"
						+ "			<groupId>"+st.substring(0,st.lastIndexOf("."))+"</groupId>\r\n"
						+ "			<artifactId>"+st.substring(0,st.lastIndexOf("."))+"</artifactId>\r\n"
						+ "			<scope>system</scope>\r\n"
						+ "			<version>1.0</version>\r\n"
						+ "			<systemPath>${project.basedir}\\lib\\"+st+"</systemPath>\r\n"
						+ "		</dependency>";
				System.out.println(jars);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
