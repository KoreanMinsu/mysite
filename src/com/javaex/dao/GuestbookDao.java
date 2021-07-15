package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.GuestbookVo;

public class GuestbookDao {

	private Connection conn=null;
	private PreparedStatement pstmt=null;
	private ResultSet rs=null;
	
	private String driver ="oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";
	
	//getConnection method
	private void getConnection() {
		try {
			Class.forName(driver);
			
			conn=DriverManager.getConnection(url, id, pw);
			
		}catch(ClassNotFoundException e) {
			System.out.println("driver load error:"+e);
		}catch(SQLException e) {
			System.out.println("error:"+e);
		}
	}
	
	//close() 자원관리
	private void close() {
		try {
			if(rs!=null) 
				rs.close();
			if(pstmt!=null) 
				pstmt.close();
			if(conn!=null) 
				conn.close();
		}catch(SQLException e) {
			System.out.println("error:"+e);
		}
	}
	
	//추가
	public int guestInsert(GuestbookVo gbVo) {
		int count=-1;
		getConnection();
		
		try {
			String query="";
			query+= " INSERT INTO guestbook ";
			query+=" VALUES ( seq_no.NEXTVAL, ?, ?, ?, sysdate ) ";
			
			pstmt=conn.prepareStatement(query);
			
			pstmt.setString(1, gbVo.getName());
			pstmt.setString(2, gbVo.getPassword());
			pstmt.setString(3, gbVo.getContent());
			
			count = pstmt.executeUpdate();
			
			if(count>0) {
				System.out.println(count+" 건 추가함");
			} else {
				System.out.println("관리자문의");
			}
			
		} catch(SQLException e) {
			System.out.println("error:"+e);
		}
		close();
		return count;
	}
	
	//삭제
	public int guestDelete(GuestbookVo gbVo) {
		int count =-1;
		getConnection();
		
		try {
			String query ="";
			query+=" DELETE FROM guestbook ";
			query+=" WHERE no = ? ";
			query+=" AND password = ? ";
			
			pstmt=conn.prepareStatement(query);
			pstmt.setInt(1, gbVo.getNo());
			pstmt.setString(2, gbVo.getPassword());
			
			count = pstmt.executeUpdate();
		} catch(SQLException e) {
			System.out.println("error:"+e);
		}		
		close();
		return count;
	}
	
	//list
	public List<GuestbookVo> getList(){
		List<GuestbookVo> gbList = new ArrayList<GuestbookVo>();
		getConnection();
		
		try {
			String query="";
			query+=" SELECT no,";
			query+="         name, ";
			query+="         password, ";
			query+="         content, ";
			query+="         reg_date, ";
			query+=" FROM guestbook ";
			query+=" ORDER BY reg_date DESC ";
			
			pstmt=conn.prepareStatement(query);
			rs= pstmt.executeQuery();
			
			while(rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String content = rs.getString("content");
				String regDate = rs.getString("reg_date");
				
				GuestbookVo gbVo = new GuestbookVo(no, name, password, content, regDate);
				gbList.add(gbVo);
			}
			
		}catch(SQLException e) {
			System.out.println("error:"+e);
		}
		close();
		return gbList;
		
	}
}
