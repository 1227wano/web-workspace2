package com.kh.ssuper.member.model.service;

import org.apache.ibatis.session.SqlSession;

import com.kh.ssuper.common.Template;
import com.kh.ssuper.member.model.dao.MemberDao;
import com.kh.ssuper.member.model.vo.Member;

public class MemberServiceImpl implements MemberService {

	private MemberDao memberDao = new MemberDao();
	
	@Override
	public int insertMember(Member member) {
		/*
		 * Conncetion conn = JDBCTemplate.getConnection();
		 * int result = new MemberDao().insertMember(conn, member);
		 * if(result >  0) {
		 * 		JDBCTemplate.commit(conn);
		 * } 
		 * JDBCTemplate.close(conn);
		 * return result;
		 */
		SqlSession sqlSession = Template.getSqlSession();
		
		int result = memberDao.insertMember(sqlSession, member);
		
		if(result > 0) sqlSession.commit();
		
		sqlSession.close();
		
		return result;
	}

	@Override
	public Member login(Member member) {
		
		SqlSession sqlSession = Template.getSqlSession();
		
		Member loginUser = memberDao.login(sqlSession, member);
		System.out.println(loginUser);
		
		sqlSession.close();
		
		return loginUser;
	}

}
