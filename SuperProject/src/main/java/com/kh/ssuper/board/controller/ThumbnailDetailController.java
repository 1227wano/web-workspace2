package com.kh.ssuper.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.ssuper.board.model.service.BoardService;
import com.kh.ssuper.board.model.vo.Attachment;
import com.kh.ssuper.board.model.vo.Board;

@WebServlet("/detail.thumbnail")
public class ThumbnailDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ThumbnailDetailController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
	
		// 가공은 하나라 패스
		
		// 서비스 요청
		Board board = new BoardService().findById(boardNo);
	
		// 기존에 만들어두었던 findById() 호출시
		// 일반 게시글 조회할 떈 카테고리 컬럼에 null값이 들어간 행이 없었지만, 
		// 사진 게시글 조회할 땐 카테고리 컬럼 값이 전부 null이기 때문에 innerJoin으로 조회 불가능
		// => CATEGORY_NO컬럼을 기준으로 일치하는 컬럼, 그렇지 않은 컬럼 BOARD는 다 조회할거야~
		// 기존의 innerJoin을 outerJoin으로 바꿔버렸다.
		
		// Attachment테이블 조회 
		List<Attachment> list = new BoardService().selectImageList(boardNo);
		
		request.setAttribute("list", list);
		request.setAttribute("board", board);
		
		// -> 수업자료에 올려준 걸로 thumbnail_detail.jsp 만들기
		
		request.getRequestDispatcher("/WEB-INF/views/board/thumbnail_detail.jsp")
				.forward(request, response);
		
		// 서브 껐다 끄고 해보기~
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
