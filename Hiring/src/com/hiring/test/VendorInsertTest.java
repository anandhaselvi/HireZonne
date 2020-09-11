package com.hiring.test;

import java.io.IOException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import com.hiring.dao.VendorDao;
class VendorInsertTest {
	@Test
	void test()  throws IOException {
		VendorDao vendor = new VendorDao();
		JSONObject ven = new JSONObject();
		//ven.put("vendorId","01");
		ven.put("firstname","Kiruba");
		ven.put("lastname", "celin");
		ven.put("description","Processing");
		ven.put("isprimary","0");
		ven.put("customerId","1");
		ven.put("userId", "1");
		ven.put("reportingperson", "null");
		System.out.println(vendor.insertVendor(ven));
	}
	}