package com.hiring.service.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;

import com.hiring.db.DBConnection;


public class Authorization {
	private Authorization() {
		 throw new IllegalStateException();
	}
	private static Logger logger = Logger.getLogger(Authorization.class);
	private static DBConnection conn = DBConnection.getInstance();

	 public static final Boolean authorizeToken(HttpHeaders headers) {
		Connection con =null;
		PreparedStatement preparedstmt = null;
		ResultSet rs= null;
		try {
			String token = headers.getFirst("Authorization");
			String username = headers.getFirst("username");
			String sql ="SELECT token FROM sessiontoken WHERE username=?";
			con=conn.getConnection();
			preparedstmt=con.prepareStatement(sql);
			preparedstmt.setString(1, username);
			rs= preparedstmt.executeQuery();
			if(rs.next() && rs.getString("token").equalsIgnoreCase(token)) {
					return true;
			}		
		}catch(SQLException e) {
			logger.error(e);
		}finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
			 if (rs != null) { try { rs.close(); } catch (SQLException e){logger.error(e);}}
		}
		return false;
	}


}
