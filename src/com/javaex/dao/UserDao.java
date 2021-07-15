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
			query += "             SEQ_USER_NO.NEXTVAL, ?, ?, ?, ? ) ";	
			
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, userVo.getId());
			pstmt.setString(2, userVo.getPassword());
			pstmt.setString(3, userVo.getName());
			pstmt.setString(4, userVo.getGender());
		
			count = pstmt.executeUpdate();
			if(count >0) {
				System.out.println(count+"등록됨");
			}else {
				System.out.println("관리자문의");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		close();
		return count;
	}
	//update
	public int userUpdate(UserVo userVo) {
		
		int count = -1;
		
		getConnection();
		
		try {
			String query = "";
			query += " UPDATE users ";
			query += " SET    password = ?, ";
			query += "        name = ?, ";
			query += "        gender = ? ";
			query += " WHERE id = ? ";
			
			
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, userVo.getPassword());
			pstmt.setString(2, userVo.getName());
			pstmt.setString(3, userVo.getGender());
			pstmt.setString(4, userVo.getId());
		
			count = pstmt.executeUpdate();
			if(count >0) {
				System.out.println(count+"수정됨");
			}else {
				System.out.println("관리자문의");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		close();
		return count;
	}
	//delete
	
	
	//List
	
	//유저 1명 정보
	public UserVo getUser(String id, String pass) {
		UserVo userVo = null;
		//return 값 초기화
		getConnection();
		
		try {
			String query="";
			query+= " SELECT no, ";
			query+= "        name ";
			query+= " FROM users ";
			query+= " WHERE id = ? ";
			query+= " AND password = ? ";
			
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, "id");
			pstmt.setString(2, "pass");
			
			rs=pstmt.executeQuery();
			while(rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				
				//생성자 없을시 Setter 이용
				userVo = new UserVo();
				userVo.setNo(no);
				userVo.setName(name);
			}

		}catch(SQLException e) {
			e.printStackTrace();
		}
		close();
		return userVo;
	}
	
}
