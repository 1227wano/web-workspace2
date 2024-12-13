package com.kh.ssuper.board.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/enrollForm.thumbnail")
public class ThumbnailEnrollFormController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ThumbnailEnrollFormController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 포워딩용~

		request.getRequestDispatcher("/WEB-INF/views/board/thumbnail_enroll.jsp")
				.forward(request, response);

			
	
	
	
	
	
	
	
	
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
