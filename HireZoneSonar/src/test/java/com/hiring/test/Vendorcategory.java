package com.hiring.test;

import org.junit.Test;

import com.hiring.dao.JobDao;


public class Vendorcategory {

	@Test
	public void test() {
		JobDao dao = new JobDao();
		System.out.println(dao.vendorCategory("74"));
	}

}
