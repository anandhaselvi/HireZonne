package com.hiring.test;

import java.io.IOException;

import org.json.JSONObject;
import org.junit.Test;

import com.hiring.dao.VendorDao;

public class VendorInsertTest {
	@Test
	 public void test()  throws IOException {
		VendorDao vendor = new VendorDao();
		JSONObject ven = new JSONObject();
	//	companydetails,taxid,address,contact,vendorname,email,phone,insurance,certification,partnership,reportingto,
	//  createdon,status,userId,createdby,category
		//ven.put("vendorId","01");
		ven.put("companydetails","CTERS");
		ven.put("taxid", "007");
		ven.put("address","Chennai");
		ven.put("contact","9087654362");
		ven.put("firstname","Kiruba");
		ven.put("lastname", "Wincelin");
		ven.put("email", "kiruba123@gmail.com");
		ven.put("phone", "9087654362");
		ven.put("insurance", "Yes");
		ven.put("certification", "No");
		ven.put("partnership", "No");
		ven.put("reportingto", "51");
		ven.put("createdon", "08/19/2020 05:20:00");
		ven.put("status", "Approved");
		ven.put("userId", "74");
		ven.put("createdby", "74");
		ven.put("category", "gold");
		ven.put("customer", "1");
		ven.put("vendorId", "4");
		System.out.println(vendor.insertVendor(ven));
		System.out.println(vendor.vendorUpdate(ven));
	}
	}