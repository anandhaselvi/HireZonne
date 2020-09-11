package com.hiring.test;

import org.json.JSONObject;
import org.junit.Test;

import com.hiring.dao.JobDao;

 public class JobListTest {

	@Test
	 public void test() {
	
	
		
		JobDao job = new JobDao();
		//for fetch
		JSONObject jsonobj = job.fetchJob("7");
		System.out.println(jsonobj);
		
		
		//for list below
	JSONObject json = job.jobList();
	System.out.println(json);
		
		System.out.println(job.jobRefNoListing("72"));
	
		
		System.out.println(job.vendorCategory("72"));
	}

}
