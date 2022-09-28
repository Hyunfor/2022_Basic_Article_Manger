package com.KoreaIT.java.BAM.service;

import java.util.ArrayList;
import java.util.List;

import com.KoreaIT.java.BAM.container.Container;
import com.KoreaIT.java.BAM.dto.Article;

public class ArticleService {

	public List<Article> getForPrintArticles(String searchKeyword){
		return Container.articleDao.getForPrintArticles(searchKeyword);
	}
	
}
