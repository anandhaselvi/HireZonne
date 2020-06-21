package com.hiring.test;



import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import com.hiring.dao.CustomerDao;

class CustomerListTest {

	@Test
	void test(){
		CustomerDao cust=new CustomerDao();
		JSONObject jsonobj = cust.customerList();
		System.out.println(jsonobj);

}
}