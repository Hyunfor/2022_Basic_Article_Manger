package com.KoreaIT.java.BAM.dto;

public class Member extends Dto{
	public String loginId;
	public String loginPw;
	public String name;
	
//	public Member (int id, String regDate, String loginId, String loginPw, String loginPwChk, String name){ // 생성자
//		this(id, regDate, loginId, loginPw, loginPwChk, name); // 다른 생성자에게 일을 떠넘김
//	}
	
	// 함수명이 똑같지만 매개변수, 인자의 차이로 쓰는것이 오버로딩
	public Member(int id, String regDate, String loginId, String loginPw, String name) {
		this.id = id;
		this.regDate = regDate;
		this.loginId = loginId;
		this.loginPw = loginPw;
		this.name = name;
	}
}
