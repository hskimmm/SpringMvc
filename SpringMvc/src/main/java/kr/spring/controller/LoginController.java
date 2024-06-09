package kr.spring.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.spring.entity.Member;
import kr.spring.mapper.MemberMapper;

@Controller
public class LoginController {
	
	@Autowired
	MemberMapper memberMapper;

	//로그인폼 이동
	@RequestMapping("/memberLoginForm.do")
	public String memberLoginForm() {
		return "login/loginForm";
	}
	
	//로그인
	@RequestMapping("/memberLogin.do")
	public String memberLogin(Member member, RedirectAttributes redirect, HttpSession session) {
		
		if(member.getMemId() == null || member.getMemId().equals("") ||
			member.getMemPassword() == null || member.getMemPassword().equals("")) {
			
			redirect.addFlashAttribute("messageType", "error");
			redirect.addFlashAttribute("message", "로그인에 실패하였습니다.");
			
			return "redirect:/memberLoginForm.do";
		}
		
		Member loginMember = memberMapper.memberLogin(member);
		
		if(loginMember != null) {
			redirect.addFlashAttribute("messageType", "success");
			redirect.addFlashAttribute("message", "로그인에 성공하였습니다.");
			
			session.setAttribute("member", loginMember); //로그인 성공시 session에 해당 유저의 정보를 담음
			
			return "redirect:/";
		} else {
			redirect.addFlashAttribute("messageType", "error");
			redirect.addFlashAttribute("message", "로그인에 실패하였습니다.");
			
			return "redirect:/memberLoginForm.do";
		}
	}
	
	//로그아웃
	@RequestMapping("/memberLogout.do")
	public String memberLogout(HttpSession session) {
		session.invalidate(); //세션 무효화
		return "redirect:/";
	}
}
