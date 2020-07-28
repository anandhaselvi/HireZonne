package com.hiring.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import com.hiring.db.DBConnection;
public class VendorDao implements Vendor {
private static DBConnection conn = DBConnection.getInstance();
private static Logger logger = Logger.getLogger(VendorDao.class);

@Override
public JSONObject insertvendor(JSONObject json) {
	JSONObject jsonObject = new JSONObject();
	int counter=nameExists(json.getString("firstname") +" "+json.getString("lastname"));
	if(counter>0) {
		jsonObject.put("msg", "vendor already exists");
		}
	else {
		Connection con = null;		
		PreparedStatement preparedstmt = null;
      	try {
			//String sql = "Insert into vendor(vendorname,description,isprimary,customer,reportingto)values(?,?,?,?,?)";
			String sql = "Insert into vendor(vendorname,isprimary,customer,reportingto,userId,createdon,createdby)values(?,?,?,?,?,?,?)";
     		con = conn.getConnection();
			preparedstmt = con.prepareStatement(sql);
			//preparedstmt.setString(1, json.getString("vendorId"));
			preparedstmt.setString(1,json.getString("firstname") +" "+json.getString("lastname"));
			//preparedstmt.setString(2,json.getString("description"));
			preparedstmt.setInt(2,json.getInt("isprimary"));
			preparedstmt.setString(3,json.getString("customerId"));
			preparedstmt.setString(4,json.optString("reportingto").isEmpty()?"0":json.optString("reportingto"));
			preparedstmt.setString(5,json.getString("userId"));
			preparedstmt.setString(6,LocalDateTime.now().toString());
			preparedstmt.setString(7,json.getString("userId"));
			preparedstmt.executeUpdate();
			jsonObject.put("msg", "vendor added successfully");
		} catch (SQLException e) {
			logger.error(e);
			jsonObject.put("msg", "vendor creation failed");
		}
		finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
			
		}
	}
	return jsonObject;
}


@Override
public int nameExists(String vendorname) {
	Connection con = null;
	PreparedStatement preparedstmt= null;
	ResultSet rs = null;
	try {
		String sql = "SELECT vendorname FROM vendor WHERE vendorname=?";
		int counter = 0;
		con = conn.getConnection();
		preparedstmt = con.prepareStatement(sql);
		preparedstmt.setString(1, vendorname);
		rs = preparedstmt.executeQuery();
		if (rs.next()) {
			counter = 1;
		}
		return counter;
	} catch (SQLException e) {
		e.printStackTrace();
		return 0;
	}
	finally {
		 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
		 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
		 if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
	}
}


