package com.kh.ssuper.ajax;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.ssuper.board.model.service.BoardService;
import com.kh.ssuper.board.model.vo.Reply;
import com.kh.ssuper.member.model.vo.Member;

@WebServlet("/insert.reply")
public class AjaxInsertReplyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AjaxInsertReplyController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		String content = request.getParameter("content");
		
		// session에 있는 유저넘버뽑아서 인서트 해야함
		int userNo = ((Member)request.getSession()
									 .getAttribute("loginUser"))
									 .getUserNo();
		
		// VO에 담아ㅏ => Reply
		Reply reply = new Reply();
		reply.setRefBno(boardNo);
		reply.setReplyContent(content);
		reply.setReplyWriter(String.valueOf(userNo)); // userNo는 int인데 Writer스트림은 String으로 넘겨야함
		
		// Service
		int result = new BoardService().insertReply(reply);
		
		// 응답화면~ 은 지정하지 않아. 왜냐면 이미 정해져 있으니까. 응답데이터만 넘겨
		
		// Gson, Json 은 돌려줘야 할 값이 여러개일때 객체형으로 반환하고자 사용하는 것
		// 지금은 돌려줄 값이 하나라 굳이!
		
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(result > 0 ? "success" : "fail"); // 삼항연산자는 (백엔드가 프론드로의)誠意
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
