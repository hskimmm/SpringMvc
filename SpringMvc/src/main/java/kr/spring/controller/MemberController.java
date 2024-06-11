package kr.spring.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.spring.entity.Member;
import kr.spring.mapper.MemberMapper;

@Controller
public class MemberController {
	
	
	@Autowired
	MemberMapper memberMapper;
	
	
	/**
	 * @apiNote 회원가입 페이지로 이동한다.
	 * @author hskim
	 * @since 2024-06-10
	 * @return
	 */
	@RequestMapping("/memJoinForm.do")
	public String memJoin() {
		return "member/memberJoinForm";
	}
	
	
	/**
	 * @apiNote 회원가입
	 * @author hskim
	 * @since 2024-06-10
	 * @param member
	 * @param memPwd
	 * @param memPwdOk
	 * @param redirect
	 * @param session
	 * @return
	 * @throws NullPointException
	 */
	@RequestMapping("/memRegister.do")
	public String memRegister(Member member, String memPwd, String memPwdOk, RedirectAttributes redirect, HttpSession session) {
		//유효성체크
		if(member.getMemId() == null || member.getMemId().equals("") || 
			member.getMemPassword() == null || member.getMemPassword().equals("") ||
			memPwd == null || memPwd.equals("") ||
			memPwdOk == null || memPwdOk.equals("") ||
			member.getMemName() == null || member.getMemName().equals("") ||
			member.getMemAge() == 0 || 
			member.getMemGender() == null || member.getMemGender().equals("") ||
			member.getMemEmail() == null || member.getMemEmail().equals("")) {
			
			//객체바인딩(딱 한번만 객체바인딩)
			redirect.addFlashAttribute("messageType", "error");
			redirect.addFlashAttribute("message", "누락된 정보");
			return "redirect:/memJoinForm.do";
		}
		
		//비밀번호와 비밀번호체크의 값이 서로 다를 경우
		if(!memPwd.equals(memPwdOk)) {
			redirect.addFlashAttribute("messageType", "error");
			redirect.addFlashAttribute("message", "비밀번호가 서로 다릅니다.");
			return "redirect:/memJoinForm.do";
		}
		
		member.setMemProfile(""); // 회원가입시 파일을 넣지 않기 때문에 db에 null을 넣지 않기 위해 임시로 처리
		
		int resultType = memberMapper.memRegister(member);
		if(resultType == 1) {
			redirect.addFlashAttribute("messageType", "success");
			redirect.addFlashAttribute("message", "회원가입에 성공했습니다.");
			
			//회원가입 성공시 바로 로그인 처리
			session.setAttribute("member", member);
			
			//회원가입 성공시 메인페이지로 이동 처리
			return "redirect:/";
		} else {
			redirect.addFlashAttribute("messageType", "error");
			redirect.addFlashAttribute("message", "회원가입에 실패했습니다.");
			return "redirect:/memJoinForm.do";
		}
	}
	
	
	/**
	 * @apiNote DB에 중복된 아이디가 있는지 확인한다.
	 * @author hskim
	 * @since 2024-06-10
	 * @param memberId
	 * @return
	 * @throws NullPointException
	 */
	@RequestMapping("/memberRegisterCheck.do")
	public @ResponseBody int memberRegisterCheck(@RequestParam String memberId) {
		int memeberIdCheck = memberMapper.memberRegisterCheck(memberId);
		
		//아이디 유무 체크
		//0 : 해당 아이디 사용중, 1 : 신규생성가능
		if(memeberIdCheck != 0 || memberId.equals("")) {
			memeberIdCheck = 0;
		} else {
			memeberIdCheck = 1;
		}
		
		return memeberIdCheck;
	}
	
	
	/**
	 * @apiNote 회원정보수정 페이지로 이동한다.
	 * @author hskim
	 * @since 2024-06-11
	 * @param session
	 * @return
	 */
	@RequestMapping("/memberUpdateForm.do")
	public String memberUpdateForm() {
		return "member/memberUpdateForm";
	}
	
	
	/**
	 * @apiNote 회원정보를 수정한다.
	 * @author hskim
	 * @since 2024-06-11
	 * @param memPwd
	 * @param memPwdOk
	 * @param member
	 * @param redirect
	 * @param session
	 * @return
	 * @throws NullPointException
	 */
	@RequestMapping("/memberUpdate.do")
	public String memberUpdate(@RequestParam String memPwd, String memPwdOk, Member member, RedirectAttributes redirect, HttpSession session) {
		
		if(member.getMemId() == null || member.getMemId().equals("") || 
				member.getMemPassword() == null || member.getMemPassword().equals("") ||
				memPwd == null || memPwd.equals("") ||
				memPwdOk == null || memPwdOk.equals("") ||
				member.getMemName() == null || member.getMemName().equals("") ||
				member.getMemAge() == 0 || 
				member.getMemGender() == null || member.getMemGender().equals("") ||
				member.getMemEmail() == null || member.getMemEmail().equals("")) {
				
				//객체바인딩(딱 한번만 객체바인딩)
				redirect.addFlashAttribute("messageType", "error");
				redirect.addFlashAttribute("message", "누락된 정보");
				
				return "redirect:/memberUpdateForm.do";
		}
			
		//비밀번호와 비밀번호체크의 값이 서로 다를 경우
		if(!memPwd.equals(memPwdOk)) {
			redirect.addFlashAttribute("messageType", "error");
			redirect.addFlashAttribute("message", "비밀번호가 서로 다릅니다.");
			
			return "redirect:/memberUpdateForm.do";
		}
		
		int updateType = memberMapper.memberUpdate(member);
		if(updateType == 1) {
			redirect.addFlashAttribute("messageType", "success");
			redirect.addFlashAttribute("message", "회원정보수정에 성공했습니다.");
			
			//회원정보수정시 세션의 수정된 값 다시 넣어준다.
			session.setAttribute("member", member);
			//회원가입 성공시 메인페이지로 이동 처리
			return "redirect:/";
		} else {
			redirect.addFlashAttribute("messageType", "error");
			redirect.addFlashAttribute("message", "회원정보수정에 실패했습니다.");
			
			return "redirect:/memberUpdateForm.do";
		}
		
	}
	
}
