package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

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
			String password = request.getParameter("password");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			//System.out.println(userId +"," +password +","+ name+"," + gender);  확인용
			
			//vo 만들기
			UserVo userVo = new UserVo(userId, password, name, gender);
			System.out.println(userVo);
			
			//dao.insert(vo)로 DB저장
			UserDao userDao = new UserDao();
			userDao.userInsert(userVo);
			
			//포워드
			WebUtil.forward(request, response, "WEB-INF/views/user/joinOk.jsp");
			
		}else if("loginForm".equals(action)) {
			//로그인폼
			System.out.println("[LoginForm]");
			
			
			//포워드
			WebUtil.forward(request, response, "WEB-INF/views/user/loginForm.jsp");
			
		}else if("login".equals(action)) {
			//login
			System.out.println("[login]");
			
			//parameter 호출
			String id = request.getParameter("id");
			String pw = request.getParameter("password");
			//System.out.println(id +"," +pw);
			
			//Vo묶기
			UserDao userDao = new UserDao();
			//DB반영
			UserVo userVo = userDao.getUser(id, pw);
		
			if(userVo !=null) {
				System.out.println("로그인성공");
				//성공시(ID PASS 일치) 세션 저장
				HttpSession session = request.getSession();
				session.setAttribute("authUser", userVo); //JSP 데이터전달 REQUEST.setattribute와 비교
				
				//redirect
				WebUtil.redirect(request, response, "/mysite/main");
				
			}else {
				System.out.println("로그인 실패");
			
				//redirect - login form page
				WebUtil.redirect(request, response, "/mysite/user?action=loginForm&result=fail");
			}
			
		}else if("logout".equals(action)) {
			//logout
			System.out.println("[logout]");
			
			//세션의 정보삭제(authUser)
			HttpSession session = request.getSession();
			session.removeAttribute("authUser");
			session.invalidate();
			
		} if("modifyForm".equals(action)) {
			//modifyForm : 로그인 상태에서만 - 세션활성화상태 어떻게?
			System.out.println("[modifyForm]");
			
			//parameter 호출 (세션포함)
			HttpSession session = request.getSession();
			UserVo userVo= (UserVo)session.getAttribute("authUser");
			
			//jsp attribute
			request.setAttribute("authUser", userVo);
					
			//forward
			WebUtil.forward(request, response, "/WEB-INF/views/user/modifyForm.jsp");
		
		} else if("update".equals(action)) {
			//update
			System.out.println("[update]");
			
			//parameter 추출
			int no = Integer.parseInt(request.getParameter("no"));
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			
			//VO 묶고 DB반영
			UserDao userDao = new UserDao();
			UserVo userVoUpdate = new UserVo(no, id, password, name, gender);
			userDao.userUpdate(userVoUpdate);
			
			//세션업데이트
			request.getSession().setAttribute("authUser", userVoUpdate);
			
			//redirect
			WebUtil.redirect(request, response, "/mysite/main");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
