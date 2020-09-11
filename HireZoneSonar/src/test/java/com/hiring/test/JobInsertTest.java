package com.hiring.test;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import com.hiring.dao.JobDao;

public class JobInsertTest {

	@Test
	public void test() throws IOException {
		JobDao job=new JobDao();
		JSONObject json=new JSONObject();
		JSONObject  jsonobj = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		json.put("title","BussinessAnalyst");
		json.put("location","Chennai");
		json.put("jobtype", "FullTime");
		json.put("experiencerequired","Fresher");
		json.put("workauthorization","Auth34766988");
		json.put("keyskillsrequired", "java");
		json.put("jobdescription","desc");
		json.put("updatedby", "51");
		json.put("role", "Customer");
		json.put("updatedon", "08/03/2020 04:24:00");
		json.put("rate", "10000");
		json.put("visatype", "H1-visa");
		jsonArr.put(json);
		jsonobj.put("jobList", jsonArr);
		System.out.println(job.jobInsert(jsonobj));
		}
	}


