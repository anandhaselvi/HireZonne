package com.hiring.test;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import com.hiring.dao.CandidateDao;
import com.hiring.dao.JobDao;

class JobListTest {

	@Test
	void test() {
	
		CandidateDao dao = new CandidateDao();
		
		JobDao job = new JobDao();
		//for fetch
		//JSONObject jsonobj = job.fetchJob("1");
		//System.out.println(jsonobj);
		
		
		//for list below
		JSONObject json = job.jobList();
		System.out.println(json);
		dao.candidatemap("3");
		System.out.println(job.jobRefNoListing("34"));
	}

}
