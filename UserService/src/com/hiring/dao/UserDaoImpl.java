package com.hiring.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.hiring.db.DBConnection;

public class UserDaoImpl implements UserDao {
	
	private static Logger logger = Logger.getLogger(UserDaoImpl.class);
	private static DBConnection conn = DBConnection.getInstance();
	
	@Override
	public int isexistuser(JSONObject json) {
		String sql = "SELECT username FROM users WHERE username =?";
		int counter =0;
		Connection con = null;
        PreparedStatement preparedstmt = null;
        ResultSet rs=null;
		try {
			con = conn.getConnection();
			preparedstmt = con.prepareStatement(sql);
			preparedstmt.setString(1,json.getString("username"));
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
	
	@SuppressWarnings("static-access")
	@Override
    public JSONObject insertUser(JSONObject json) {
		JSONObject jsonObj = new JSONObject();
		Connection con = null;
        PreparedStatement preparedstmt = null;

		int roleid =isexistuser(json);
		if(roleid>0) {
			jsonObj.put("msg","already exists");
		}
		else {
		String sql = "INSERT INTO users(firstname,lastname,password,updatedon,username,role) VALUES(?,?,?,?,?,?)";
		try {
			con = conn.getConnection();
			preparedstmt = con.prepareStatement(sql,preparedstmt.RETURN_GENERATED_KEYS);
			preparedstmt.setString(1, json.getString("firstname"));
			preparedstmt.setString(2, json.getString("lastname"));
			preparedstmt.setString(3, getMD5EncryptedValue(json.getString("password")));
			preparedstmt.setString(4, LocalDateTime.now().toString());
			preparedstmt.setString(5, json.getString("username"));
			preparedstmt.setString(6, json.getString("role"));
			preparedstmt.executeUpdate();
			ResultSet rs = preparedstmt.getGeneratedKeys();
            if(rs != null && rs.next()){
            	jsonObj.put("userId", rs.getInt(1));
            }
			jsonObj.put("msg","user Created successfully");
		} catch (SQLException e) {
			logger.error(e);
			jsonObj.put("msg","user Creation failed");
	     }
		finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try {preparedstmt.close();} catch (SQLException e){logger.error(e);}}
		  }
		}
		return jsonObj;
	}
    
  
	
	@Override
	public JSONObject userList(){
		JSONObject json = new JSONObject();
		JSONArray jsonArr =  new JSONArray();
		String sql = "SELECT users.userid, firstname,lastname,password,username,email,roleid,role,reportingperson FROM users  " + 
				"INNER JOIN role ON users.roleid=role.id;";
		Connection con = null;
		PreparedStatement createstatment = null;
		ResultSet rs=null;
        	try {
        		con = conn.getConnection();	
    	        createstatment = con.prepareStatement(sql);
    			rs=createstatment.executeQuery();
			while (rs.next()) {
				JSONObject use = new JSONObject();
				use.put("userid",rs.getInt("userid"));
				use.put("firstname",rs.getString("firstname"));
				use.put("lastname",rs.getString("lastname"));
				use.put("password",rs.getString("password"));
				use.put("Role",rs.getString("role"));
				use.put("username",rs.getString("username"));
				use.put("email",rs.getString("email"));
				use.put("roleId",rs.getInt("roleid"));
				jsonArr.put(use);
			}
			json.put("userList", jsonArr);
		} catch (SQLException e) {
			logger.error(e);
		}	finally {
   			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
   			 if (createstatment != null) { try { createstatment.close();} catch (SQLException e){logger.error(e);}}
   			 if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
   		}
    		return json;
    	
    	}
	
		
	
