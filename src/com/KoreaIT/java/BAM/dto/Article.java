package com.KoreaIT.java.BAM.dto;

public class Article extends Dto {
	public String title;
	public String body;
	public int viewCnt;
	
	public Article (int id, String regDate, String title, String body){ // 생성자
		this(id, regDate, title, body, 0); // 다른 생성자에게 일을 떠넘김
	}
	
	// 함수명이 똑같지만 매개변수, 인자의 차이로 쓰는것이 오버로딩
	public Article(int id, String regDate, String title, String body, int viewCnt) {
		this.id = id;
		this.regDate = regDate;
		this.title = title;
		this.body = body;
		this.viewCnt = viewCnt;
	}

	public void addViewCnt(){ // 조회수 증가
		viewCnt++;
	}
	
}