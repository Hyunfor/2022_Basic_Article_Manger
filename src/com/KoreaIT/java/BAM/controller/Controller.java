package com.KoreaIT.java.BAM.controller;

import com.KoreaIT.java.BAM.dto.Member;

public abstract class Controller { // 하나라도 추상메서드가 있다면 추상클래스가 되어야함
	
	public static Member loginedMember; // Article과 Member공유재로 만들어두기
	
	public boolean isLogined() {
		
		return loginedMember != null;
	}
	
	public abstract void doAction(String cmd, String methodName); // 추상 메서드
	
	public abstract void makeTestData();
		

}
