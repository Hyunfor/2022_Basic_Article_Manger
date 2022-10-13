package com.KoreaIT.java.BAM.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.KoreaIT.java.BAM.container.Container;
import com.KoreaIT.java.BAM.dto.Article;
import com.KoreaIT.java.BAM.dto.Member;
import com.KoreaIT.java.BAM.service.MemberService;
import com.KoreaIT.java.BAM.util.Util;

public class MemberController extends Controller{
	private Scanner sc;
	private MemberService memberService;
	private String cmd;
	
	public MemberController(Scanner sc) {
		this.memberService = Container.memberService;
		this.sc = sc;
	}
	
	@Override
	public void doAction(String cmd, String methodName) {
		this.cmd = cmd;
		
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
		case "modify":	
			doUpdate();
			break;
		case "profile":
			showProfile();
			break;
		case "list":
			showList();
			break;
		default:
				System.out.println("존재하지 않는 명령어 입니다.");
				break;

		}
	}
	
	private void doLogin(){
		
		Member member = null;
		String loginPw = null;
		while(true){
			System.out.printf("로그인 아이디 : ");
			String loginId = sc.nextLine(); 
			
			if(loginId.trim().length() == 0) { // 아이디의 길이가 0이라면 - 입력하지 않은 경우
				// trim()으로 공백 체크
				System.out.println("로그인 아이디를 입력해주세요.");
				continue;
			}
			
			while(true) {
				System.out.printf("로그인 비밀번호 : ");
				loginPw = sc.nextLine();
				
				
				if(loginPw.trim().length() == 0) {
					System.out.println("로그인 비밀번호를 입력해주세요.");
					continue;
				}
				break;
			}
			
			
			// 사용자의 입력 아이디와 일치하는 회원이 우리한테 있는지 확인 
			member = memberService.getMemberByLoginId(loginId);
				
			if(member == null) { // 일치하는 회원이 없을때
					System.out.println("일치하는 회원이 없습니다.");
					return;
				}
				
			if(member.loginPw.equals(loginPw) == false) { // 입력한 pw가 같은지 확인
					System.out.println("비밀번호가 일치하지 않습니다.");
					return;
				}
			
			break;
		}
		
		loginedMember = member; // login정보를 들고 있기.
		System.out.printf("로그인 성공! %s님 환영합니다.\n", loginedMember.name);
		
		
		
	}
	
	private void doLogout() {
		
		loginedMember = null; // 로그아웃 상태
		System.out.println("로그아웃 되었습니다.");
		
	}
	
	private void doUpdate() { // 회원정보 수정
		String[] cmdBits = cmd.split(" ");
		
		if(cmdBits.length == 2) {
			System.out.println("명령어를 확인해주세요");
			return;
		}
		
		int id = Integer.parseInt(cmdBits[2]);
		
		Member foundMember = memberService.getMemberById(id);
		
		if(foundMember == null) { 
			System.out.printf("%d번 회원은 존재하지 않습니다.\n", id);
			return;
		}
		
		String loginPw = null;
		String loginPwChk = null;
		while(true) {
			System.out.printf("새로운 비밀번호 : ");
			loginPw = sc.nextLine();
			System.out.printf("비밀번호 확인 : ");
			loginPwChk = sc.nextLine();
			
			if(loginPw.equals(loginPwChk) == false) { 
				System.out.println("비밀번호를 다시 입력해주세요.");
				continue;
			}
			break; 
		}
		System.out.printf("새로운 이름 : ");
		String name = sc.nextLine();
		
		// 객체 리모컨이 여러개라 바뀌는게 가능
		foundMember.loginPw = loginPw; // 리모컨이 여러개 가지고 있어서 새로 입력 받은것으로 바뀜
		foundMember.name = name;
		
		System.out.printf("%s 회원님 정보가 수정되었습니다.\n", id);
		
	}


	private void doJoin(){
		int id = memberService.setArticleId();
//		int id = members.size() + 1;
		String regDate = Util.getNowDateStr();
		
		String loginId = null;
		while(true) {
			System.out.printf("로그인 아이디 : ");
			loginId = sc.nextLine();
			
			if(memberService.loginIdChk(loginId) == false) { // 
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
		
		memberService.add(member); // Dao에서 add 하도록 넘김.
//		members.add(member); // join 할때마다 게시글을 하나씩 배열에 저장
		
		System.out.printf("%s 회원님 환영합니다 \n", loginId);
		}
	
	private void showProfile() {
		
		System.out.println("== 내 정보 ==");
		System.out.printf("로그인 아이디 : %s\n", loginedMember.loginId);
		System.out.printf("이름 : %s\n", loginedMember.name);
		
	}
	
	private void showList() {
		System.out.println("== 활동 중인 회원 리스트 ==");
		
		String searchKeyword = cmd.substring("member list".length()).trim();
		// substring - 앞의 글자수를 제외하고 뒤에 쓰는 글자 index부터 가져옴
		
		List<Member> forPrintMembers = memberService.getForPrintMembers(searchKeyword); 		

		if(forPrintMembers.size() == 0) { // 검색해도 없는 경우
			System.out.println("검색결과가 없습니다.");
			return;
		}
		
		System.out.println("번호	|	아이디	|	작성자	");
		
		for(int i = forPrintMembers.size() - 1; i >= 0; i--) { // 순회는 역순으로
			Member member = forPrintMembers.get(i);		
			
			String writerName = memberService.getWriterName(member.memberId);
			
			System.out.printf("%d	|	%s	|	%s	\n", member.id, member.loginId, member.name);
		}
		
	}
		
	
	
	public void makeTestData() {
		System.out.println("테스트를 위한 회원 데이터를 생성합니다.");
		memberService.add(new Member(memberService.setArticleId(), Util.getNowDateStr(), "test1", "test1", "미국"));
		memberService.add(new Member(memberService.setArticleId(), Util.getNowDateStr() , "test2", "test2", "러시아"));
		memberService.add(new Member(memberService.setArticleId(), Util.getNowDateStr() , "test3", "test3", "중국"));
		
	}
	
}


