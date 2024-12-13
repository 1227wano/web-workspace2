package com.kh.ssuper.board.model.service;

import java.sql.Connection;
import java.util.List;

import com.kh.ssuper.board.model.dao.BoardDao;
import com.kh.ssuper.board.model.vo.Attachment;
import com.kh.ssuper.board.model.vo.Board;
import com.kh.ssuper.board.model.vo.Category;
import com.kh.ssuper.board.model.vo.Reply;
import com.kh.ssuper.common.JDBCTemplate;
import com.kh.ssuper.common.PageInfo;

public class BoardService {
	
	static {
		JDBCTemplate.registDriver();
	}

	public int selectListCount() {

		Connection conn = JDBCTemplate.getConnection();
		
		int result = new BoardDao().selectListCount(conn);
		
		JDBCTemplate.close(conn);
		
		return result;
	}

	public List<Board> fingAll(PageInfo pi) {
		
		Connection conn = JDBCTemplate.getConnection();
		// DB에서 인라인 뷰 활용
		// 1) ORDER BY절은 순서가 가장 마지막인데 정렬이 끝난 상태가 필요함!
		//    일단 정렬해주는 SELECT문을 만들고 => 서브쿼리
		// 2) 서브쿼리를 FROM절에 넣음 + ROWNUM
		
		/*
		 * boardLimit이 10이라는 가정하에
		 * currentPage == 1 => 시작값 : 1, 끝값 : 10
		 * currentPage == 2 => 시작값 : 11, 끝값 : 20
		 * 
		 * 시작값 = (currentPage - 1) * boardLimit + 1
		 * 끝값 = 시작값 + boardLimit - 1;
		 */
		
		
		int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
		int endRow = startRow + pi.getBoardLimit() - 1;
		
		List<Board> boardList = new BoardDao().findAll(conn, startRow, endRow);
		
		JDBCTemplate.close(conn);
		
		return boardList;
		
	}

	public List<Category> selectCategory() {
		
		Connection conn = JDBCTemplate.getConnection();
		
		List<Category> list = new BoardDao().selectCategory(conn);
		
		JDBCTemplate.close(conn);
		
		return list;
	}

	public int insert(Board board, Attachment attachment) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		// 1) Board테이블에 INSERT
		int boardResult = new BoardDao().insertBoard(conn, board);
		
		// 2) Attachment테이블에 INSERT
		int attachmentResult = 1;
		if(attachment != null) {
			attachmentResult = 
					new BoardDao().insertAttachment(conn, attachment);
		}
		
		// 3) 트랜잭션 처리
		// boardResult가 성공이고 attachmentResult도 성공이면
		// commit
		if((boardResult * attachmentResult) > 0) {		// aR가 기본값 1이니까 bR과 곱한게 0이면 bR이 0인지 확인가능!
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return (boardResult * attachmentResult);
	}

	public Board findById(int boardNo) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		BoardDao boardDao = new BoardDao(); // 메소드 두개니까 미리 선언하기~
		
		Board board = null;	// 아래의 if문 안에서 board를 전달받을 예정이라
		
		// 1. 조회수 증가
		int result = boardDao.increaseCount(conn, boardNo);
		
		// 2. 조회수 증가 성공시, 한 행 조회
		if(result > 0) {
			board = boardDao.findById(conn, boardNo);
			JDBCTemplate.commit(conn);
		} 
		JDBCTemplate.close(conn);	// 자원반납dmf if문 안에서 하면 조회수 증가했을때만 자원반납 되니까 밖에서!
		
		return board;
	}

	public Attachment selectAttachment(int boardNo) {

		Connection conn = JDBCTemplate.getConnection();
		
		Attachment attachment = new BoardDao().selectAttachment(conn, boardNo);
		
		JDBCTemplate.close(conn);
		
		return attachment;
	}
	
	public Board selectBoard(int boardNo) {	// 위의 findById는 조회수도 증가시켜버리니까 board만 들고오는걸로 새로 만들기 
		
		Connection conn = JDBCTemplate.getConnection();
		
		Board board = new BoardDao().findById(conn, boardNo);
		
		JDBCTemplate.close(conn);
		
		return board;
		
	}

	public int update(Board board, Attachment attachment) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		// BOARD 테이블부터
		int result = new BoardDao().updateBoard(conn, board);
		
		// ATTACHMENT
		int attachmentResult = 1;	// 0이면 커밋하기 어려움~
		
		// 새롭게 파일을 첨부했을 경우
		if(attachment != null) {
			
			if(attachment.getFileNo() != 0) { // 단, DB의 시퀀스로 만든거라 기본값이 1인데 항상 쓰는건 아니니 0이 들어갈수도 있음 
				// 기존에 첨부파일이 존재했을 경우 => UPDATE
				attachmentResult = 
						new BoardDao().updateAttachment(conn, attachment);
			} else {
				// 기존에 첨부파일이 존재하지 않았을 경우 => INSERT
				attachmentResult = 
						new BoardDao().insertNewAttachment(conn, attachment);
			}
		} // 아닐 경우는 할게 없음 else없어도 됨~
		
		// 둘다 성공했을 경우에만 commit
		// 하나라도 실패라면 rollback
		
		if((result * attachmentResult) > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return (result * attachmentResult);
	}

	public int insertThumbnailBoard(Board board, List<Attachment> list) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		// 한 개의 트랜잭션에 최소 2개에서 최대 5개까지의 DML구문이 만들어질 것
		int boardResult = new BoardDao().insertThumbnailBoard(conn, board);
		
		if(!(boardResult > 0)) {
			JDBCTemplate.close(conn);
			return 0;
		} // 0보다 높지 않으면 인서트 안된거라 dao갈 필요가 없으니까
		
		int attachmentResult = new BoardDao().insertAttachmentList(conn, list);
		
		if((boardResult * attachmentResult) > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return (boardResult * attachmentResult);	// 1 or 0
		
	}

	public List<Board> selectList() {
		
		Connection conn = JDBCTemplate.getConnection();
		
		List<Board> list = new BoardDao().selectList(conn);
		
		JDBCTemplate.close(conn);
		
		return list;
	}

	public List<Attachment> selectImageList(int boardNo) {
		
		Connection conn = JDBCTemplate.getConnection();
		List<Attachment> list = new BoardDao().selectImageList(conn, boardNo);
		
		JDBCTemplate.close(conn);
		
		return list;
	}

	public List<Reply> selectReplyList(int boardNo) {
		
		Connection conn = JDBCTemplate.getConnection();
		List<Reply> replyList = new BoardDao().selectReplyList(conn, boardNo);
		
		JDBCTemplate.close(conn);
		
		return replyList;
	}

	public int insertReply(Reply reply) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new BoardDao().insertReply(conn, reply);
		
		if(result > 0) {
			JDBCTemplate.commit(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}
	

	
	
	
	
	

}
