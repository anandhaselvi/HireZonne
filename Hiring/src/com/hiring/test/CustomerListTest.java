package com.hiring.test;



import org.junit.jupiter.api.Test;

import com.hiring.dao.CandidateDao;

class CustomerListTest {

	@Test
	void test(){
		//CustomerDao cust=new CustomerDao();
		//JSONObject jsonobj = cust.customerList();
		//System.out.println(jsonobj);
		CandidateDao dao = new CandidateDao();
		System.out.println(dao.candidatemap("1"));
}
}