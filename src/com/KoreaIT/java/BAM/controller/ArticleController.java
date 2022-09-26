package com.KoreaIT.java.BAM.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.KoreaIT.java.BAM.dto.Article;
import com.KoreaIT.java.BAM.dto.Member;
import com.KoreaIT.java.BAM.util.Util;

public class ArticleController extends Controller{
	private List<Article> articles;
	private Scanner sc;
	private String cmd;
	// 일단 private 으로 만들고 다른 곳에서 필요하게 된다면 public으로 바꾸는 방향으로 
	
	public ArticleController(Scanner sc) {
		this.articles = new ArrayList<>();
		this.sc = sc;
	}
	
	@Override
	public void doAction(String cmd, String methodName) {
		this.cmd = cmd;
		
		switch(methodName) {
		case "write":
			if(isLogined() == false) {
				System.out.println("로그인 상태가 아닙니다.");
				break; 
			}
			doWrite();
			break;
		case "list":
			showList();
			break;
		case "modify":
			doModify();
			break;
		case "detail":
			showDetail();
			break;
		case "delete":
			doDelete();
			break;
			default:
				System.out.println("존재하지 않는 명령어 입니다.");
				break;
		}
	}
	
	private void doWrite(){
		
		int id = articles.size() + 1;
		String regDate = Util.getNowDateStr();
		System.out.printf("제목 : ");
		String title = sc.nextLine();
		System.out.printf("내용 : ");
		String body = sc.nextLine();

		
		Article article = new Article(id, regDate, loginedMember.id, title, body);
		
		articles.add(article); // write 할때마다 게시글을 하나씩 배열에 저장
		
		System.out.printf("%d번 글이 생성되었습니다 \n", id, title, body);
		
	}
	
	private void showList() {
		System.out.println("== 게시물 리스트 ==");
		
		if (articles.size() == 0) {
			System.out.println("게시물이 없습니다.");
			return; // return 명령어로 함수 종료 . 리턴 안하겠다
		}
		
		List<Article> forPrintArticles = articles; 
		
		String searchKeyword = cmd.substring("article list".length()).trim(); 
		// substring - 앞의 글자수를 제외하고 뒤에 쓰는 글자 index부터 가져옴
		
		
		if(searchKeyword.length() > 0) { // 리스트 검색
			
			System.out.println("검색어 : " + searchKeyword);
			
			forPrintArticles = new ArrayList<>(); // 해당된다면 객체 하나 더 생성
			
			for(Article article : articles) {
				if(article.title.contains(searchKeyword)) {
					forPrintArticles.add(article);
				}
			}
			
			if(forPrintArticles.size() == 0) { // 검색해도 없는 경우
				System.out.println("검색결과가 없습니다.");
				return;
			}
			
		}
		
		System.out.println("번호	|	제목	|	날짜			|	작성자	|	조회수");
		
		for(int i = forPrintArticles.size() - 1; i >= 0; i--) { // 순회는 역순으로
			Article article = forPrintArticles.get(i);
			System.out.printf("%d	|	%s	|	%s	|	%s	|	%d \n", article.id, article.title, article.regDate, article.memberId, article.viewCnt);
		}
		
	}
	
	private void doModify() {
		String[] cmdBits = cmd.split(" ");
		
		if(cmdBits.length == 2) {
			System.out.println("명령어를 확인해주세요");
			return;
		}
		
		int id = Integer.parseInt(cmdBits[2]);
		
		Article foundArticle = getArticleById(id);
		
		for(int i = 0; i < articles.size(); i++) { 
			Article article = articles.get(i);
			
			if(article.id == id) { 
				foundArticle = article; 
				break;
			}
			
		}
		
		if(foundArticle == null) { 
			
			System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
			return;
		}
		
		System.out.printf("수정할 제목 : ");
		String title = sc.nextLine();
		System.out.printf("수정할 내용 : ");
		String body = sc.nextLine();
		
		// 객체 리모컨이 여러개라 바뀌는게 가능
		foundArticle.title = title; // 리모컨이 여러개 가지고 있어서 새로 입력 받은것으로 바뀜
		foundArticle.body = body;
		
		System.out.printf("%d번 글이 수정되었습니다.\n", id, title, body);
		
	}
	
	private void showDetail() {
		String[] cmdBits = cmd.split(" ");
		
		if(cmdBits.length == 2) {
			System.out.println("명령어를 확인해주세요");
			return;
		}
		
		int id = Integer.parseInt(cmdBits[2]);
		
		Article foundArticle = getArticleById(id); // 인자로 넣음.
		
		if(foundArticle == null) {
			// 출력을 담당하는 로직
			System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
			return;
		} 
		
		foundArticle.addViewCnt(); // 조회수
		
			System.out.printf("번호 : %d \n", foundArticle.id);
			System.out.printf("날짜 : %s \n", foundArticle.regDate);
			System.out.printf("작성자 : %s \n", foundArticle.memberId);
			System.out.printf("제목 : %s \n", foundArticle.title);
			System.out.printf("내용 : %s \n", foundArticle.body);
			System.out.printf("조회수 : %s \n", foundArticle.viewCnt);
		
	}
	
	private void doDelete() {
		String[] cmdBits = cmd.split(" ");
		
		if(cmdBits.length == 2) {
			System.out.println("명령어를 확인해주세요");
			return;
		}
		
		int id = Integer.parseInt(cmdBits[2]);
		
		int foundIndex = getArticleIndexById(id);  // index를 사용하기 위해 변수를 하나 만듦 . -1번으로 존재하는 index는 존재하지 않기 때문에 
		
		for(int i = 0; i < articles.size(); i++) { // 게시글 순회
			Article article = articles.get(i);
			
			if(article.id == id) { // 명령어에 입력한 id가 일치한다면
				foundIndex = i;
				break;
			}
		}
		
		if(foundIndex == -1) {
			System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
			return;
		}
		
		articles.remove(foundIndex); // index 번호 삭제
		System.out.printf("%d번 게시물이 삭제되었습니다.\n", id); // 삭제가 된다면 출력
		
	}

	private int getArticleIndexById(int id) {
		int i = 0;
		for(Article article : articles) { 
			
			if(article.id == id) { 
				return i;
			}
			i++;
		}
		return -1;
	}


	private Article getArticleById(int id) { // 더 간결한 for문 - 단순히 도는 for문만 가능

		int index = getArticleIndexById(id);
		
		if(index != -1) {
			return articles.get(index);
		}
		
		return null;
		
	}


	
	public void makeTestData() {
		System.out.println("테스트를 위한 게시물 데이터를 생성합니다.");
		articles.add(new Article(1, Util.getNowDateStr(), 1, "제목1", "내용1", 11));
		articles.add(new Article(2, Util.getNowDateStr(), 2, "제목2", "내용2", 12));
		articles.add(new Article(3, Util.getNowDateStr(), 2, "제목3", "내용3", 13));
		
	}
	
}
