package com.hiring.test;

import java.io.IOException;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import com.hiring.dao.JobDao;

class JobInsertTest {

	@Test
	void test() throws IOException {

		JobDao job=new JobDao();
		JSONObject json=new JSONObject();
		json.put("title","BusineeAnalyst");
		json.put("company","TCS");
		json.put("location","Chennai");
		json.put("jobtype", "FullTime");
		json.put("experiencerequired","Fresher");
		json.put("keyskillsrequired", "java");
		json.put("jobdescription","deesc");
		json.put("updatedby", "1");
		json.put("status", "Apply");
		System.out.println(job.jobInsert(json));
		}

	}


