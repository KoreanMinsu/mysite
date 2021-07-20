package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;
import com.javaex.vo.GuestbookVo;


public class BoardDao {

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
	
	//insert
	public int boardInsert(BoardVo boardVo) {
		int count=-1;
		getConnection();
		
		try {
			String query="";
			query+= " INSERT INTO board ";
			query+=" VALUES ( seq_board_no.NEXTVAL, ?, ?, ?, sysdate ) ";
			
			pstmt=conn.prepareStatement(query);
			
			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContent());
			pstmt.setString(3, boardVo.getContent());
			
			count = pstmt.executeUpdate();
			
			if(count>0) {
				System.out.println(count+" 건 추가함");
			} else {
				System.out.println("관리자문의");
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		close();
		return count;
	}
	
	//delete
	public int boardDelete(int bNo) {
		int count =0;
		getConnection();
		
		try {
			String query ="";
			query+=" DELETE FROM board ";
			query+=" WHERE no = ? ";
			
			pstmt=conn.prepareStatement(query);
			pstmt.setInt(1, bNo);
						
			count = pstmt.executeUpdate();
			
			if(count>0) {
				System.out.println(count+" 건 삭제함");
			} else {
				System.out.println("관리자문의");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}		
		close();
		return count;
	}
	
	//list With keyword search
	public List<BoardVo> getList(String keyword){
		List<BoardVo> boardList = new ArrayList<BoardVo>();
		getConnection();
		
		try {
			String query="";
			query+=" SELECT no,";
			query+="         title, ";
			query+="         content, ";
			query+="         hit, ";
			query+="         reg_date ";
			query+="         user_no ";
			query+="         name ";
			query+=" FROM users us LEFT OUTER JOIN board bo ON us.no = bo.user_no  ";
			
			if(keyword !=""|| keyword == null) {
				query+=" WHERE title like ? ";
				query+="    OR content like ? ";
				query+="    OR name like ? ";
				query+=" ORDER BY no DESC ";
					
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1,'%'+ keyword + '%');
				pstmt.setString(2,'%'+ keyword + '%');
				pstmt.setString(3,'%'+ keyword + '%');
				
			} else {
				query+=" ORDER BY no DESC ";
				pstmt=conn.prepareStatement(query);
			}
			
			rs= pstmt.executeQuery();
			
			while(rs.next()) {
				int no = rs.getInt("no");
				String title = rs.getString("title");
				String content = rs.getString("content");
				int hit = rs.getInt("hit");
				String regDate = rs.getString("reg_date");
				int userNo = rs.getInt("user_no");
				String name = rs.getString("name");
				
				BoardVo boardVo = new BoardVo(no, title, content, hit, regDate, userNo, name);
				boardList.add(boardVo);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		close();
		return boardList;
		
	}
	
	//list(pick one)
	public BoardVo getList(int boardNo) {
		
		BoardVo boardVo = null;
		getConnection();
		
		try {
			String query="";
			query+=" SELECT no,";
			query+="         title, ";
			query+="         content, ";
			query+="         hit, ";
			query+="         reg_date ";
			query+="         user_no ";
			query+="         name ";
			query+=" FROM users, board ";
			query+=" WHERE users.no = board.user_no  ";
			query+=" and no = ? ";
			
			pstmt=conn.prepareStatement(query);
			rs= pstmt.executeQuery();
			
			while(rs.next()) {
				int no = rs.getInt("no");
				String title = rs.getString("title");
				String content = rs.getString("content");
				int hit = rs.getInt("hit");
				String regDate = rs.getString("reg_date");
				int userNo = rs.getInt("user_no");
				String name = rs.getString("name");
				
				boardVo = new BoardVo(no, title, content, hit, regDate, userNo, name);
				
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		close();
		return boardVo;
	}
	
	//update
	public int boardModify(BoardVo boardVo) {
		int count=-1;
		getConnection();
		
		try {
			String query = "";
			query += " UPDATE board ";
			query += " SET    title = ?, ";
			query += "        content = ?, ";
			query += " WHERE no = ? ";

			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContent());
			pstmt.setInt(3, boardVo.getNo());
			
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
	
	//hit조회수
	public void hitCount(int hitCount) {
		getConnection();
		
		try {
			String query="";
			query+=" UPDATE board ";
			query+=" SET hit = nvl(hit,0) + 1 ";
			query+=" WHERE no = ? ";
			
			pstmt=conn.prepareStatement(query);
			pstmt.setInt(1, hitCount);
			
			pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		close();
	}
}
