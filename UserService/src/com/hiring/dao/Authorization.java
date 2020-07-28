package com.hiring.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.hiring.db.DBConnection;

public class Authorization {
	private static Logger logger = Logger.getLogger(UserDaoImpl.class);
	private static DBConnection conn = DBConnection.getInstance();

	 public static final Boolean authorizeToken(String username,String token) {
		Connection con =null;
		PreparedStatement preparedstmt = null;
		ResultSet rs= null;
		try {
			String sql ="SELECT token FROM sessiontoken WHERE username=?";
			con=conn.getConnection();
			preparedstmt=con.prepareStatement(sql);
			preparedstmt.setString(1, username);
			rs= preparedstmt.executeQuery();
			if(rs.next()) {
				if(rs.getString("token").equalsIgnoreCase(token)) {
					return true;
				}
				else {
					return false;
				}
			}
		}catch(SQLException e) {
			logger.error(e);
		}finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
		}
		return false;
	}
}
