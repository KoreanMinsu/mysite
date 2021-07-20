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
			List<BoardVo> boardList = boardDao.getList(null);

			//attribute(data)
			request.setAttribute("boardList", boardList);
			
			//forward
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
		
		}else if("read".equals(action)) {
			//read
			System.out.println("[read]");
			//조회수 카운트 메소드 사용
			boardDao.hitCount(1);
			
			//parameter 호출 
			int no = Integer.parseInt(request.getParameter("no"));
			BoardVo boardVo = boardDao.getList(no);
			
			//attribute
			request.setAttribute("readBVo", boardVo);
			
			//foward
			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");
			
		}else if("delete".equals(action)){
			//delete
			System.out.println("[delete]");
			
			//parameter 호출 & db 반영
			int no = Integer.parseInt(request.getParameter("no"));
			boardDao.boardDelete(no);
			
			//redirect
			WebUtil.redirect(request, response, "/mysite/board?action=list");
			
		}else if("writeform".equals(action)) {
			//writeForm
			System.out.println("[writeForm");
			
			//세션정보 호출-로그인유저만
			HttpSession session = request.getSession();
			UserVo authUser=(UserVo)session.getAttribute("authUser");
			
			//로그인사용자만 클릭시 등록폼
			if(authUser != null) {
				//forward
				WebUtil.forward(request, response, "WEB-INF/views/board/writeForm.jsp");
			} //비회원용
			
			
		}else if("write".equals(action)) {
			//write
			System.out.println("[write]");
			
			//login 상태에서만 쓰기 가능 - 해당유저 세션값 가져오기
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			
			//parameter 호출
			String title = request.getParameter("title");
			String content = request.getParameter("content"); 
			int no = authUser.getNo();
			
			BoardVo boardVo = new BoardVo();
			//기본생성자에 지정
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
			
			//parameter 호출
			int no = Integer.parseInt(request.getParameter("no"));
			BoardVo boardVo = boardDao.getList(no);
			
			//attribute
			request.setAttribute("modifyBVo", boardVo);
			
			//foward
			WebUtil.forward(request, response, "WEB-INF/views/board/modifyform.jsp");
			
		}else if("modify".equals(action)) {
			//modify
			System.out.println("[modify]");
			
			//login 상태만 가능 - 세션값 호출
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			
			//parameter 호출
			String title = request.getParameter("title");
			String content = request.getParameter("content"); 
			int no = Integer.parseInt(request.getParameter("no"));
			int userNo = Integer.parseInt(request.getParameter("userNo"));

			
			//해당유저만 수정기능
			if(authUser.getNo() == no) {
				
				//Vo 묶기 & DB반영
				BoardVo boardVo = new BoardVo();
				boardVo.setTitle(title);
				boardVo.setContent(content);
				boardVo.setNo(no);
				boardVo.setUserNo(userNo);
				boardDao.boardModify(boardVo);
			}
			
			//redirect
			WebUtil.redirect(request, response, "/mysite/board?action=list");
			
		}else if("search".equals(action)) {
			//search
			System.out.println("[search]");
			
			//parameter 호출
			String keyword = request.getParameter("keyword");
			
			//data load from DB
			List<BoardVo> searchList = boardDao.getList(keyword);
			
			//attribute
			request.setAttribute("searchList", searchList);
			
			//forward
			WebUtil.forward(request, response, "WEB-INF/views/board/list.jsp");
			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
