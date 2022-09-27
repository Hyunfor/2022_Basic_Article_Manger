package com.KoreaIT.java.BAM.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.KoreaIT.java.BAM.container.Container;
import com.KoreaIT.java.BAM.dto.Member;
import com.KoreaIT.java.BAM.util.Util;

public class MemberController extends Controller{
	private List<Member> members;
	private Scanner sc;
	
	
	public MemberController(Scanner sc) {
		this.members = Container.memberDao.members;
		this.sc = sc;
	}
	
	@Override
	public void doAction(String cmd, String methodName) {

		switch(methodName) {
		case "join":
			doJoin();
			break;
		case "login":
			doLogin();
			break;
		case "logout":
			doLogout();
			break;
		case "profile":
			showProfile();
			break;
		default:
				System.out.println("존재하지 않는 명령어 입니다.");
				break;

		}
	}
	
	private void doLogin(){
		
		System.out.printf("로그인 아이디 : ");
		String loginId = sc.nextLine();
		System.out.printf("비밀번호 : ");
		String loginPw = sc.nextLine();
		
		// 사용자의 입력 아이디와 일치하는 회원이 우리한테 있는지 확인 
		Member member = getMemberByLoginId(loginId);
		
		if(member == null) { // 일치하는 회원이 없을때
			System.out.println("일치하는 회원이 없습니다.");
			return;
		}
		
		if(member.loginPw.equals(loginPw) == false) { // 입력한 pw가 같은지 확인
			System.out.println("비밀번호를 확인해주세요");
			return;
		}
		
		loginedMember = member; // login정보를 들고 있기.
		System.out.printf("로그인 성공! %s님 환영합니다.\n", loginedMember.name);
		
	}
	
	private void doLogout() {
		
		loginedMember = null; // 로그아웃 상태
		System.out.println("로그아웃 되었습니다.");
		
	}

	private void doJoin(){
		int id = members.size() + 1;
		String regDate = Util.getNowDateStr();
		
		String loginId = null;
		while(true) {
			System.out.printf("로그인 아이디 : ");
			loginId = sc.nextLine();
			
			if(loginIdChk(loginId) == false) { // 
				System.out.printf("%s은(는) 이미 사용중인 아이디입니다.\n", loginId);
				continue;
			}
			System.out.printf("%s은(는) 사용가능한 아이디입니다.\n", loginId);
			break;
		}
		
		
		String loginPw = null;
		String loginPwChk = null;
		while(true) {
			System.out.printf("로그인 비밀번호 : ");
			loginPw = sc.nextLine();
			System.out.printf("비밀번호 확인 : ");
			loginPwChk = sc.nextLine();
			
			if(loginPw.equals(loginPwChk) == false) { 
				// 로그인 비밀번호와 비밀번호 확인이 일치할시 실행, 틀리면 false 실행
				System.out.println("비밀번호를 다시 입력해주세요.");
				continue;
			}
			break; // 비밀번호가 일치할시 반복문 탈출
		}
		
		
		System.out.printf("이름 : ");
		String name = sc.nextLine();

		
		Member member = new Member(id, regDate, loginId, loginPw, name);
		
		members.add(member); // join 할때마다 게시글을 하나씩 배열에 저장
		
		System.out.printf("%s 회원님 환영합니다 \n", loginId);
		}
	
	private void showProfile() {
		
		System.out.println("== 내 정보 ==");
		System.out.printf("로그인 아이디 : %s\n", loginedMember.loginId);
		System.out.printf("이름 : %s\n", loginedMember.name);
		
	}
	
	private boolean loginIdChk(String loginId) {
		int index = getMemberIndexByLoginId(loginId); // 먼저 실행 후
		
		if(index == -1) { // 아이디 중복이 없을 경우 true
			return true;
		}
		
		return false;
	}
	
	
	private Member getMemberByLoginId(String loginId) {
		int index = getMemberIndexByLoginId(loginId);
		
		if(index != -1) { // 순회 후 일치하는 회원 발견 
			return members.get(index);
		}
		
		return null;
	}

	private int getMemberIndexByLoginId(String loginId) {
		int i = 0;
		for(Member member : members) { 
			
			if(member.loginId.equals(loginId)) { // 문자열은 equals로 
				return i;
			}
			i++;
		}
		return -1;
	}
	
	public void makeTestData() {
		System.out.println("테스트를 위한 회원 데이터를 생성합니다.");
		members.add(new Member(1, Util.getNowDateStr(), "test1", "test1", "미국"));
		members.add(new Member(2, Util.getNowDateStr() , "test2", "test2", "러시아"));
		members.add(new Member(3, Util.getNowDateStr() , "test3", "test3", "중국"));
		
	}
	
}


