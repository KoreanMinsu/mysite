package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;


@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("[UserController]");
		
		request.setCharacterEncoding("UTF-8");
		
		String action = request.getParameter("action");
		System.out.println(action);
		
		if("joinForm".equals(action)) {
			//회원가입폼
			System.out.println("[UserController-joinForm]");
		
			//회원가입폼 포워드
			WebUtil.forward(request, response, "WEB-INF/views/user/joinForm.jsp");
		
		}else if("join".equals(action)) {
			//회원가입
			System.out.println("[UserController-join]");
			
			//파라미터 호출
			String userId = request.getParameter("id");
			String password = request.getParameter("pw");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			//System.out.println(userId +"," +password +","+ name+"," + gender);  확인용
			
			//vo 만들기
			UserVo userVo = new UserVo(userId, password, name, gender);
			System.out.println(userVo);
			
			//dao.insert(vo)로 DB저장
			UserDao userDao = new UserDao();
			int count = userDao.userInsert(userVo);
			
			//포워드
			WebUtil.forward(request, response, "WEB-INF/views/user/joinOk.jsp");
			
		}else if("loginForm".equals(action)) {
			//로그인폼
			System.out.println("[LoginForm]");
			
			//포워드
			WebUtil.forward(request, response, "WEB-INF/views/user/loginForm.jsp");
			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
