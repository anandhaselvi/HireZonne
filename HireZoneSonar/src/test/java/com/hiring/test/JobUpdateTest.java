package com.hiring.test;

import org.json.JSONObject;
import org.junit.Test;

import com.hiring.dao.JobDao;

 public class JobUpdateTest {

	@Test
   public 	void test() {
	
		JobDao jobdao= new JobDao();
		JSONObject json= new JSONObject();
		json.put("title","FullStack Developer");
		json.put("location","Chennai");
		json.put("jobtype", "FullTime");
		json.put("experiencerequired","Fresher");
		json.put("workauthorization", "8097645312");
		json.put("jobdescription","Description");
		json.put("updatedby", "51");
		json.put("role", "Customer");
		json.put("updatedon", "08/03/2020 04:24:00");
		json.put("jobId", "JID01");
		json.put("rate", "10000");
		json.put("visatype", "H1-visa");
		json.put("jobpostingId", "59");
		System.out.println(jobdao.jobUpdate(json));
		
	}
}
