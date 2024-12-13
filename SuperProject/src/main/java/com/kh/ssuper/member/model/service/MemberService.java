package com.kh.ssuper.member.model.service;

import static com.kh.ssuper.common.JDBCTemplate.getConnection;
import static com.kh.ssuper.common.JDBCTemplate.registDriver;  // 아래에서 JDBCTemplate 쓰끼 귀차나~~

import java.sql.Connection;

import com.kh.ssuper.common.JDBCTemplate;
import com.kh.ssuper.member.model.dao.MemberDao;
import com.kh.ssuper.member.model.vo.Member;

public class MemberService {

	static {
		registDriver();
	}
	
	public Member login(Member member) {
		
		// 1) Service => Connection객체 생성
		Connection conn = getConnection();
		
		// 2) Controller에서 넘어온 전달값과 Connection객체를 DAO메소드를 호출하면서 전달
		Member m = new MemberDao().login(conn, member);
		
		// 3) Connection 객체 반납
		JDBCTemplate.close(conn);
		
		// 4) Controller(Servlet)에 결과 반환
		return m;
	}

	public int join(Member member) {
		
		Connection conn = getConnection();
		
		int result = new MemberDao().join(conn, member);
		
		if(result > 0) 
			JDBCTemplate.commit(conn);
		// 지금까지는 else로 rollback시켰지만 사실 변경된게 없으면 할 필요는 없어서 생략~
		
		JDBCTemplate.close(conn);
		
		return result;
	}

	public int update(Member member) {
		
		Connection conn = getConnection();

		int result = new MemberDao().update(conn, member);
		
		if(result > 0) 
			JDBCTemplate.commit(conn);
		
		JDBCTemplate.close(conn);
		
		return result;
		
	}

	public int updatePwd(int userNo, String userPwd, String updatePwd) {
		
		Connection conn = getConnection();

		int result = new MemberDao().updatePwd(conn, userNo, userPwd, updatePwd);
		
		if(result > 0) 
			JDBCTemplate.commit(conn);
		
		JDBCTemplate.close(conn);
		
		return result;
		
	}

	public int deleteMember(int userNo, String userPwd) {
		
		Connection conn = getConnection();

		int result = new MemberDao().delete(conn, userNo, userPwd);
		
		if(result > 0) 
			JDBCTemplate.commit(conn);
		
		JDBCTemplate.close(conn);
		
		return result;
	}

	public int checkId(String id) {

		Connection conn = getConnection();

		int count = new MemberDao().checkId(conn, id);
		
		JDBCTemplate.close(conn);
		
		return count;
	}

	
	
	
	
	
	
	
	
	
	
	
}
