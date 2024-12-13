package com.kh.ssuper.ajax;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

@WebServlet("/ajax2.do")
public class AjaxController2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AjaxController2() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// POST => encoding
		request.setCharacterEncoding("UTF-8");
		
		// 값 뽑기
		String name = request.getParameter("name");
		int age = Integer.parseInt(request.getParameter("age"));	// 이부분 조심하자
		
		// VO가공 => Service~ 
		
		// 결과 응답
		// 인코딩설정(필수)
		/*
		response.setContentType("text/html; charset=UTF-8");
		
		// 값 넘기기 --> 출력
		// response.getWriter().print(name, age); 두개를 하나로 보낼 방법이..? 
		
		String responseData = "이름 : " + name + ", 숫자 : " + age;
		
		response.getWriter().print(responseData);
		*/
		
		// AJAX를 활용해서 실제 값을 여러개 응답하고 싶다면,
		// JSON(JavaScript Object Notation)
		// 데이터 전송 시 이용할 수 있는 포맷형식 중 하나
		// 1. 자바스크립트의 배열 객체 => [value, value, value]
		// 2. 자바스크립트의 일반 객체 => {key:value, key:value, key:value}
		
		// 지금 자바에서 위의 자바스크립트 객체 모양 [name, age] 처럼 만들려면..!
		
		// String responseData = "['" + name + "', " + age + "]";
		
		// System.out.println(responseData);
		
		//response.setContentType("text/html; charset=UTF-8");
		/*
		 * JSON형태로 처리 시 사용하는 클래스
		 * => 자바에서는 기본적으로 제공 X => 라이브러리 추가
		 * 
		 * 1. JSONArray
		 * 2. JSONObject
		 */
		
		/* 1번
		JSONArray responseData = new JSONArray(); // []일케 생긴것
		// 요소 추가 => add()
		responseData.add(name); // ['홍길동']
		responseData.add(age);  // ['홍길동', 14]
		그러나 배열은 인덱스로 관리라 번거롭다
		*/
		
		JSONObject obj = new JSONObject();
		/// 요소추가 => put()
		obj.put("name", name);
		obj.put("age", age);
		
		
		
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(obj);
		
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
