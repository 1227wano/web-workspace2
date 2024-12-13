package com.kh.ssuper.common;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.oreilly.servlet.multipart.FileRenamePolicy;

// FileRenamePolicy라는 인터페이스를 구현해서
// 이름바꾸기 정책을 사용

public class MyRenamePolicy implements FileRenamePolicy {
	
	// FileRenamePolicy 인터페이스가 가지고 있는 rename 추상메소드가 존재!
	// rename메소드를 오버라이딩해서 기존파일명을 전달받아서 파일명을 수정한 뒤
	// 수정한 파일을 반환해줄 것
	
	@Override
	public File rename(File originFile) {
		// "aaa.jpg"
		// "bono.jpg"
		// "abc.jpg" 등등
		
		// 원본 파일명 아는법
		String originName = originFile.getName();
		
		// 우리 만의 이름 규칙 => 최대한 이름이 안겹치도록!
		// KH_Bclass_super_ 년월일시분초_랜덤값 + 원본파일 확장자
		
		/*
		 * 원본명					바꾸기
		 * 
		 * bono.jpg			=>  KH_Bclass_super_2024120314033399999.jpg
		 */
		
		// 1. 원본파일의 확장자명 찾기
		String ext = originName.substring(originName.lastIndexOf("."));
		
		// 2. 랜덤값 1000 ~ 9999
		int randomNo = (int)(Math.random() * 9000) + 1000;	// 1000이 시작값 9000이 범위!
		
		// 3. 년월일시분초
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmss")
								.format(new Date());
		
		// 1 + 2 + 3 조합해서 수정파일명을 변수에 대입하기
		String changeName = "KH_Bclass_super_" + currentTime 
						  + "_" + randomNo + ext;
		
		// 기존 파일명을 수정된 파일명으로 적용시켜서 반환
		return new File(originFile.getParent(), changeName);
	}
	
	
	
	

}
