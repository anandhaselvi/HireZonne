package com.hiring.test;

import org.json.JSONObject;
import org.junit.Test;

import com.hiring.dao.JobDao;

 public class MappingTest {

	@Test
  public void test() {

		
		JobDao job = new JobDao();
		JSONObject json = new JSONObject();
		json.put("candidateId", "72");
		json.put("jobpostingId", "20");
		json.put("submittedto", "73");
		json.put("vendorId", "73");
		json.put("customerId", "2");
		json.put("submittedType", "Primaryvendor");
		json.put("createdby", "22");
		//json.put("candidatejobpostId", "53");
		System.out.println(job.candidateMapping(json));
		
		
	//	jsonobj.put("jobpostingId", "9");
		//jsonobj.put("candidateId", "46");
		//jsonobj.put("submittedto", "45");
		//jsonobj.put("vendorId", "45");
		//jsonobj.put("customerId", "0");
		//jsonobj.put("submittedType", "Secondaryvendor");
		//jsonobj.put("createdby", "2");
	//	jobdao.candidatemapping(jsonobj);
			
	
	}

}
