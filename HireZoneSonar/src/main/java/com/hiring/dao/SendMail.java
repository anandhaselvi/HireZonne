package com.hiring.dao;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.json.JSONObject;

public class SendMail {

	private static final Logger logger = Logger.getLogger(SendMail.class);
	
    public JSONObject sendmail(JSONObject json) {
        // Recipient's email ID needs to be mentioned.
    	JSONObject jsonObj = new JSONObject();
    	String to=json.optString("email");
    	String from ="HireZone";
        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("prismentsolwebapp@gmail.com", "prism@123");
            }

        });

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            if(json.has("submitType")) {
            if(json.getString("submitType").equalsIgnoreCase("approved")) {
           	 message.setSubject("vendor has been approved");

    			// Now set the actual message
                message.setText("Your vendor has been approved.");
           }
            if(json.getString("submitType").equalsIgnoreCase("Declined")) {
          	 	message.setSubject("vendor has been declined");

   			// Now set the actual message
               message.setText("Your vendor  has been declined,Please check your updated timesheet and resubmit again.");
          }
                 // Set Subject: header field
            }
           
           // message.setContent("<h1>This is a HTML text</h1>","text/html"); 
            else if(json.has("customerId")) {
            	 message.setSubject("Invited you to Hirezone");
            	 message.setContent("Dear vendor, <br><br>We would like to invite you to a join a Hirezone to create jobposting,Click here to register<br><a href=http://localhost:8080/HiringUI/register?id="+json.optInt("customerId")+"&Vendor="+json.getString("userId")+">Click here</a><br>"
            		+ "Regards,<br>Team HireZone","text/html");
            }
            else {
            	 message.setSubject("Invited you to Hirezone");
                message.setContent("Dear vendor, <br><br>We would like to invite you to a join a Hirezone to create jobposting,Click here to register<br><a href=http://localhost:8080/HiringUI/register?id="+json.getString("userId")+">Click here</a><br>"
                		+ "Regards,<br>Team HireZone","text/html");
            }
            Transport.send(message);
            
          } catch (Exception ex) {
        	  logger.error(ex);
        }
       
        return jsonObj.put("msg","Sent message successfully");
    }

}
												
