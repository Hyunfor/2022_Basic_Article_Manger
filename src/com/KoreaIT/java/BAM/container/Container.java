package com.KoreaIT.java.BAM.container;

import com.KoreaIT.java.BAM.dao.ArticleDao;
import com.KoreaIT.java.BAM.dao.MemberDao;
import com.KoreaIT.java.BAM.service.AdminService;
import com.KoreaIT.java.BAM.service.ArticleService;
import com.KoreaIT.java.BAM.service.MemberService;

public class Container{
	public static ArticleDao articleDao;
	public static MemberDao memberDao;
	public static ArticleService articleService;
	public static MemberService memberService;
	public static AdminService adminService;

	
	static {
		
		articleDao = new ArticleDao();
		memberDao = new MemberDao();
		articleService = new ArticleService();
		memberService = new MemberService();

	}
}
