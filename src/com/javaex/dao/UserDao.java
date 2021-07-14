package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaex.vo.UserVo;

public class UserDao {

	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";
	
	private void getConnection() {
		try {
			Class.forName(driver);
			
			conn=DriverManager.getConnection(url, id, pw);
			
		}catch(ClassNotFoundException e) {
			System.out.println("driver load error"+e);
		}catch(SQLException e) {
			System.out.println("error"+e);
		}
	}
	
	private void close() {
		try {
			if(rs!=null) {
				rs.close();
			}
			if(pstmt!=null) {
				pstmt.close();
			}
			if(conn!=null) {
				conn.close();
			}
		}catch(SQLException e) {
			System.out.println("error"+e);
		}
	}
	
	//insert
	public int userInsert(UserVo userVo) {
		
		int count = -1;
		
		getConnection();
		
		try {
			String query = "";
			query += " INSERT INTO users ";
			query += " VALUES ( ";
			query += "             SEQ_USER_NO.NEXTVAL. ?, ?, ?, ? ) ";	
			
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, userVo.getId());
			pstmt.setString(2, userVo.getPassword());
			pstmt.setString(3, userVo.getName());
			pstmt.setString(4, userVo.getGender());
		
			count = pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		close();
		return count;
	}
	//delete
	//update
	//List
	
}
