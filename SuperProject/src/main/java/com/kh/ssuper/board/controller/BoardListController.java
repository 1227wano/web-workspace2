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
import com.kh.ssuper.common.PageInfo;

@WebServlet("/list.board")
public class BoardListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BoardListController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
		// - 페이징 처리 - 
		// 필요한 변수들
		int listCount; // 현재 일반게시판의 총 게시글의 개수
		// => BOARD테이블 가서 COUNT(*) (STATUS = 'Y')해서 조회
		int currentPage; // 현재 사용자가 요청한 페이지
		// => request.getParameter("currentPage")로 뽑아서 씀
		int pageLimit; // 페이지 하단에보여질 페이징 개수 => 10개로 고정
		int boardLimit; // 한 페이지에 보여질 게시글의 최대 개수 => 10개로 고정
		
		int maxPage;	// 가장 마지막 페이지가 몇번페이지인지 (총 페이지의 개수)
		int startPage;	// 페이지 하단에 보여질 페이징 바의 시작 
		int endPage;	// 페이지 하단에 보여질 페이징바의 끝 수
		
		// * listCount : 총 게시글의 수
		listCount = new BoardService().selectListCount();	// 107
		// System.out.println(listCount);
		
		// * currentPage : 현재페이지(사용자가 요청한 페이지)
		currentPage = Integer.parseInt(request.getParameter("currentPage"));
		
		// * pageLimit : 페이지버튼 최대 개수 
		pageLimit = 10;
		
		// * boardLimit : 한페이지에 보여질 게시글의 최대 개수
		boardLimit = 10;
		
		// * maxPage : 가장 마지막 페이지가 몇 번 페이지인지(총 페이지의 개수)
		/*
		 * listCount, boardLimit에 영향을 받음
		 * 
		 * - 공식 구하기
		 * 	단, boardLimit이 10이라고 가정
		 * 
		 * 총 개수  한 페이지 개수	 나눗셈결과	 마지막페이지
		 * 100	 /	  10	  =		10.0 		10
		 * 107	 /	  10	  =		10.7 		11
		 * 111	 /	  10	  = 	11.1		12
		 * 
		 * => 나눗셈결과(listCount/boardLimit)를 올림처리를 할 경우 maxPage가 됨
		 * 
		 * but, 자바는 실수와 실수의 연산이라 소수는 나올수없다
		 * 
		 * 스텝.
		 * 1. listCount를 double로 형변환
		 * 2. listCount / boardLimit
		 * 3. Math.ceil() => 결과를 올림처리
		 * 4. 결과값을 int로 형변환
		 */
		maxPage = (int)Math.ceil((double)listCount / boardLimit);	// 더블과 더블의 연산이 됨
		
		// * startPage : 페이지 하단에 보여질 페이징버튼 중 시작 수
		/*
		 * pageLimit, currentPage 영향을 받음
		 * 
		 * - 공식 구하기
		 *   단, pageLimit이 10이라고 가정
		 *   
		 * startPage : 1, 11, 21, ... => n * 10 + 1
		 * 
		 * 만약 pageLimit이 5였다?
		 * 
		 * startPage : 1, 6, 11, 16, ... => n * 5 + 1
		 *   
		 * 즉, startPage == n * pageLimit + 1;
		 * 
		 * currentPage	 startPage
		 * 		1			1	
		 * 		5			1
		 * 		10			1
		 * 		11			11
		 * 		13			11
		 * 		21			21
		 * 		30			21
		 * 
		 * =>  1 ~ 10 : n * 10 + 1 ==> n == 0
		 * => 11 ~ 20 : n * 10 + 1 ==> n == 1
		 * 
		 *  1 ~ 10 / 10 => 0 ~ 1
		 * 11 ~ 20 / 10 => 1 ~ 2
		 * 
		 *  0 ~  9 / 10 => 0
		 * 10 ~ 19 / 10 => 1
		 * 
		 * n = (currentPage -1) / pageLimit
		 * 
		 * startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
		 */
		startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
		
		// * endPage : 페이지 하단에 보여질 페이징 버튼의 끝 수
		/*
		 * startPage, pageLimit에 영향을 받음(maxPage도 마지막 페이징바에 대한 영향을 끼침)
		 * 
		 * - 공식 구하기
		 * 	단, pageLimit이 10이라는 가정하에
		 * 
		 * startPage :  1 => endPage : 10
		 * startPage : 11 => endPage : 20
		 * startPage : 21 => endPage : 30
		 * 
		 * endPage = startPage + pageLimit - 1;
		 */
		endPage = startPage + pageLimit - 1;
		
		// startPage 11이라서 endPage에는 20이 들어갔는데[
		// maxPage가 11이라면?
		if(endPage > maxPage) endPage = maxPage;
		
		// 여기까지 총 7개의 변수를 선언 및 초기화까지 완료
		PageInfo pi 
		= new PageInfo(listCount, currentPage, pageLimit, boardLimit, maxPage, startPage, endPage);
		
		List<Board> list = new BoardService().fingAll(pi);
		
		
		request.setAttribute("list", list); 	// 게시글버튼을 위함
		request.setAttribute("pi", pi);			// 페이지버튼을 위함
		
		
		
		
		// 5) 응답화면 지정
		request.getRequestDispatcher("/WEB-INF/views/board/board_list.jsp")
			   .forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
