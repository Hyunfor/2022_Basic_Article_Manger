package com.KoreaIT.java.BAM;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.KoreaIT.java.BAM.controller.ArticleController;
import com.KoreaIT.java.BAM.controller.Controller;
import com.KoreaIT.java.BAM.controller.MemberController;
import com.KoreaIT.java.BAM.dto.Article;
import com.KoreaIT.java.BAM.dto.Member;
import com.KoreaIT.java.BAM.util.Util;

public class App {
	
	public void run() {

		System.out.println("== 프로그램 시작 ==");
		
		Scanner sc = new Scanner(System.in);
		
		MemberController memberController = new MemberController(sc);
		ArticleController articleController = new ArticleController(sc);
		
		articleController.makeTestData(); // 메서드 실행
		memberController.makeTestData(); 

		while(true) {
			System.out.printf("명령어 ) ");
			String cmd = sc.nextLine().trim(); // trim() 공백 제거 
			
			if(cmd.length() == 0 ) {
				System.out.println("명령어를 입력해주세요.");
				continue; 
			}
			
			if(cmd.equals("exit")) {
				break;
			}
			
			String[] cmdBits = cmd.split(" "); // article list
			
			if(cmdBits.length == 1) {
				System.out.println("명령어를 확인해주세요.");
				continue;
			}
			
			String controllerName = cmdBits[0];  // article 
			String methodName = cmdBits[1]; // write
			
			Controller controller = null;
			
			if(controllerName.equals("article")) { // article 관련은 articleController로
				controller = articleController;
			} else if (controllerName.equals("member")) { // member 관련은 articleController로
				controller = memberController;
			} else {
				System.out.println("존재하지 않는 명령어 입니다");
				continue;
			} 
			
			// 로그인이 필요한 경우 - 각controller에서 app으로 기능 이전
//			String actionName = controllerName + "/" + methodName; // article , write 로 나눈 것을 / 로 다시 하나로 합침.
			
			switch(methodName) {
				case "write" :
				case "modify" : // switch case 문법 - 붙여두면 해당 되는 것에 실행
				case "delete" :
				case "logout" :
				case "profile" :
				case "update" :
					if(Controller.isLogined() == false) {
						System.out.println("로그인 후 이용해주세요.");
						continue;
					}
					break;
				case "login" :
				case "join" :
					if(Controller.isLogined()) {
						System.out.println("로그아웃 후 이용해주세요.");
						continue;
					}
					break;
					
					// 댓글 기능처럼 구분이 필요한 경우에는 나눠야함. 
//				switch(actionName) {
//					case "article/write" :
//					case "article/modify" : // switch case 문법 - 붙여두면 해당 되는 것에 실행
//					case "article/delete" :
//					case "member/logout" :
//					case "member/profile" :
//						if(Controller.isLogined() == false) {
//							System.out.println("로그인 후 이용해주세요.");
//							continue;
//						}
//						break;
//					case "member/login" :
//					case "member/join" :
//						if(Controller.isLogined() == false) {
//							System.out.println("로그아웃 후 이용해주세요.");
//							continue;
//						}
//						break;		
					
			}
			
			
			
			controller.doAction(cmd, methodName);
		
		
		}
		
		System.out.println("== 프로그램 종료 ==");
		
		sc.close();
	}



}
