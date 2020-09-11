package com.hiring.test;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.junit.Test;

import com.hiring.dao.CandidateDao;
 public class CandidateInsertTest {
	@Test
	 public void test() throws IOException {
		CandidateDao cust=new CandidateDao();
		JSONObject custs =new JSONObject();
		custs.put("firstname", "braiaive");
		custs.put("lastname","Stonned");
		custs.put("name","JasperRose");
		custs.put("emailid","jasper12@gmail.com");
		custs.put("dob","04/22/1993");	
		custs.put("profile","Java Developer");
		custs.put("candidateid", "23");
		custs.put("userid", "75");
		custs.put("createdby", "75");
		custs.put("candidateid", "18");
		byte[] fileContent = FileUtils.readFileToByteArray(new File("D:\\Selvi.jpg"));
		String encodedString = Base64.getEncoder().encodeToString(fileContent);
		custs.put("resumeupload", encodedString);
		System.out.println(cust.insertApplicant(custs));
		System.out.println(cust.updateCandidate(custs));
	}
	
}