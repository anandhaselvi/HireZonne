package com.hiring.test;
import org.junit.Test;

import com.hiring.dao.CustomerDao;

public class CustomerListTest {

	@Test
	 public void test(){
		CustomerDao cust = new CustomerDao();
		System.out.println(cust.customerList());
		System.out.println(cust.custById("1"));
		
	}
		
}
