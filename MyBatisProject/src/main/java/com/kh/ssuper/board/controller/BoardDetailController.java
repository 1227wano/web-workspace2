package com.kh.ssuper.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.ssuper.board.model.service.BoardService;
import com.kh.ssuper.board.model.service.BoardServiceImpl;

@WebServlet("/detail.board")
public class BoardDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BoardDetailController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		
		BoardService boardService = new BoardServiceImpl();
		
		// 돌아온 정수를 확인
		if(boardService.increaseCount(boardNo) > 0) {
			
			request.setAttribute("board", boardService.selectBoard(boardNo));
			//request.setAttribute("list", boardService.selectReplyList(boardNo));
			request.getRequestDispatcher("/WEB-INF/views/board/detail.jsp").forward(request, response);
			
		}
		
		
		
		
		
		
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
