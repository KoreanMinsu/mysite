package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@WebServlet("/board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//위치 확인
		System.out.println("[controller]");
		request.setCharacterEncoding("UTF-8");
		
		//Dao 반복 사용 최소화
		BoardDao boardDao = new BoardDao();
		
		String action = request.getParameter("action");
		System.out.println(action);
		
		if("list".equals(action)) {
			//list
			System.out.println("[List]");
			
			//data loading from DB
			List<BoardVo> boardList = boardDao.getList();

			//attribute(data)
			request.setAttribute("boardList", boardList);
			
			//forward
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
		
		}else if("read".equals(action)) {
			//read
			System.out.println("[read]");
			//조회수 카운트 생각하기?? 메소드 추가? 오라클 설정?
			
			int no = Integer.parseInt(request.getParameter("no"));
			BoardVo boardVo = boardDao.getList(no);
			
			//attribute
			request.setAttribute("readBVo", boardVo);
			
			//foward
			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");
			
		}else if("writeform".equals(action)) {
			//writeForm
			System.out.println("[writeForm");
			
			
		}else if("write".equals(action)) {
			//write
			System.out.println("[write]");
			
			//login 상태에서만 쓰기 가능 - 해당유저 세션값 가져오기
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			
			String title = request.getParameter("title");
			String content = request.getParameter("content"); 
			int no = authUser.getNo();
			
			BoardVo boardVo = new BoardVo();
			boardVo.setTitle(title);
			boardVo.setContent(content);
			boardVo.setNo(no);
			
			boardDao.boardInsert(boardVo);
			
			//redirect
			WebUtil.redirect(request, response, "/mysite/board?action=list");
			
		}
		else if("modifyForm".equals(action)) {
			//modifyForm
			System.out.println("[modifyForm]");
			
			
		}else if("modify".equals(action)) {
			//modify
			System.out.println("[modify]");
		}else if("search".equals(action)) {
			//search
			System.out.println("[search]");
		}
 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
	}

}
