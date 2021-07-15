package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestbookDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestbookVo;


@WebServlet("/guest")
public class GuestbookController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("[controller]");
		request.setCharacterEncoding("UTF-8");
		
		String action = request.getParameter("action");
		System.out.println(action);
		
		if("addList".equals(action)) {
			//리스트등록폼
			System.out.println("[addList]");
			
			//list loading From DB
			GuestbookDao guestbookDao = new GuestbookDao();
			List<GuestbookVo> guestbookList = guestbookDao.getList();
			
			//list attribute
			request.setAttribute("gbList", guestbookList);
			
			//포워딩
			WebUtil.forward(request, response, "WEB-INF/guestbook/addList.jsp");
			
		}else if("add".equals(action)) {
			//등록
			System.out.println("[add]");
			
			//parameter 호출
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String content = request.getParameter("content");
			
			//Vo묶기
			GuestbookDao gbDao = new GuestbookDao();
			GuestbookVo gbVo = new GuestbookVo(name, password, content);
						
			//DB넣기
			gbDao.guestInsert(gbVo);
			
			//redirect 
			WebUtil.redirect(request, response, "mysite/guest?action=addList");
			
		}else if("dform".equals(action)) {
			//방명록 삭제
			System.out.println("[dform]");
			
			//parameter 호출
			int no = Integer.parseInt(request.getParameter("no"));
			
			//data attribute
			request.setAttribute("no", no);
			
			//포워딩
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/deleteForm.jsp");
			
		}else if("delete".equals(action)) {
			//삭제
			System.out.println("[delete]");
			
			//parameter 호출
			int no = Integer.parseInt(request.getParameter("no"));
			String password = request.getParameter("password");
			
			//Vo묶기
			GuestbookDao gbDao = new GuestbookDao();
			GuestbookVo gbVo = new GuestbookVo(no, password);
			//생성자를 별도로 만들어야함 - 아니면 Vo의 Set이용.
			

			//DB반영
			gbDao.guestDelete(gbVo);
			
			//redirect
			WebUtil.redirect(request, response, "/mysite/guest?action=addList");
			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
