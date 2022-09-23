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
		memberController.makeTestData(); // 메서드 실행

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
			String methodName = cmdBits[1]; // list
			
			Controller controller = null;
			
			if(controllerName.equals("article")) { // article 관련은 articleController로
				controller = articleController;
			} else if (controllerName.equals("member")) { // member 관련은 articleController로
				controller = memberController;
			} else {
				System.out.println("존재하지 않는 명령어 입니다");
				continue;
			}
			
			controller.doAction(cmd, methodName);
		
		
		}
		
		System.out.println("== 프로그램 종료 ==");
		
		sc.close();
	}

}
