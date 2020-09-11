package com.hiring.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class Utilities {
	private Utilities() {
		throw new IllegalStateException();
	}
	private static final Logger logger = Logger.getLogger(Utilities.class);
	    public static final String readProperties(){
	        InputStream inputStream = null;
	        String serviceURL = null;
	        try {
	            Properties properties = new Properties();
	            ClassLoader loader =Thread.currentThread().getContextClassLoader();
	            inputStream = loader.getResourceAsStream("application.properties");
	            properties.load(inputStream);
	            serviceURL = properties.getProperty("serviceUrl");
	        }
	        catch (IOException e) {
				logger.error(e);
			}
			return serviceURL; 
	    }
	   
		public static HttpHeaders userAuthentication(HttpServletRequest request) {
			String strToken = request.getSession().getAttribute("token") != null
					? request.getSession().getAttribute("token").toString()
					: "";
			String strUsername = request.getSession().getAttribute("username") != null
					? request.getSession().getAttribute("username").toString()
					: "";
			HttpHeaders header = new HttpHeaders();
			header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			header.set("Authorization", strToken);
			header.set("username", strUsername);
			return header;
		}
}

