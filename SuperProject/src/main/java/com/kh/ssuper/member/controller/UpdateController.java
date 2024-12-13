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

@WebServlet("/update.me")
public class UpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UpdateController() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 1) POST방식 => 인코딩
		request.setCharacterEncoding("UTF-8");
		
		// 2) request로부터 값 뽑기
		// userId, userName, email, interest(checkbox)
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
		String userName = request.getParameter("userName");
		String email = request.getParameter("email");
		String[] interestArr = request.getParameterValues("interest");
		
		String interest = interestArr != null ?
				String.join(",", interestArr) : "";
		
		// 3) VO객체로 가공
		Member member = new Member();
		member.setUserId(userId);
		member.setUserPwd(userPwd);
		member.setUserName(userName);
		member.setEmail(email);
		member.setInterest(interest);
		
		// 4) Service에 메서드 호출
		int result = new MemberService().update(member);
		
		// 5) 결과값에 따라서 응답화면 지정
		if(result > 0) {
			HttpSession session = request.getSession();
			session.setAttribute("alertMsg", "정보 수정에 성공했습니다 추카추카~");
			
			// 응답화면으로 다시 내정보페이지로 보내기
			// 1. sendRedirect
			// 2. forward
			
			// 발생한 문제점!
			// Update에 성공했는데, session의 loginUser키값에는
			// 로그인 당시 조회했던 값들이 필드에 담겨있기 때문에, 
			// 마이페이지에서 갱신되기 전 값들이 출력됨
			
			// 목표 => DB에 가서 갱신된 회원정보 들고오기
			// 현재 update에 성공한 행을 식별할 수 있는 값이 존재한가? => userId
			// new MemberService().selectOne(userId);
			// 근데 이미 login이라는 한 회원의 정보를 조회하는 메소드는 있다. 바퀴를 또 만들어..?
			// 로그인 -> 아이디 / 비밀번호
			
			String updatePwd = ((Member)session.getAttribute("loginUser")).getUserPwd();
			// loginUser를 Member타입으로 한 후, 연산자 우선순위를 위해 모두 ()로 묶어서 getter로 빼온다
			
			Member selectMember = new Member();
			selectMember.setUserId(userId);
			selectMember.setUserPwd(updatePwd);
			
			Member UpdateMember = new MemberService().login(selectMember);
			
			session.setAttribute("loginUser", UpdateMember);
			
			// 1.
			response.sendRedirect(request.getContextPath() + "/myPage.me");
			
			/* 2. <- 중복이다
			request.getRequestDispatcher("/WEB-INF/views/member/my_page.me")
					.forward(request, response);
			*/
			
		} else {
			request.setAttribute("failMsg", "회원 정보 수정에 실패했습니다");
			request.getRequestDispatcher("/WEB-INF/views/common/fail_page.jsp")
					.forward(request, response);
			
		}
		
		
		
		
	}

}
