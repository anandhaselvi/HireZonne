package com.hiring.test;

import org.junit.jupiter.api.Test;

import com.hiring.dao.VendorDao;

class VendorListTest {

	@Test
	void test() {
		VendorDao dao = new VendorDao();
		System.out.println(dao.vendorList("34", "vendor"));
	}

}
