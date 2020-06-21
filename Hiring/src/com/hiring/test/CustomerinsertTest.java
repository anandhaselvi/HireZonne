package com.hiring.test;

import java.io.IOException;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import com.hiring.dao.CustomerDao;

class CustomerinsertTest {

	@Test
	void test() throws IOException {
		CustomerDao cust=new CustomerDao();
		JSONObject custs =new JSONObject();
		custs.put("customerid","2");
		custs.put("companyname","CTS");
		custs.put("description","SW");	
		System.out.println(cust.insertCustomer(custs));
		//System.out.println(cust.updatecust(custs));
	}

}