package com.kh.ssuper.board.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import com.kh.ssuper.board.model.vo.Board;
import com.kh.ssuper.board.model.vo.Reply;
import com.kh.ssuper.common.PageInfo;

public class BoardDao {
	
	public int selectListCount(SqlSession sqlSession) {
			return sqlSession.selectOne("boardMapper.selectListCount");
	}

	public List<Board> selectList(SqlSession sqlSession, PageInfo pi) {
		
		// 마이바티스에서는 페이징처리를 위해 RowBounds라는 클래스를 제공함
		
		// * offset : 몇 개의 게시글을 건너뛰고 조회할 것인지
		/*
		 * boardLimit이 8일 경우
		 * 			
		 * 								offset(건너뛸 숫자)
		 * currentPage : 1 -> 1 ~ 8			0
		 * currentPage : 3 -> 17 ~ 24		16
		 * 
		 */
		int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		
		return sqlSession.selectList("boardMapper.selectList", null, rowBounds);
		
		// RowBounds객체를 전달해야 할 경유
		// selectList()의 오버로딩된 형태 중 매개변수가 3개인 메소드로 꼭 호출해야함
		// 두번쨰 자리에 넘길 인자가 없다면 null로
	}

	public int increaseCount(SqlSession sqlSession, int boardNo) {
		return sqlSession.update("boardMapper.increaseCount", boardNo);
	}

	public Board selectBoard(SqlSession sqlSession, int boardNo) {
		//return sqlSession.selectOne("boardMapper.selectBoard", boardNo);
		return sqlSession.selectOne("boardMapper.selectBoardAndReply", boardNo);
	}

	public List<Reply> selectReplyList(SqlSession sqlSession, int boardNo) {
		
		return sqlSession.selectList("boardMapper.selectReplyList", boardNo);
	}

	public int searchedCount(SqlSession sqlSession, Map<String, String> map) {
		
		return sqlSession.selectOne("boardMapper.searchedCount", map);
	}

	public List<Board> selectSearchList(SqlSession session, Map<String, String> map, 
										RowBounds rowBounds) {
		return session.selectList("boardMapper.selectSearchList", map, rowBounds);
	}
	
	
	
	
	
	
	
	
	
	
	

}
