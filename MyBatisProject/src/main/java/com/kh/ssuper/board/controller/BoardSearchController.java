package com.kh.ssuper.board.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.ssuper.board.model.service.BoardService;
import com.kh.ssuper.board.model.service.BoardServiceImpl;
import com.kh.ssuper.board.model.vo.Board;
import com.kh.ssuper.common.PageInfo;
import com.kh.ssuper.common.pagination;

@WebServlet("/search.board")
public class BoardSearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BoardSearchController() {
        super();
    }
    
    // asassas

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 사용자가 선택한 옵션과 사용자가 입력한 키워드를 가지고
		// 페이징처리가 끝난 검색결과를 들고 갈것
		
		String condition = request.getParameter("condition");
		// "writer", "title", "content" 셋중 하나 옴
		
		String keyword = request.getParameter("keyword");
		// 사용자가 입력한 값
		
		// DTO
		Map<String, String> map = new HashMap();
		map.put("condition", condition);
		map.put("keyword", keyword);
		
		BoardService boardService = new BoardServiceImpl();
		
		int count = boardService.searchedCount(map);
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		// System.out.println(count);
		int pageLimit = 10;
		int boardLimit = 3;
		
		PageInfo pi = pagination.getPageInfo(count, currentPage, pageLimit, boardLimit);
		
		List<Board> list = boardService.selectSearchList(pi, map);
		
		
		request.setAttribute("list", list);
		request.setAttribute("pi", pi);
		request.setAttribute("keyword", keyword);  // 사용자가 입력한 값 확인하기
		request.setAttribute("condition", condition);	// 검색 후 셀렉트한 선택지 남기기
		
		
		request.getRequestDispatcher("/WEB-INF/views/board/board_list.jsp").forward(request, response);
		
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
