package com.kh.ssuper.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.ssuper.board.model.service.BoardService;
import com.kh.ssuper.board.model.vo.Board;

@WebServlet("/list.thumbnail")
public class ThumbnailListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ThumbnailListController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		List<Board> list = new BoardService().selectList();
		
		request.setAttribute("list", list);
		
		request.getRequestDispatcher("/WEB-INF/views/board/thumbnail_list.jsp")
				.forward(request, response);
	
	
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
