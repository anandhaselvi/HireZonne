package com.hiring.test;

import org.junit.jupiter.api.Test;

import com.hiring.dao.JobDao;

class Vendorcategory {

	@Test
	void test() {
		JobDao dao = new JobDao();
		System.out.println(dao.vendorCategory("91"));
	}

}
