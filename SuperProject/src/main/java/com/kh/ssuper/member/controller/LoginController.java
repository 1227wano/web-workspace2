package com.kh.ssuper.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.ssuper.member.model.service.MemberService;
import com.kh.ssuper.member.model.vo.Member;

// 제일 처음 확인할 내용! ↓
@WebServlet("/login.me")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/*
		 * <HttpServletRequest, HttpServletResponse>
		 * 
		 * - request : 서버로 요청할 때의 정보(요청 시 전달값, 요청전송방식, 요청한 사용자 정보 등등)
		 * - response : 요청에 대해 응답하고자 할때 사용하는 객체
		 */
		
		// 절차 
		// 1) GET? POST? => 요청 방식이 POST라면 인코딩작업
		request.setCharacterEncoding("UTF-8");
		
		// 2) 요청시 전달값이 있나? 없나? 생각해 => 있으면 뽑아서 가공해야되니깐
		// request.getParameter("키값");
		// request.getParameterValues("키값": String[] => checkbox일 경우 사용)
		
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
		
		// System.out.println(userId);		
		// System.out.println(userPwd);
		
		Member member = new Member();
		member.setUserId(userId);
		member.setUserPwd(userPwd);
		
		// 해당 요청을 처리해주는 서비스의 메소드를 호출
		Member loginUser = new MemberService().login(member);
		// 모든 조건을 만족하는 행이 존재한다면 반환된 값에는 필드값이 회원정보로 꽉찬 주소값
		// 하나의 조건이라도 만족하지 못했다면 null 값 
		
		// System.out.println(loginUser);
		
		// 4) 처리된 결과를 사용자가 보게 될 응답화면 지정
		// 스텝 1. request객체 응답화면에 보여질 값 담기 => setAttribute()
		// 스텝 2. RequestDispatcher객체 생성 => 뷰 지정
		// 스텝 3. RequestDispatcher객체로부터 forward() 호출
		
		// 1. 어딘가에 응답화면에 보여질 값 담기 (request, session, application, page)
		
		/*
		 * 큰 순서
		 * 
		 * 1) application : 웹 애플리케이션 전역에서 언제나 꺼내 쓸 수 있음
		 * 					(Java클래스에서 뽑을 수 있음)
		 * 
		 * 2) session : 모든 JSP와 Servlet에서 꺼내 쓸 수 있음
		 * 				단, 직접적으로 session에 담은 값을 지우기 전까지만
		 * 				세션이 끊기는 경우 : 브라우저 종료, 서버 멈췄다, 코드로 지웠다
		 * 
		 * 3) request : 해당 request를 포워딩한 응답 JSP에서만 쓸 수 있음
		 * 				요청부터 응답페이지까지만 딱 쓸 수 있음
		 * 
		 * 4) page	: 담은 값을 해당 JSP페이지에서만 쓸 수 있음
		 * 
		 * 
		 * => session, request를 많이 사용함
		 * 
		 * => 공통적으로 데이터를 담고자 할 떄 : XXX.setAttribute(키, 밸류);
		 * 			  데이터를 뽑고자 할 때 : XXX.getAttribute(키);
		 * 			  데이터를 지우고자 할때 : XXX.removeAttribute(키);
		 * 
		 * 예시) 
		 * 로그인 시 : session.setAttribute("userInfo", loginUser);
		 * 로그아웃 시 : session.removeAttribute("userInfo");
		 */
		
		// 2. RequestDispatcher 객체 생성 (응답할 뷰 지정) => forward();
		
		// 로그인에 실패할 수도 있음 / 성공할 수 도 있음
		
		if(loginUser != null) {
			// 로그인 성공 => index.jsp 응답
			
			// 사용자의 정보 넘기기
			
			// 로그인한 회원의 정보를 로그아웃하거나
			// 브라우저를 종료하기 전까지는 계속 사용할 예정이기 때문에
			// session에 담기
			
			// 스텝 1. session의 attribute에 사용자 정보 담기
			// session객체의 type : HttpSession 
			// => 현재 요청 보낸 Client의 Session : request.getSession();
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", loginUser);
			
			// * 포워딩
			/*
			// 스텝 2. RequestDispatcher객체 생성
			RequestDispatcher view = request.getRequestDispatcher("/index.jsp");
			
			// 스텝 3. forward();
			view.forward(request, response);
			*/
			
			// 클라이언트가 localhost/super (매핑값) 을 요청하게 하면,
			// index.jsp가 index.html로 바뀌거나 해도 문제가 없음!
			
			// sendRedirect : Client에게 url을 다시 요청하게 함
			// response객체를 이용
			// response.sendredirect("/재요청경로")
			session.setAttribute("alertMsg", "로그인 성공");
			response.sendRedirect("/super");
			
		} else {
			// 로그인 실패
			
			// 에러 메시지 넘기기
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
