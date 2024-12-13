package com.kh.ssuper.ajax;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.kh.ssuper.board.model.service.BoardService;
import com.kh.ssuper.board.model.vo.Reply;

@WebServlet("/list.reply")
public class AjaxReplyListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AjaxReplyListController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// get방식 -> 인코딩X
		
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		
		// VO가공 은 하나니까 넘어가
		
		// Servlet 요청 -> BoardService
		List<Reply> replyList = new BoardService().selectReplyList(boardNo);
		
		// 응답
		// GSON을 이용해보자
		
		// 형식, 인코딩
		response.setContentType("application/json; charset=UTF-8");
		
		new Gson().toJson(replyList, response.getWriter());
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
