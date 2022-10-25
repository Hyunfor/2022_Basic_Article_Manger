package com.KoreaIT.java.BAM.controller;

import java.util.Scanner;

import com.KoreaIT.java.BAM.container.Container;
import com.KoreaIT.java.BAM.dto.Admin;
import com.KoreaIT.java.BAM.modelandview.ModelAndView;
import com.KoreaIT.java.BAM.service.AdminService;

public class AdminController {
	private Scanner sc;
	private AdminService adminService;
	private String cmd;
	
//	  view
//	  ㄴadmin_login.jsp (관리자 로그인 관련)
//	  ㄴadmin_member_forced_eviction_view.jsp (회원 강제 탈퇴 기능 관련)
//
//	  Controller
//	  ㄴAdminController.java (관리자 로그인 및 기능 관련 컨트롤러)
//
//	  Service
//	  ㄴAdminService.java (관리자 관련 서비스 인터페이스)
//	  ㄴAdminServiceImpl.java (관리자 관련 서비스 구현 클래스)
//
//	  model
//	  ㄴAdminDAO.java (관리자 관련 DAO 인터페이스)
//	  ㄴAdminDAOImpl.java (관리자 관련 DAO 구현 클래스)
//	  ㄴAdminDTO (계층별 자료 전송)
	
	public String admin_login_view() {
        
        return "admin/admin_login";
    }
	
	public AdminController(Scanner sc) {
		this.adminService = Container.adminService;
		this.sc = sc;
	}

	private AdminController(String admin_id, String admin_pw) {
		
		//로그인 체크도 같이 함
        //dto에 값들을 넣기 위해 객체를 생성.
		Admin admindto = new Admin();
		
		admindto.setAdmin_id(admin_id);
		admindto.setAdmin_pw(admin_pw);
		
		Object dto;
		Object Session;
		boolean result = adminService.login(dto, Session);
		ModelAndView mav = new ModelAndView();
		

        if(result)    {//로그인이 성공했을시 출력되는 구문
            mav.setViewName("home");    //로그인이 성공했을시 이동하게되는 뷰의 이름
            mav.addObject("admin_id", Session.getAttribute(admin_id));
            
            }else if(Session.getAttribute(admin_id) == null) {    //로그인 실패 했을시 출력
                
                //뷰에 전달할 값
                
                mav.addObject("message", "관리자의 아이디 혹은 비밀번호가 일치하지 않습니다.");
            
            }
        
                return mav;
        }
		
	
	private void doMlogout() {
		loginedMember = null; // 로그아웃 상태
		System.out.println("로그아웃 되었습니다.");
		
	}

	
}
