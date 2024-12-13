package com.kh.ssuper.board.model.service;

import static com.kh.ssuper.common.Template.getSqlSession;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import com.kh.ssuper.board.model.dao.BoardDao;
import com.kh.ssuper.board.model.vo.Board;
import com.kh.ssuper.board.model.vo.Reply;
import com.kh.ssuper.common.PageInfo;

public class BoardServiceImpl implements BoardService {

	private BoardDao boardDao = new BoardDao();
	
	
	
	@Override
	public int selectListCount() {
		
		SqlSession sqlSession = getSqlSession();
		
		int count = boardDao.selectListCount(sqlSession);
		
		sqlSession.close();
		
		return count;
	}

	@Override
	public List<Board> selectList(PageInfo pi) {
		
		SqlSession sqlSession = getSqlSession();
		
		List<Board> list = boardDao.selectList(sqlSession, pi);
		
		sqlSession.close();
		
		return list;
	}

	@Override
	public int increaseCount(int boardNo) {
		
		SqlSession sqlSession = getSqlSession();
		
		int result = boardDao.increaseCount(sqlSession, boardNo);
		
		if(result > 0) sqlSession.commit();
		
		sqlSession.close();
		
		return result;
	}

	@Override
	public Board selectBoard(int boardNo) {
		
		SqlSession sqlSession = getSqlSession();
		
		Board board = boardDao.selectBoard(sqlSession, boardNo);
		
		sqlSession.close();
		
		return board;
	}

	@Override
	public List<Reply> selectReplyList(int boardNo) {
		
		SqlSession sqlSession = getSqlSession();
		
		List<Reply> list = boardDao.selectReplyList(sqlSession, boardNo);
		
		sqlSession.close();
		
		return list;
	}

	@Override
	public int searchedCount(Map<String, String> map) {
		
		SqlSession sqlSession = getSqlSession();
		
		int result = boardDao.searchedCount(sqlSession, map);
		
		sqlSession.close();
		
		return result;
	}

	@Override
	public List<Board> selectSearchList(PageInfo pi, Map<String, String> map) {
		
		SqlSession session = getSqlSession();
		
		RowBounds rowBounds = new RowBounds((pi.getCurrentPage() - 1) * pi.getBoardLimit(), 
											 pi.getBoardLimit());
		
		List<Board> list = boardDao.selectSearchList(session, map, rowBounds);
		
		session.close();
		
		return list;
	}

}
