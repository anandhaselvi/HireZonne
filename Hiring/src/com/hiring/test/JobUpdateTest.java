package com.hiring.test;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import com.hiring.dao.JobDao;

class JobUpdateTest {

	@Test
	void test() {
	
		JobDao jobdao= new JobDao();
		JSONObject json= new JSONObject();
		json.put("title", "SoftwareEngineer");
		json.put("location", "Chennai");
		json.put("jobtype", "PartTime");
		json.put("experiencerequired","3years");
		json.put("workauthorization", "Java");
		json.put("jobdescription", "deeeesc");
		json.put("updatedby", "1");;
		json.put("jobId", "");
		json.put("role", "customer");
		//System.out.println(jobdao.jobInsert(json));
		JSONObject jsonobj = new JSONObject();
		
		jsonobj.put("jobpostingId", "9");
		jsonobj.put("candidateId", "46");
		jsonobj.put("submittedto", "45");
		jsonobj.put("vendorId", "45");
		jsonobj.put("customerId", "0");
		jsonobj.put("submittedType", "Secondaryvendor");
		jsonobj.put("createdby", "2");
		jobdao.candidateMapping(jsonobj);
			
	}
}