public JSONObject getCustomer() {
	JSONObject jsonObject = new JSONObject();
	JSONArray jsonArr = new JSONArray();
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	try {
		String sql = "SELECT customerid,companyname FROM customer ORDER BY companyname";
		con = conn.getConnection();
		stmt = con.createStatement();
		rs = stmt.executeQuery(sql);
		while (rs.next()) {
			JSONObject json = new JSONObject();
			json.put("customerid", rs.getInt("customerid"));
			json.put("companyname", rs.getString("companyname"));
			jsonArr.put(json);
		}
		jsonObject.put("getCustomer", jsonArr);
	} catch (SQLException e) {
		logger.error(e);
	} finally {
		 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
		 if (stmt != null) { try { stmt.close();} catch (SQLException e){logger.error(e);}}
		 if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
	}
	return jsonObject;
}
	public JSONObject getReporting() {
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT vendorid,vendorname FROM vendor ORDER BY vendorname";
			con = conn.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				JSONObject json = new JSONObject();
				json.put("vendorid", rs.getInt("vendorid"));
				json.put("vendorname", rs.getString("vendorname"));
				jsonArr.put(json);
			}
			jsonObject.put("getReporting", jsonArr);
		} catch (SQLException e) {
			logger.error(e);
		} finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (stmt != null) { try { stmt.close();} catch (SQLException e){logger.error(e);}}
			 if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
		}
		return jsonObject;
	}

	public JSONObject vendorList(String userId,String role) {
		JSONObject jsonobj = new JSONObject();
		JSONArray jsonarr = new JSONArray();
		Connection con = null;
		PreparedStatement preparedstmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT vendor.vendorid, vendor.vendorname,vendor.description,vendor.isprimary,customer.companyname, vendor.reportingto, username FROM vendor"
						+" INNER JOIN customer ON vendor.customer = customer.customerid INNER JOIN users ON users.id = vendor.userId WHERE";
			if(role.equalsIgnoreCase("customer")) {		
						sql+= " vendor.customer=? AND isprimary=?";
			}else if(role.equalsIgnoreCase("vendor")) {
				sql+= " vendor.reportingto=?";
			}
			con = conn.getConnection();
			preparedstmt = con.prepareStatement(sql);
			preparedstmt.setString(1, userId);
			if(role.equalsIgnoreCase("customer")) {		
				preparedstmt.setString(2, "1");
			}
			rs = preparedstmt.executeQuery();
			while (rs.next()) {
				JSONObject ven = new JSONObject();
				ven.put("vendorId", rs.getString("vendorid"));
				ven.put("vendorname", rs.getString("vendorname"));
				if(rs.getString("description") != null) {
				ven.put("description", rs.getString("description"));
				}
				ven.put("isprimary", rs.getString("isprimary"));
				if(rs.getString("companyname") != null) {
				ven.put("customer", rs.getString("companyname"));
				}
				if(rs.getInt("reportingto") != 0) {
				JSONObject json = getReportingto(rs.getString("reportingto"));
				ven.put("reportingto",json.optString("reportingname"));
				}
				ven.put("emailId", rs.getString("username"));
				jsonarr.put(ven);
			}
			jsonobj.put("vendorList", jsonarr);
		} catch (SQLException e) {
			logger.error(e);
		}finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
			 if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
		}
		return jsonobj;
	}

	public JSONObject getReportingto(String reportingto) {
		JSONObject json = new JSONObject();
		Connection con = null;
	    PreparedStatement preparedstmt = null;
	    ResultSet rs=null;
		try {
			String sql = "SELECT vendorname,reportingto,customer FROM vendor WHERE userId =?";
			con = conn.getConnection();
			preparedstmt = con.prepareStatement(sql);
			preparedstmt.setString(1, reportingto);
			rs=preparedstmt.executeQuery();
			if (rs.next()) {
			 json.put("reportingname", rs.getString("vendorname"));
			 json.put("customer", rs.getString("customer"));
			 json.put("reportingto", rs.getString("reportingto"));
			}
			con.close();
		}catch (SQLException e) {
			logger.error(e);
		}
		finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
			 if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
		}
		return json;
	}
	
	public JSONObject vendorupdate(JSONObject jsonObject) {
		JSONObject json =new JSONObject();
		Connection con =null;
		PreparedStatement preparedstmt = null;
		try {
			String[] vendorIds = jsonObject.getString("vendorId").replaceAll(",$","").split(",");
			String sql = "UPDATE vendor SET isprimary=? where vendorId=?";
			con = conn.getConnection();
			preparedstmt = con.prepareStatement(sql);
			for (String vendorId : vendorIds) {
			preparedstmt.setString(1, "1");
			preparedstmt.setString(2, vendorId);
			preparedstmt.executeUpdate();
			}
		 	json.put("msg","Vendor Updated");
			return  json;
		} catch (SQLException e) {
			logger.error(e);
			json.put("msg","Vendor failed");
			return json;
		}finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
		}
	}
	

	public JSONObject fetchvendor(String id) {
		JSONObject json = new JSONObject();
		Connection con= null;
	    PreparedStatement preparedstmt=null;
	    ResultSet rs = null;
	    String sql = "SELECT vendorId, vendorname,description,isprimary, customer,reportingto from vendor WHERE vendorId=?";
		try {
			con = conn.getConnection();
			preparedstmt = con.prepareStatement(sql);
			preparedstmt.setString(1, id);
			rs = preparedstmt.executeQuery();
			if (rs.next()) {
				json.put("vendorname", rs.getString("vendorname"));
				json.put("description", rs.getString("description"));
				json.put("isprimary", rs.getInt("isprimary"));
				json.put("customer", rs.getInt("customer"));
				json.put("reportingto", rs.getInt("reportingto"));
				json.put("vendorId", rs.getInt("vendorId"));
			}
		} catch (SQLException e) {
			logger.error(e);
		}
		finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
			 if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
		}
		return json;
	}
	
	public JSONObject getCustomerId(String id) {
		JSONObject json = new JSONObject();
		Connection con= null;
	    PreparedStatement preparedstmt=null;
	    ResultSet rs = null;
	    String sql = "SELECT vendorId, vendorname,description,isprimary, customer,reportingto from vendor WHERE userId=?";
		try {
			con = conn.getConnection();
			preparedstmt = con.prepareStatement(sql);
			preparedstmt.setString(1, id);
			rs = preparedstmt.executeQuery();
			if (rs.next()) {
				json.put("vendorname", rs.getString("vendorname"));
				json.put("description", rs.getString("description"));
				json.put("isprimary", rs.getInt("isprimary"));
				json.put("customer", rs.getInt("customer"));
				json.put("reportingto", rs.getInt("reportingto"));
				json.put("vendorId", rs.getInt("vendorId"));
			}
		} catch (SQLException e) {
			logger.error(e);
		}
		finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
			 if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
		}
		return json;
	}
}