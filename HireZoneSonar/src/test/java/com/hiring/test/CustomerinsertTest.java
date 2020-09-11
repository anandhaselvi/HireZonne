package com.hiring.test;

import java.io.IOException;

import org.json.JSONObject;
import org.junit.Test;

import com.hiring.dao.CustomerDao;

 public class CustomerinsertTest {

	@Test
	 public void test() throws IOException {
		CustomerDao cust=new CustomerDao();
		JSONObject custs =new JSONObject();
		custs.put("customerid","3");
		custs.put("companyname","CTSrCompany");
		custs.put("description","SWT");	
		custs.put("userid", "70");
		custs.put("createdby", "70");
		System.out.println(cust.insertCustomer(custs));
		System.out.println(cust.updatecust(custs));
	}

}