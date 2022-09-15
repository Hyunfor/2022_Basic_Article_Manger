package com.KoreaIT.java.BAM;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		System.out.println("== 프로그램 시작 ==");
		
		Scanner sc = new Scanner(System.in);
		
		int lastArticleId = 0;

		List<Article> articles = new ArrayList<>();
		
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
			
			if (cmd.equals("article write")) {
				int id = lastArticleId + 1;
				lastArticleId = id;
				System.out.printf("제목 : ");
				String title = sc.nextLine();
				System.out.printf("내용 : ");
				String body = sc.nextLine();
				
				Article article = new Article(id, title, body);
				
				articles.add(article); // write 할때마다 게시글을 하나씩 배열에 저장
				
				System.out.printf("%d번 글이 생성되었습니다 \n", id, title, body);
				
			} else if (cmd.equals("article list")) {
				System.out.println("== 게시물 리스트 ==");
				
				if (articles.size() == 0) {
					System.out.println("게시물이 없습니다.");
				}
				
				System.out.println("번호	|	제목");
				
				for(int i = articles.size() - 1; i >= 0; i--) { // 순회는 역순으로
					Article article = articles.get(i);
					System.out.printf("%d	|	%s \n", article.id, article.title);
				}
				
				
				
			} else {
				System.out.println("존재하지 않는 명령어 입니다.");
			}
			
		}
		
		System.out.println("== 프로그램 종료 ==");
		
		sc.close();
	}

}

class Article {
	int id;
	String title;
	String body;
	
	Article (int id, String title, String body){
		this.id = id;
		this.title = title;
		this.body = body;
	}

	
	
}
