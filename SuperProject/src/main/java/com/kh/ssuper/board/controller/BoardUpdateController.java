package com.kh.ssuper.board.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.kh.ssuper.board.model.service.BoardService;
import com.kh.ssuper.board.model.vo.Attachment;
import com.kh.ssuper.board.model.vo.Board;
import com.kh.ssuper.common.MyRenamePolicy;
import com.oreilly.servlet.MultipartRequest;

@WebServlet("/update.board")
public class BoardUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BoardUpdateController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 1) POST => 인코딩 
		request.setCharacterEncoding("UTF-8");
		
		// 2) 값 뽑기 전
		// multipart방식으로 잘 왔을때만 내용이 수정되도로
		if(ServletFileUpload.isMultipartContent(request)) {
			
			// 파일 업로드
			// 1. 전송파일 용량 제한
			int maxSize = 1024 * 1024 * 10;
			
			// 2. 파일을 저장할 물리적인 경로
			String savePath = request.getSession()
									 .getServletContext()
								  	 .getRealPath("/resources/board_upfiles");
			
			// MultipartRequest객체 생성과 동시에 파일 업로드
			MultipartRequest multiRequest = 
					new MultipartRequest(request,
										 savePath,
										 maxSize,
										 "UTF-8",
										 new MyRenamePolicy());
			// --- 파일을 서버에 업로드 --- 
			
			// BOARD UPDATE
			
			// ATTACHMENT
			
			// case 1. 첨부파일이 존재하지 않는 경우 => BOARD UPDATE + AT는 암것도 하면 안됨
			// case 2 .첨부파일을 첨부, 기존 첨부파일이 존재 => BOARD UPDATE + AT UPDATE
			// case 3. 첨부파일을 첨부, 기존 첨부파일이 X => BOARD UPDATE + AT INSERT
			
			// 값뽑기
			String category = multiRequest.getParameter("category");
			String boardTitle = multiRequest.getParameter("title");
			String boardContent = multiRequest.getParameter("content");
			int boardNo = Integer.parseInt(multiRequest.getParameter("boardNo"));
			
			
			// VO로 가공
			Board board = new Board();
			board.setCategory(category);
			board.setBoardTitle(boardTitle);
			board.setBoardContent(boardContent);
			board.setBoardNo(boardNo);
			
			// Attachment 객체 선언만 
			// 실제 첨부파일이 존재하는 경우에만 => 객체 생성
			
			Attachment at = null;
			
			if(multiRequest.getOriginalFileName("reUpfile") != null) {	
				
				// 새로운 파일명이 존재한다면 객체 생성후, 원본명, 수정명, 파일경로 담기
				at = new Attachment();
				at.setOriginName(multiRequest.getOriginalFileName("reUpfile"));
				at.setChangeName(multiRequest.getFilesystemName("reUpfile"));
				at.setFilePath("/resources/board_upfiles");
				// 여기까지가 새롭게 업로드한 첨부파일에 대한 내용
				
				// INSERT / UPDATE 의 차이는?
				// INSERT : 이 첨부파일이 어떤 게시글에 달리는건가?
				// UPDATE : ATTACHMENT테이블의 몇 행인지?
				
				// 내가 무슨값을 가지고 어떤 연산을 해서 조건을 걸거지?
				
				if(multiRequest.getParameter("fileNo") != null) {
					// 새로운 첨부파일이 존재(첫if) + 원본 파일도 존재(두번째if)
					// ATTACHMENT => UPDATE => 원본파일번호 필요함
					// 기존 파일이 가지고 있던 FileNo를 at에 담을 것
					at.setFileNo(
							Integer.parseInt(multiRequest.getParameter("fileNo"))
								);
					
					// 기존에 존재하던 첨부파일을 삭제
					new File(savePath + "/" + multiRequest
							.getParameter("changeName"))
							.delete();
					
				} else {
					// 새로운 첨부파일이 존재(첫if) + 원본파일은 존재하지 않음
					// ATTACHMENT => INSERT
					// 어떤 게시글의 첨부 파일인지(REF_BNO)
					
					at.setRefBno(boardNo);
				}
				// 요건 안쪽 if블럭 끝나고니까 여기서 서비스 호출하지 않도록 조심!
			}
			
			// 서비스 요청 case↓
			// 1update
			// 2update
			// 1update + 1insert -> 트랜잭션 처리 필요
			
			int result = new BoardService().update(board, at);
			
			// 5) 결과에 따른 응답뷰 지정
			if(result > 0) { // 성공페이지
				
				request.getSession().setAttribute("alertMsg", "게시글 수정 성공");
				
				// sendRedirect를 쓰는 이유 : detail.jsp에서 출력시키려면 board내용을 DB에서 조회해서 request에 담아야하는데, 
				// 만들어 놓은 jsp를 재호출(redirection)해서 구현시키기 위해
				// http://localhost/super	/detail.board?boardNo=게시글번호
				
				response.sendRedirect(request.getContextPath()
										+ "detail.board?boardNo=" 
										+ boardNo);
				
			} else {	// 실패페이지
				request.setAttribute("failMsg", "게시글 수정 실패");
				request.getRequestDispatcher("/WEB-INF/views/common/fail_page.jsp")
					   .forward(request, response);
			}
			
		}
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
