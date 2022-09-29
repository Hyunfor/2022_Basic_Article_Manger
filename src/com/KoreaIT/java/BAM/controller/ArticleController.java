package com.KoreaIT.java.BAM.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.KoreaIT.java.BAM.container.Container;
import com.KoreaIT.java.BAM.dto.Article;
import com.KoreaIT.java.BAM.dto.Member;
import com.KoreaIT.java.BAM.service.ArticleService;
import com.KoreaIT.java.BAM.service.MemberService;
import com.KoreaIT.java.BAM.util.Util;

public class ArticleController extends Controller{
	private Scanner sc;
	private String cmd;
	// 일단 private 으로 만들고 다른 곳에서 필요하게 된다면 public으로 바꾸는 방향으로 
	private ArticleService articleService;
	private MemberService memberService;

	public ArticleController(Scanner sc) {
		this.articleService = Container.articleService;
		this.memberService = Container.memberService;
		this.sc = sc;
	}
	
	@Override
	public void doAction(String cmd, String methodName) {
		this.cmd = cmd;
		
		switch(methodName) {
		case "write":
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
		
		int id = Container.articleService.setArticleId();
//		int id = articles.size() + 1;	
		String regDate = Util.getNowDateStr();
		System.out.printf("제목 : ");
		String title = sc.nextLine();
		System.out.printf("내용 : ");
		String body = sc.nextLine();
			

		Article article = new Article(id, regDate, loginedMember.id, title, body);
		
		articleService.add(article); // 직접 add 하던 일을 Dao로 빼서 이용
//		articles.add(article); // write 할때마다 게시글을 하나씩 배열에 저장
		
		System.out.printf("%d번 글이 생성되었습니다 \n", id, title, body);
		
	}
	
	private void showList() {
		System.out.println("== 게시물 리스트 ==");
		
		String searchKeyword = cmd.substring("article list".length()).trim();
		// substring - 앞의 글자수를 제외하고 뒤에 쓰는 글자 index부터 가져옴
		
		List<Article> forPrintArticles = articleService.getForPrintArticles(searchKeyword); 		

		if(forPrintArticles.size() == 0) { // 검색해도 없는 경우
			System.out.println("검색결과가 없습니다.");
			return;
		}
		
		System.out.println("번호	|	제목	|	날짜			|	작성자	|	조회수");
		
		for(int i = forPrintArticles.size() - 1; i >= 0; i--) { // 순회는 역순으로
			Article article = forPrintArticles.get(i);		
			
			String writerName = memberService.getWriterName(article.memberId);
			
			System.out.printf("%d	|	%s	|	%s	|	%s	|	%d \n", article.id, article.title, article.regDate, writerName, article.viewCnt);
		}
		
	}
	
	private void doModify() {
		String[] cmdBits = cmd.split(" ");
		
		if(cmdBits.length == 2) {
			System.out.println("명령어를 확인해주세요");
			return;
		}
		
		int id = Integer.parseInt(cmdBits[2]);
		
		Article foundArticle = articleService.getArticleById(id);
		
		if(foundArticle == null) { 
			
			System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
			return;
		}
		
		if(foundArticle.memberId != loginedMember.id) { // 게시글 작성자 확인 로직
			System.out.println("권한이 없습니다.");
			return;
		}
		
		System.out.printf("수정할 제목 : ");
		String title = sc.nextLine();
		System.out.printf("수정할 내용 : ");
		String body = sc.nextLine();
		
		// 객체 리모컨이 여러개라 바뀌는게 가능
		foundArticle.title = title; // 리모컨이 여러개 가지고 있어서 새로 입력 받은것으로 바뀜
		foundArticle.body = body;
		
		System.out.printf("%d번 글이 수정되었습니다.\n", id);
	}
	
	private void showDetail() {
		String[] cmdBits = cmd.split(" ");
		
		if(cmdBits.length == 2) {
			System.out.println("명령어를 확인해주세요");
			return;
		}
		
		int id = Integer.parseInt(cmdBits[2]);
		
		Article foundArticle = articleService.getArticleById(id); // 인자로 넣음.
		
		if(foundArticle == null) {
			// 출력을 담당하는 로직
			System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
			return;
		} 
		
		String writerName = memberService.getWriterName(foundArticle.memberId);
		
		foundArticle.addViewCnt(); // 조회수
		
			System.out.printf("번호 : %d \n", foundArticle.id);
			System.out.printf("날짜 : %s \n", foundArticle.regDate);
			System.out.printf("작성자 : %s \n", writerName); // PK
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
		
		Article foundArticle = articleService.getArticleById(id);
		
		if(foundArticle == null) {
			System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
			return;
		}
		
		if(foundArticle.memberId != loginedMember.id) { // 게시글 작성자 확인 로직
			System.out.println("권한이 없습니다.");
			return;
		}
		
		articleService.remove(foundArticle); // remove가 알아서 해당 객체를 지워줌
		System.out.printf("%d번 게시물이 삭제되었습니다.\n", id); // 삭제가 된다면 출력
		
	}



	
	public void makeTestData() {
		System.out.println("테스트를 위한 게시물 데이터를 생성합니다.");
		articleService.add(new Article(Container.articleService.setArticleId(), Util.getNowDateStr(), 1, "제목1", "내용1", 11));
		articleService.add(new Article(Container.articleService.setArticleId(), Util.getNowDateStr(), 2, "제목2", "내용2", 12));
		articleService.add(new Article(Container.articleService.setArticleId(), Util.getNowDateStr(), 2, "제목3", "내용3", 13));
		
	}
	
}
