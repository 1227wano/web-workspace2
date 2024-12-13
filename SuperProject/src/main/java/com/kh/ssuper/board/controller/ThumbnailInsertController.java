package com.kh.ssuper.board.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

@WebServlet("/save.thumbnail")
public class ThumbnailInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ThumbnailInsertController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 1) 인코딩
		request.setCharacterEncoding("UTF-8");
		
		// 2) 첨부파일 -> mutltipart/form-data => 조건제시 => 서버로 파일을 올려주자
		if(ServletFileUpload.isMultipartContent(request)) {
			
			// 1. MultipartRequest객체 생성
			// 1_1. 전송용량 제한(100MByte)
			int maxSize = 1024 * 1024 * 100;  //100메가 = 대충100000000
			
			// 1_2. 저장할 경로를 구해야함!
			String savePath = request.getSession()
									 .getServletContext()
									 .getRealPath("/resources/image_upfiles");
			
			// 2. MultipartRequest객체를 생성하면서 파일의 이름을 바꿔주면서 업로드
			MultipartRequest multiRequest =
					new MultipartRequest(request,
										 savePath,
										 maxSize,
										 "UTF-8",
										 new MyRenamePolicy());
			
			// ------------------------여기까지가 파일업로드-----------------------------------------
			
			// 3) multiRequest로부터 값뽑아 변수에 담기 => getParameter() 호출
			String boardTitle = multiRequest.getParameter("title");
			String boardContent = multiRequest.getParameter("content");
			String userNo = multiRequest.getParameter("userNo");
			
			// 4) 가공 
			Board board = new Board();
			board.setBoardTitle(boardTitle);
			board.setBoardContent(boardContent);
			board.setBoardWriter(userNo);
			
			// Attachment
			// => 사진게시판 작성 폼 required
			// => 게시글 한개당 최소 한개의 첨부파일은 존재
			
			// file1, file2, file3, file4 
			
			// 여러 개의 VO를 묶어서 다룰 경우 List를 사용하면 편하지 않을까..?
			List<Attachment> list = new ArrayList(); 
			
			// 키값 : file1 ~ file4
			for(int i = 1; i <= 4; i++) {
				
				String key = "file" + i;
				
				// 조건 검사 name 속성 값을 이용해서 파일이 첨부되었느가
				if(multiRequest.getOriginalFileName(key) != null) {
					// 파일이 존재한다
					
					Attachment at = new Attachment();
					at.setOriginName(multiRequest.getOriginalFileName(key));
					at.setChangeName(multiRequest.getFilesystemName(key));
					at.setFilePath("resources/image_upfiles");  // 이미지는 상대경로로 해야해서 맨앞의 /뺴기!
					
					// 파워레벨
					at.setFileLevel(i == 1 ? i : 2);
					
					list.add(at);
				}
			}
			
			// 5) 서비스 요청
			int result = new BoardService().insertThumbnailBoard(board, list);
			
			// 6) 결과에 따른 응답화면 지정
			if(result > 0) {
				request.getSession().setAttribute("alertMsg", "성공");
				response.sendRedirect(request.getContextPath() + "/list.thumbnail");
			} else {
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
