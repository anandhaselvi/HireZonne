package com.hiring.test;



import org.junit.Test;

import com.hiring.dao.VendorDao;

 public class VendorListTest {

	@Test
	 public void test() {
		VendorDao dao = new VendorDao();
		System.out.println(dao.vendorList("50", "customer"));
		System.out.println(dao.vendorList("45", "vendor"));
		System.out.println(dao.checkStatus("unapproved", "72"));
		System.out.println(dao.getCustomer());
		System.out.println(dao.fetchVendor("12"));
		System.out.println(dao.getCustomerId("49"));
		System.out.println(dao.getReporting());
		System.out.println(dao.getVendor(1));
	}
}