	private static String getMD5EncryptedValue(String password) {
        final byte[] defaultBytes = password.getBytes();
        try {
            final MessageDigest md5MsgDigest = MessageDigest.getInstance("MD5");
            md5MsgDigest.reset();
            md5MsgDigest.update(defaultBytes);
            final byte messageDigest[] = md5MsgDigest.digest();
            final StringBuffer hexString = new StringBuffer();
            for (final byte element : messageDigest) {
                final String hex = Integer.toHexString(0xFF & element);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            password = hexString + "";
        } catch (final NoSuchAlgorithmException nsae) {
            nsae.printStackTrace();
        }
      
		return password;
	
		
}
      
    @Override
	public int login(JSONObject json) {
	  Connection con = null;
      PreparedStatement preparedstmt = null;
      ResultSet rs=null;
      int counter=0;
		try {
			int userId =isexistuser(json);
			if(userId>0) {
			String sql = "SELECT username,password FROM users WHERE username=? AND password=?";
			con = conn.getConnection();
			preparedstmt = con.prepareStatement(sql);
			preparedstmt.setString(1, json.getString("username"));
			preparedstmt.setString(2,getMD5EncryptedValue(json.getString("password")));
			rs=preparedstmt.executeQuery();
			if (rs.next()) {
				counter=1;
			}
			else  {
				counter=2;
			}
		 }else {
			 counter=3;
		 }
		} catch (Exception e) {
			logger.error(e);
		}
		finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
			 if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
		
		}
		return counter;
	}
	@Override
	public JSONObject CheckRole(JSONObject jsonObj){
        JSONObject json = new JSONObject();
        Connection con = null;
        PreparedStatement preparedstmt = null;
        ResultSet rs= null;
		try {
			String sql = "SELECT CONCAT(firstname,'',lastname)as name,users.id,users.role,users.username,token FROM users"
					+ " INNER JOIN sessiontoken st ON st.username = users.username WHERE users.username=?";
			con = conn.getConnection();
			preparedstmt = con.prepareStatement(sql);
			preparedstmt.setString(1,jsonObj.getString("username"));
			rs = preparedstmt.executeQuery();
			if (rs.next()) {
				json.put("userId",rs.getString("id"));
				json.put("role",rs.getString("role")); 
				json.put("username", rs.getString("username"));
				json.put("token",rs.getString("token"));
				json.put("name", rs.getString("name"));
			}
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

	@Override
	public void validateToken(String username) {
		Connection con = null;
		PreparedStatement preparedstmt = null;
		try {
		String token = UUID.randomUUID().toString();
		String deletesql = "DELETE FROM sessiontoken WHERE username=?";
		con = conn.getConnection();
		preparedstmt = con.prepareStatement(deletesql);
		preparedstmt.setString(1, username);
		preparedstmt.executeUpdate();
		preparedstmt.close();
		String sql = "INSERT INTO sessiontoken(username,token,updatedon) VALUES(?,?,?)";
		preparedstmt = con.prepareStatement(sql);
		preparedstmt.setString(1, username);
		preparedstmt.setString(2, token);
		preparedstmt.setString(3, LocalDateTime.now().toString());
		preparedstmt.executeUpdate();
		}catch (SQLException e) {
			logger.error(e);
		}
		finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
		}
	}
//	@Override
//	public Boolean authorizeToken(String username,String token) {
//		Connection con =null;
//		PreparedStatement preparedstmt = null;
//		ResultSet rs= null;
//		try {
//			String sql ="SELECT token FROM sessiontoken WHERE username=?";
//			con=conn.getConnection();
//			preparedstmt=con.prepareStatement(sql);
//			preparedstmt.setString(1, username);
//			rs= preparedstmt.executeQuery();
//			if(rs.next()) {
//				if(rs.getString("token").equalsIgnoreCase(token)) {
//					return true;
//				}
//				else {
//					return false;
//				}
//			}
//		}catch(SQLException e) {
//			logger.error(e);
//		}finally {
//			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
//			 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
//		}
//		return false;
//	}
//	
	
	@Override
	public JSONObject passwordchange(JSONObject json) {
		  JSONObject jsonObj = new JSONObject();
		  Connection con = null;
	      PreparedStatement preparedstmt = null;
	      ResultSet rs=null;
			try {
				int userid = checkoldPassword(json);
				if(userid>0) {
				String sql = "UPDATE users set Password=? WHERE id=?";
				con = conn.getConnection();
				preparedstmt = con.prepareStatement(sql);
				preparedstmt.setString(1,getMD5EncryptedValue(json.getString("password")));
				preparedstmt.setString(2, json.getString("userid"));
				int counter =preparedstmt.executeUpdate();
				if (counter>0) {
					jsonObj.put("msg", "Your password has been updated sucessfully");
					}
				}
				else {
					jsonObj.put("msg", "Incorrect old password");
				}
			} catch (SQLException e) {
				logger.error(e);
				jsonObj.put("msg", "password updation failed");
			}
			finally {
				 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
				 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
				 if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
			}
			return jsonObj;
		}

	private int checkoldPassword(JSONObject json) {
		 Connection con = null;
	      PreparedStatement preparedstmt = null;
	      ResultSet rs=null;
	      int counter =0;
			try {
				String sql ="SELECT id FROM users WHERE password=? and id=?";
				con = conn.getConnection();
				preparedstmt = con.prepareStatement(sql);
				preparedstmt.setString(1,getMD5EncryptedValue(json.getString("oldpassword")));
				preparedstmt.setString(2, json.getString("userid"));
			    rs =preparedstmt.executeQuery();
				if (rs.next()) {
					counter=1;
				}
				}
			catch (SQLException e) {
				logger.error(e);
			}
			finally {
				 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
				 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
				 if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
			}
			return counter;
	}

	
	
}