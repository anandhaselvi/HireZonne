package com.hiring.test;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import com.hiring.dao.CandidateDao;
class CandidateInsertTest {
	@Test
	void test() throws IOException {
		CandidateDao cust=new CandidateDao();
		JSONObject custs =new JSONObject();
		custs.put("name","jashvan");
		custs.put("emailid","jash@gmail.com");
		custs.put("dob","04/03/1993");	
		custs.put("profile","Java developer");
		custs.put("candidateid", "1");
		custs.put("userid", "5");
		byte[] fileContent = FileUtils.readFileToByteArray(new File("D:\\Selvi.jpg"));
		String encodedString = Base64.getEncoder().encodeToString(fileContent);
		custs.put("resumeupload", encodedString);
		System.out.println(cust.insertapplicant(custs));
		System.out.println(cust.updatecandidate(custs));
	}
}