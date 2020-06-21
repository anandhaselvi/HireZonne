package com.hiring.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.hiring.db.DBConnection;

public class CustomerDao implements Customer {
	private static Logger logger = Logger.getLogger(CustomerDao.class);
	private static DBConnection conn = DBConnection.getInstance();
	
	public int isexistcustomer(JSONObject json) {
		String sql = "SELECT companyname FROM customer WHERE companyname =?";
		int counter =0;
		Connection con = null;
        PreparedStatement preparedstmt = null;
        ResultSet rs=null;
		try {
			con = conn.getConnection();
			preparedstmt = con.prepareStatement(sql);
			preparedstmt.setString(1,json.getString("companyname"));
			rs=preparedstmt.executeQuery();
			if(rs.next()) {
				counter=1;
			}
			con.close();
			return counter;
		} catch (SQLException e) {
			logger.error(e);}
		finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try {preparedstmt.close();} catch (SQLException e){logger.error(e);}}
			 if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
		}
		return 0;
		}	
	@Override
public JSONObject insertCustomer(JSONObject json) {
		JSONObject jsonObj = new JSONObject();
		int customerid =isexistcustomer(json);
		if(customerid>0) {
			jsonObj.put("msg","already exists");
		}
		else {
		String sql = "INSERT INTO customer(companyname,description) VALUES(?,?)";
		Connection con = null;
        PreparedStatement preparedstmt = null;
		try {
			con = conn.getConnection();
			preparedstmt = con.prepareStatement(sql);
			preparedstmt.setString(1, json.getString("companyname"));
			preparedstmt.setString(2, json.getString("description"));
			preparedstmt.executeUpdate();
			jsonObj.put("msg","Cusotmer Created successfully");
		} catch (SQLException e) {
			logger.error(e);
			jsonObj.put("msg","Customer Creation failed");
	}
		finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try {preparedstmt.close();} catch (SQLException e){logger.error(e);}}
			
		}
			}
		return jsonObj;

}
@Override
public JSONObject customerList(){
	JSONObject json = new JSONObject();
	JSONArray jsonArr =  new JSONArray();
	Connection con = null;
	Statement stmt=null;
	ResultSet rs=null;
	//String sql = "SELECT users.userid, firstname,lastname,password,username,email,roleid,role,reportingperson FROM users  " + 
			//"INNER JOIN role ON users.roleid=role.id;";
	try {
		String sql = "SELECT customerid,companyname,description from customer ";
    		con = conn.getConnection();	
	        stmt=con.createStatement();
			rs=stmt.executeQuery(sql);
		while (rs.next()) {
			JSONObject cust = new JSONObject();
			cust.put("customerid",rs.getInt("customerid"));
			cust.put("companyname",rs.getString("companyname"));
			cust.put("description",rs.getString("description"));
			jsonArr.put(cust);
		}
		json.put("customerList", jsonArr);
	} catch (SQLException e) {
		logger.error(e);
	}	finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (stmt != null) { try { stmt.close();} catch (SQLException e){logger.error(e);}}
			 if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
		}
		return json;
	
	}
@Override
public JSONObject updatecust(JSONObject json) {
	String sql="UPDATE customer SET companyname=?,description=?WHERE customerid=?";
	JSONObject jsonobj = new JSONObject();
	Connection con = null;
    PreparedStatement preparedstmt = null;
	try {
		con = conn.getConnection();
		preparedstmt = con.prepareStatement(sql);
		preparedstmt.setString(1, json.getString("companyname"));
		preparedstmt.setString(2, json.getString("description"));
		preparedstmt.setInt(3,json.getInt("customerid"));
		preparedstmt.executeUpdate();
		jsonobj.put("msg", "user updated successfully");
	}catch (SQLException e) {
		logger.error(e);
		jsonobj.put("msg", "user updated failed");
	}
	finally {
		 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
		 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
	}
	return jsonobj;

}
@Override
public JSONObject custById(String customerid) {
	JSONObject jsonobj = new JSONObject();
	String sql = "SELECT companyname,description FROM customer WHERE customerid=?";
	Connection con = null;
    PreparedStatement preparedstmt = null;
    ResultSet rs=null;
	try {
		con = conn.getConnection();
		preparedstmt = con.prepareStatement(sql);
		preparedstmt.setString(1, customerid);
		rs=preparedstmt.executeQuery();
		if (rs.next()) {
			jsonobj.put("companyname",rs.getString("companyname"));
			jsonobj.put("description",rs.getString("description"));
			}
	} catch (SQLException e) {
		logger.error(e);
	}
	finally {
		 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
		 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
		 if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
	}
	return jsonobj;
}
	


}