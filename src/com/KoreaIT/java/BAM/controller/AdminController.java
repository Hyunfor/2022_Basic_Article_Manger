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
	
	public AdminController(Scanner sc) {
		this.adminService = Container.adminService;
		this.sc = sc;
	}
//	
//	public void masterAdmin(String cmd, String methodName) {
//		this.cmd = cmd;
//		
//		switch(methodName) {
//		case "Alogin":
//			doMlogin();
//			break;
//		case "Alogout":
//			doMlogout();
//			break;
//		default:
//			System.out.println("존재하지 않는 명령어 입니다.");
//			break;
//		}
//	}

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
                
                //로그인이 실패했을 시에 다시 관리자 로그인 페이지로 이동함
                
                mav.setViewName("admin/admin_login");
                
                //뷰에 전달할 값
                
                mav.addObject("message", "관리자의 아이디 혹은 비밀번호가 일치하지 않습니다.");
            
            }
        
                return;
        }
		
	
	private void doMlogout() {
		// TODO Auto-generated method stub
		
	}

	
}
