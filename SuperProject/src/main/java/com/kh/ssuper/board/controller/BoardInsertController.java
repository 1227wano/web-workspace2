package com.kh.ssuper.board.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.kh.ssuper.board.model.service.BoardService;
import com.kh.ssuper.board.model.vo.Attachment;
import com.kh.ssuper.board.model.vo.Board;
import com.kh.ssuper.common.MyRenamePolicy;
import com.oreilly.servlet.MultipartRequest;

@WebServlet("/insert.board")
public class BoardInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BoardInsertController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		// 1) post방식이니까 인코딩방식 설정
		request.setCharacterEncoding("UTF-8");
		
		// 2) 값 뽑기
		// String userNo = request.getParameter("userNo");
		// System.out.println(userNo);
		
		// form태그 요청 시 multipart/form-date형식으로 전송하는 경우
		// request.getParameter로는 값 뽑기가 불가능 -> 라이브러리 필요
		// com.oreilly.servlet 
		
		// 스텝 0) 요청이 murtipart방식으로 잘 전송이 되었는지부터 확인
		if(ServletFileUpload.isMultipartContent(request)) {
			// System.out.println("요청 잘 옴!!");
			
			// 스텝 1) 전송되는 파일의 처리를 위한 작업
			// 1_1. 전송파일 용량 제한
			/*
			 * 단위 정리 
			 * 
			 * bit X 8 => 1 Byte =(x1000)=> KiloByte => MegaByte => GigaByte => TeraByte => PetaByte => ...
			 * 
			 * 10Megabyte
			 * 
			 * 1KByte => 1024Byte
			 * 
			 * 1MByte => 1024KByte
			 */
			int maxSize = 1024 * 1024 * 10;
			
			// 1_2. 전달된 파일을 저장할 서버의 폴더 경로 알아내기
			// HttpServletRequest
			// HttpSession
			// ServletContext
			// getRealPath()
			// => 인자값으로 webapp부터 board_upfiles폴더까지의 경로를 문자열로 전달 
			
			HttpSession session = request.getSession();
			ServletContext application = session.getServletContext();
			String savePath = application.getRealPath("/resources/board_upfiles");
			
			// 스텝 2) 서버에 업로드
			
			// 동명 파일이 오는경우 ->  a.jpg a2.jpg a3.jpg
			// -> 우리의 규칙대로 파일명을 설정해보자! ~> MyRenamePolicy
			
			/*
			 * - HttpServletRequest request
			 * => 
			 * MultipartRequest multiRequest객체로 변환
			 * 
			 * [ 표현법 ]
			 * 
			 * MultiRequest multiRequest = 
			 * new MultiRequest(request, savePath, maxSize, 인코딩("UTF-8"),
			 * 					파일명을 수정해주는 객체);
			 * 
			 * MultiRequest객체를 생성하면 파일이 업로드된다!
			 * 
			 * 사용자가 올린 파일명은 해당 폴더에 업로드하지 않는 것이 일반적
			 * 
			 * Q) 파일명을 수정하는 이유는?
			 * A) 같은 파일명이 존재할 수 있으니까
			 * 	  파일명에 한글 / 특수문자 / 공백문자 포함된 경우 서버에 따라 문제가 일어날 수 있음
			 * 
			 * cor.jar => 기본적으로 파일명을 수정해주는 객체
			 * => 내부적으로 rename()호출하면서 파일명 수정
			 * => bono.jpg bono1.jpg bono2.jpg
			 * 
			 * => 우리 입맛대로 파일명을 수정해서 겹치지 않게 하기 위해
			 *    MyRenamePolicy라는 클래스를 만들었음
			 */
			
			MultipartRequest multiRequest = 
					new MultipartRequest(request, savePath, maxSize, "UTF-8",
										 new MyRenamePolicy());
			
			// -- 파일 업로드 --
			// BOARD 테이블에 INSERT
			// 2) 값 뽑기
			String userNo = multiRequest.getParameter("userNo");
			String title = multiRequest.getParameter("title");
			String content = multiRequest.getParameter("content");
			String categoryNo = multiRequest.getParameter("category");
			
			// 3) VO객체로 가공 => BOARD테이블에 INSERT할 내용
			Board board = new Board();
			board.setBoardTitle(title);
			board.setBoardContent(content);
			board.setBoardWriter(userNo);
			board.setCategory(categoryNo);
			
			// 3_2) 첨부파일의 경우 => 선택적
			Attachment attachment = null;
			
			// 첨부파일의 유무를 파악
			// multiRequest.getOriginalFileName("키값") <- 키 : input타입 file의 name속성
			// 첨부파일이 존재하면 "원본파일명" // 존재하지 않는다면 null값을 반환
			if(multiRequest.getOriginalFileName("upfile") != null) {
				
				// 첨부파일 있다~ => VO객체로 가공
				attachment = new Attachment();
				
				// originName
				attachment.setOriginName(multiRequest.getOriginalFileName("upfile"));
				
				// changeName
				attachment.setChangeName(multiRequest.getFilesystemName("upfile"));
				
				// filePath
				attachment.setFilePath("resources/board_upfiles");
			}
			
			// 4) 서비스 호출
			int result = new BoardService().insert(board, attachment);
			// 동시 트랜잭션 처리가 안되니, 매개변수를 따로 보내는건 불가...!!
			
			// 5) 
			if(result > 0) {  // 성공
				
				request.getSession().setAttribute("alertMsg", "게시글등록 성공");
				
				response.sendRedirect(request.getContextPath() + "/list.board?currentPage=1"); 
				// 포워딩은 안됨 -> list에 담아서 출력하는 과정에서 적합하지 못함
				
			} else {	// 실패
				
				// 실패했을 경우 이미 업로드한 파일을 삭제
				if(attachment != null) {
					new File(savePath + "/" + attachment.getChangeName()).delete();
					// 파일 삭제 메소드
				}
				
				request.setAttribute("failMsg", "게시글 작성 실패");
				request.getRequestDispatcher("/WEB-INF/views/common/fail_page.jsp")
					   .forward(request, response);
				
			}
			
		}
	
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
