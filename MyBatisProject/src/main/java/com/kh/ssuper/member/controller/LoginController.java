package com.kh.ssuper.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.ssuper.member.model.service.MemberServiceImpl;
import com.kh.ssuper.member.model.vo.Member;

// 제일 처음 확인할 내용! ↓
@WebServlet("/login.me")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
		
		
		Member member = new Member();
		member.setUserId(userId);
		member.setUserPwd(userPwd);
		
		Member loginUser = new MemberServiceImpl().login(member); 	// 바꿔주자
		
		if(loginUser != null) {
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", loginUser);
			
			session.setAttribute("alertMsg", "로그인 성공");
			response.sendRedirect(request.getContextPath());	// 바꿔주자
			
		} else {
			request.setAttribute("failMsg", "로그인에 실패했습니다.");
			
			RequestDispatcher view = 
			request.getRequestDispatcher("/WEB-INF/views/common/fail_page.jsp");
			
			view.forward(request, response);
			
		}
		
		
		
		
		
		
		
																																																																																																																																																																																																																																																																																																																																																																																																																																												
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
