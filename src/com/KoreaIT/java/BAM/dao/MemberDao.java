package com.KoreaIT.java.BAM.dao;

import java.util.ArrayList;
import java.util.List;

import com.KoreaIT.java.BAM.dto.Member;

public class MemberDao extends Dao{
	private List<Member> members;
	
	public MemberDao() {
		members = new ArrayList<>();
	}

	public void add(Member member) {
		members.add(member);
		
		lastId++;
	}
	
	public boolean loginIdChk(String loginId) {
		int index = getMemberIndexByLoginId(loginId); // 먼저 실행 후
		
		if(index == -1) { // 아이디 중복이 없을 경우 true
			return true;
		}
		
		return false;
	}
	
	
	public Member getMemberByLoginId(String loginId) {
		int index = getMemberIndexByLoginId(loginId);
		
		if(index != -1) { // 순회 후 일치하는 회원 발견 
			return members.get(index);
		}
		
		return null;
	}

	public int getMemberIndexByLoginId(String loginId) {
		int i = 0;
		for(Member member : members) { 
			
			if(member.loginId.equals(loginId)) { // 문자열은 equals로 
				return i;
			}
			i++;
		}
		return -1;
	}
	
	public String getWriterName(int memberId) {

		for(Member member : members) {
			if(memberId == member.id) { // member.id 와 article의 memberId가 일치하면 작성자 이름출력
				return member.name;
			}
		}
		return null;
	}
	
}
