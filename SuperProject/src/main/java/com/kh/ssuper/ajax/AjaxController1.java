package com.kh.ssuper.ajax;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ajax1.do")
public class AjaxController1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AjaxController1() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// System.out.println("doGet메소드 호출");
		
		// 값 뽑기
		// request.getParameter()
		
		String str = request.getParameter("value");
		System.out.println("요청 시 전달 값 : " + str);
		
		
		// 응답
		
		String responseData = "AJAX 요청처리를 성공했습니다.";
		
		// 1) 응답 데이터 정보 설정 **************** 이거 꼭 하기 까먹지 말고~
		response.setContentType("text/html; charset=UTF-8");
		
		// 2) 응답
		response.getWriter().print(responseData);
		
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
