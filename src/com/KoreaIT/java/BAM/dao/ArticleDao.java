package com.KoreaIT.java.BAM.dao;

import java.util.ArrayList;
import java.util.List;

import com.KoreaIT.java.BAM.dto.Article;

public class ArticleDao extends Dao{
	private List<Article> articles;
	
	public ArticleDao() {
		articles = new ArrayList<>();
	}

	public void add(Article article) {
		articles.add(article);
		lastId++; // n번째 write시에 1씩 증가
	}

	public void remove(Article foundArticle) {
		articles.remove(foundArticle);
	}
	
	public List<Article> getForPrintArticles(String searchKeyword) {
		
//		if(searchKeyword.length() > 0) { // 리스트 검색
		
		if(searchKeyword != null) { // 검색 후 null이 아닐때 
		
			System.out.println("검색어 : " + searchKeyword);	
			
			List<Article> forPrintArticles = new ArrayList<>(); // 해당된다면 객체 하나 더 생성
		
			for(Article article : articles) {
				if(article.title.contains(searchKeyword)) {
					forPrintArticles.add(article);
					}
				}
				return forPrintArticles;
			}
			return articles;
	}
	
	public int getArticleIndexById(int id) {
		int i = 0;
		for(Article article : articles) { 
			
			if(article.id == id) { 
				return i;
			}
			i++;
		}
		return -1;
	}


	public Article getArticleById(int id) { // 더 간결한 for문 - 단순히 도는 for문만 가능

		int index = getArticleIndexById(id);
		
		if(index != -1) {
			return articles.get(index);
		}
		
		return null;
		
	}
	
}


