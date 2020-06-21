
package com.hiring.test;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import com.hiring.dao.SendMail;

class SendMailTest {

	@Test
	void test() {
		SendMail mail = new SendMail();
		JSONObject json = new JSONObject();
		json.put("email", "anandhaselvi0@gmail.com");
		json.put("userId", "2");
		mail.sendmail(json);
	}

}
