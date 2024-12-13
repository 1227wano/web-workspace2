package com.kh.ssuper.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.ssuper.member.model.service.MemberService;
import com.kh.ssuper.member.model.vo.Member;

@WebServlet("/delete.me")
public class DeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DeleteController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		// 현재 로그인한 회원의 정보
		// 방법 1. input type="hidden" 몰래 숨겨서 전달
		// 방법 2. session에서 뽑아온다.
	
		HttpSession session = request.getSession();
		
		request.setCharacterEncoding("UTF-8");
	
		String userPwd = request.getParameter("userPwd");
		int userNo = ((Member)session.getAttribute("loginUser")).getUserNo();
	
		int result = new MemberService().deleteMember(userNo, userPwd);
		
		if(result > 0) {
			session.removeAttribute("loginUser");
			response.sendRedirect(request.getContextPath());	//request.getContextPath()만 있으면 웰컴페이지
			
		} else {
			request.setAttribute("failMsg", "비밀번호를 확인하세요");
			request.getRequestDispatcher("/WEB-INF/views/common/fail_page.jsp");
		}
	
	
	
	
	
	
	
	
	
	
	
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
