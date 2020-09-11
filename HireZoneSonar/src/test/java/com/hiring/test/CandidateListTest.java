package com.hiring.test;


import org.junit.Test;

import com.hiring.dao.CandidateDao;

 public class CandidateListTest {

	@Test
	 public void test() {
		
		
		CandidateDao dao = new CandidateDao();
		
		System.out.println(dao.candidateList());
		System.out.println(dao.nameById("13"));
		
		System.out.println(dao.profileList("Android Developer"));
		
		System.out.println(dao.candidateMap("1"));

}
}