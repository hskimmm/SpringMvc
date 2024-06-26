package kr.spring.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.spring.entity.Member;
import kr.spring.mapper.MemberMapper;

@Controller
public class LoginController {
	
	@Autowired
	MemberMapper memberMapper;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	
	/**
	 * @apiNote 로그인 페이지로 이동한다.
	 * @author hskim
	 * @since 2024-06-10
	 * @return
	 */
	@RequestMapping("/memberLoginForm.do")
	public String memberLoginForm() {
		return "login/loginForm";
	}
	
	
	/**
	 * @apiNote 사용자 로그인을 처리한다.
	 * @author hskim
	 * @since 2024-06-10
	 * @param member
	 * @param redirect
	 * @param session
	 * @return
	 * @throws NullPointException
	 */
	@RequestMapping("/memberLogin.do")
	public String memberLogin(Member member, RedirectAttributes redirect, HttpSession session) {
		
		if(member.getMemId() == null || member.getMemId().equals("") ||
			member.getMemPassword() == null || member.getMemPassword().equals("")) {
			
			redirect.addFlashAttribute("messageType", "error");
			redirect.addFlashAttribute("message", "로그인에 실패하였습니다.");
			
			return "redirect:/memberLoginForm.do";
		}
		
		Member loginMember = memberMapper.memberLogin(member);
		
		//사용자가 입력한 비밀번호와 DB의 저장된 비밀번호가 서로 맞는지 확인(matches를 통해 입력한 비밀번호를 암호화 해서 비교)
		if(loginMember != null && passwordEncoder.matches(member.getMemPassword(), loginMember.getMemPassword())) {
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
	
	
	/**
	 * @apiNote 사용자 로그아웃 처리한다.
	 * @author hskim
	 * @since 2024-06-10
	 * @param session
	 * @return
	 */
	@RequestMapping("/memberLogout.do")
	public String memberLogout(HttpSession session) {
		session.invalidate(); //세션 무효화
		return "redirect:/";
	}
}
