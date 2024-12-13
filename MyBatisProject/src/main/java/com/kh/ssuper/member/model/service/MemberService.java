package com.kh.ssuper.member.model.service;

import com.kh.ssuper.member.model.vo.Member;

public interface MemberService {
	
	// 회원가입
	int insertMember(Member member);
	
	// 로그인
	Member login(Member member);
	

}
