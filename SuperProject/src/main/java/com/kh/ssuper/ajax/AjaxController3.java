package com.kh.ssuper.ajax;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.kh.ssuper.board.model.service.BoardService;
import com.kh.ssuper.board.model.vo.Board;
import com.kh.ssuper.member.model.vo.Member;

@WebServlet("/ajax3.do")
public class AjaxController3 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AjaxController3() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// GET방식
		
		// 값 뽑기
		int userNo = Integer.parseInt(request.getParameter("userNo"));
		
		// 서비스 -> DAO -> DB
		
		// 얘↓ 조회했다고 침
		Member member = new Member();
		member.setUserNo(userNo);
		member.setUserId("admin");
		member.setUserName("관리자");
		
		// JSON
		/*
		JSONObject memberObj = new JSONObject();
		memberObj.put("userNo",   member.getUserNo());
		memberObj.put("userId",   member.getUserId());
		memberObj.put("userName", member.getUserName());
		*/
		// response.getWriter().print(memberObj);
		
		response.setContentType("application/json; charset=UTF-8");
		// new Gson().toJson(member, response.getWriter()); 
		// -> VO객체의 필드명으로 키값을 담아줌
		
		
		List<Board> list = new ArrayList();
		list.add(new BoardService().selectBoard(userNo));
		list.add(new BoardService().selectBoard(userNo + 1));
		list.add(new BoardService().selectBoard(userNo + 2));
		
		// GSON : Google JSON 라이브러리
		Gson gson = new Gson();
		
		// gson.toJson()
		// [ 표현법 ]
		// gson.toJson(응답할객체, 응답할스트림)
		// 자동으로 키값이 전달하는 객체의 필드명이 됨!
		
		// VO객체 하나만 넘길 시 JSONObject 형태로 만들어서 응답!
		// List객체 응답시 JSONObject형태 안에 요소로 JSONObject를 만들어서 응답
		
		gson.toJson(list, response.getWriter());
		
		
		
		
		
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
