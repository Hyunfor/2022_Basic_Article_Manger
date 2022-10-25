package com.KoreaIT.java.BAM.service;

import com.KoreaIT.java.BAM.dto.Admin;
import com.KoreaIT.java.BAM.dto.Member;

public interface AdminService {
		 
		boolean loginCheck(Admin dto, HttpSession session) throws Exception;    //관리자 로그인을 체크하는 메소드
	 
		void admin_member_forced_evictionCheck(Member dto) throws Exception; //강제탈퇴 시킬때 해당 회원이 있는지 체크하는 메소드
		 
	}
