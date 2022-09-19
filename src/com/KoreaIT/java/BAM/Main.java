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
				String regDate = Util.getNowDateStr();
				
				System.out.printf("제목 : ");
				String title = sc.nextLine();
				System.out.printf("내용 : ");
				String body = sc.nextLine();

				
				Article article = new Article(id, regDate, title, body);
				
				articles.add(article); // write 할때마다 게시글을 하나씩 배열에 저장
				
				System.out.printf("%d번 글이 생성되었습니다 \n", id, title, body);
				
			} else if (cmd.equals("article list")) {
				System.out.println("== 게시물 리스트 ==");
				
				if (articles.size() == 0) {
					System.out.println("게시물이 없습니다.");
				}
				
				System.out.println("번호	|	제목	|	날짜");
				
				for(int i = articles.size() - 1; i >= 0; i--) { // 순회는 역순으로
					Article article = articles.get(i);
					System.out.printf("%d	|	%s	|	%s \n", article.id, article.title, article.regDate);
				}
				
			} else if (cmd.startsWith("article modify ")) {
				String[] cmdBits = cmd.split(" ");
				int id = Integer.parseInt(cmdBits[2]);
				
				Article foundArticle = null;
				
				for(int i = 0; i < articles.size(); i++) { 
					Article article = articles.get(i);
					
					if(article.id == id) { 
						foundArticle = article; 
						break;
					}
					
				}
				
				if(foundArticle == null) { 
					
					System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
					continue;
				}
				
				System.out.printf("수정할 제목 : ");
				String title = sc.nextLine();
				System.out.printf("수정할 내용 : ");
				String body = sc.nextLine();
				
				// 객체 리모컨이 여러개라 바뀌는게 가능
				foundArticle.title = title; // 리모컨이 여러개 가지고 있어서 새로 입력 받은것으로 바뀜
				foundArticle.body = body;
				
				System.out.printf("%d번 글이 수정되었습니다.\n", id, title, body);
				
			}  else if (cmd.startsWith("article detail ")) {
				String[] cmdBits = cmd.split(" ");
				int id = Integer.parseInt(cmdBits[2]);
				
				Article foundArticle = null;
				
				for(int i = 0; i < articles.size(); i++) { // 게시글 순회
					Article article = articles.get(i);
					
					if(article.id == id) { // 명령어에 입력한 id가 일치한다면
						// 데이터를 순회하면서 뽑아내는 기능만 하는 로직
						foundArticle = article; 
						System.out.printf("%d번 게시글은 존재합니다.\n", id);
						break;
					}
				}
				
				if(foundArticle == null) { // foundArticle이 있으니 found는 없어도 됨.
					// 출력을 담당하는 로직
					System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
					continue;
				} 
				
					System.out.printf("번호 : %d \n", foundArticle.id);
					System.out.printf("날짜 : %s \n", foundArticle.regDate);
					System.out.printf("제목 : %s \n", foundArticle.title);
					System.out.printf("내용 : %s \n", foundArticle.body);
				
			} else if (cmd.startsWith("article delete ")) {
				String[] cmdBits = cmd.split(" ");
				int id = Integer.parseInt(cmdBits[2]);
				
				int foundIndex = -1;  // index를 사용하기 위해 변수를 하나 만듦 . -1번으로 존재하는 index는 존재하지 않기 때문에 
				
				for(int i = 0; i < articles.size(); i++) { // 게시글 순회
					Article article = articles.get(i);
					
					if(article.id == id) { // 명령어에 입력한 id가 일치한다면
						foundIndex = i;
						break;
					}
				}
				
				if(foundIndex == -1) {
					System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
					continue;
				}
				
				articles.remove(foundIndex); // index 번호 삭제
				System.out.printf("%d번 게시물이 삭제되었습니다.\n", id); // 삭제가 된다면 출력
				
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
	String regDate;
	String title;
	String body;
	
	Article (int id, String regDate, String title, String body){
		this.id = id;
		this.regDate = regDate;
		this.title = title;
		this.body = body;
	}
	
}
