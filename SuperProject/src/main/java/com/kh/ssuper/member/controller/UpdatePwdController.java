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

@WebServlet("/updatePwd.me")
public class UpdatePwdController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UpdatePwdController() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		// 1) POST 방식 -> 인코딩
		request.setCharacterEncoding("UTF-8");
		
		// 2) request로부터 값 뽑기
		String userPwd = request.getParameter("userPwd");		// 기존 비밀번호
		String updatePwd = request.getParameter("changePwd");	// 새 비밀번호
		
		int userNo = Integer.parseInt(request.getParameter("userNo"));
		
		// 3) 식별할 수 있는게 필요하다
		// sql문
		// UPDATE SET USER_PWD = '새 비밀번호'
		// WHERE USER_PWD = '기존 비밀번호' AND USER_NO = '사용자의 유저번호(PK)'
	
		int result = new MemberService().updatePwd(userNo, userPwd, updatePwd);
		
		HttpSession session = request.getSession();
		
		// 결과값 보고 응담화면 지정
		if(result > 0) {
			Member loginUser = ((Member)session.getAttribute("loginUser"));
			loginUser.setUserPwd(updatePwd);
		} 
		session.setAttribute("alertMsg", result > 0 ? 
									  "비밀번호 성공~" : "비밀번호 변경 실패!");
		// 성공이든 실패든 마이페이지로 응답
		response.sendRedirect(request.getContextPath() + "/myPage.me");
	}

}
