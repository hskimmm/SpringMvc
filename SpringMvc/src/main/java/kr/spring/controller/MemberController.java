package kr.spring.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import kr.spring.entity.Member;
import kr.spring.entity.MemberAuth;
import kr.spring.mapper.MemberMapper;

@Controller
public class MemberController {
	
	
	@Autowired
	MemberMapper memberMapper;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	
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
			member.getMemAge() == 0 || member.getMemberAuthList().size() == 0 ||
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
		
		//비밀번호 암호화해서 member 객체에 넣음
		String encyPassWord = passwordEncoder.encode(member.getMemPassword());
		member.setMemPassword(encyPassWord);
		
		int resultType = memberMapper.memRegister(member);
		if(resultType == 1) {
			//권한 테이블에 회원 권한 추가
			List<MemberAuth> list = member.getMemberAuthList();
			
			for(MemberAuth memberAuth : list) {
				if(memberAuth.getAuth() != null) {
					MemberAuth saveAuth = new MemberAuth();
					saveAuth.setMemId(member.getMemId());
					saveAuth.setAuth(memberAuth.getAuth());
					
					memberMapper.memberAuthInsert(saveAuth);
				}
			}
			
			redirect.addFlashAttribute("messageType", "success");
			redirect.addFlashAttribute("message", "회원가입에 성공했습니다.");
			
			//회원가입 성공시 세션 값 재설정
			Member user = memberMapper.getMember(member.getMemId());
			session.setAttribute("member", user);
			
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
				member.getMemAge() == 0 || member.getMemberAuthList().size() == 0 ||
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
		
		//사용자가 입력한 비밀번호를 암호화해서 다시 넣어줌
		member.setMemPassword(passwordEncoder.encode(member.getMemPassword())); 
		
		int updateType = memberMapper.memberUpdate(member);
		
		if(updateType == 1) {
			//기존 권한 삭제
			memberMapper.memberAuthDelete(member.getMemId());
			
			//새로운 권한 추가
			List<MemberAuth> list = member.getMemberAuthList();
			for(MemberAuth memberAuth : list) {
				if(memberAuth.getAuth() != null) {
					MemberAuth saveMemberAuth = new MemberAuth();
					saveMemberAuth.setMemId(member.getMemId());
					saveMemberAuth.setAuth(memberAuth.getAuth());
					memberMapper.memberAuthInsert(saveMemberAuth);
				}
			}
			
			
			redirect.addFlashAttribute("messageType", "success");
			redirect.addFlashAttribute("message", "회원정보수정에 성공했습니다.");
			
			//회원정보수정시 세션의 수정된 값 다시 넣어준다.(수정 후의 변경 된 유저의 정보를 가져와서 세션에 넣어줌)
			Member user = memberMapper.getMember(member.getMemId());
			session.setAttribute("member", user);
			
			//회원가입 성공시 메인페이지로 이동 처리
			return "redirect:/";
		} else {
			redirect.addFlashAttribute("messageType", "error");
			redirect.addFlashAttribute("message", "회원정보수정에 실패했습니다.");
			
			return "redirect:/memberUpdateForm.do";
		}
		
	}
	
	
	/**
	 * @apiNote 프로필사진등록 페이지로 이동한다.
	 * @author hskim
	 * @since 2024-06-11
	 * @return
	 */
	@RequestMapping("/memberProfileForm.do")
	public String memberProfileForm(){
		return "member/memberProfileForm";
	}
	
	
	/**
	 * @apiNote 프로필 사진을 변경한다.
	 * @author hskim
	 * @since 2024-06-11
	 * @param request
	 * @param redirect
	 * @param session
	 * @return
	 */
	@RequestMapping("/memberProfileUpdate.do")
	public String memberProfileUpdate(HttpServletRequest request, RedirectAttributes redirect, HttpSession session) {
		//2가지 실행 = upload 폴더에 이미지 저장/DB에 저장
		//파일업로드 API
		MultipartRequest multipartRequest = null;
		int fileMaxSize = 10*1024*1024; //파일 최대 사이즈
		String savePath = request.getRealPath("resources/upload"); // 업로드될 실제 경로
		
		try {
			//1.upload 폴더에 이미지 업로드
			multipartRequest = new MultipartRequest(request, savePath, fileMaxSize, "UTF-8", new DefaultFileRenamePolicy());
		} catch (Exception e) {
			e.printStackTrace();
			redirect.addFlashAttribute("messageType", "error");
			redirect.addFlashAttribute("message", "프로필사진 등록에 실패하였습니다.");
			
			return "redirect:/memberProfileForm.do";
		}
		
		//2.DB 테이블에 유저이미지 업데이트
		String memId = multipartRequest.getParameter("memId");
		String newProfile = "";
		File file = multipartRequest.getFile("memProfile"); //업로드된 파일을 가져와서 file 객체에 넣어줌
		
		if(file != null) { //업로드 된 상태
			// 이미지 파일여부 체크 -> 이미지 파일이 아니면 삭제
			String ext = file.getName().substring(file.getName().lastIndexOf(".") + 1); //파일의 이름을 가져와서 substring으로 확장자만 가져온다.
			ext = ext.toUpperCase(); //확장자를 대문자로 변환
			
			if(ext.equals("PNG") || ext.equals("GIF") || ext.equals("JPG") || ext.equals("JPEG")) {
				// 새로 업로드된 이미지, 현재 DB에 있는 이미지
				String oldProfile = memberMapper.getMember(memId).getMemProfile();
				File oldFile = new File(savePath + "/" + oldProfile); // 기존 DB에 있던 파일을 찾아서 file 객체에 넣어줌
				
				if(oldFile.exists()) { // 기존 DB에 있던 파일이 존재하면
					oldFile.delete(); // 삭제
				}
				
				newProfile = file.getName(); //업로드된 파일이름 할당
			} else { //이미지 파일이 아닐 경우 삭제
				if(file.exists()) { //파일이 있을 경우
					file.delete(); //파일 삭제
				}
				redirect.addFlashAttribute("messageType", "error");
				redirect.addFlashAttribute("message", "이미지 파일만 업로드 가능합니다.");
				
				return "redirect:/memberProfileForm.do";
			}
			
		}
		
		//새로운 이미지 파일을 업데이트
		Member member = new Member();
		member.setMemId(memId);
		member.setMemProfile(newProfile);
		memberMapper.memberProfileUpdate(member);
		
		//변경된 이미지의 정보를 포함하여 유저의 정보를 가져와서 세션에 새로 값을 부여
		Member user = memberMapper.getMember(memId);
		session.setAttribute("member", user);
		
		redirect.addFlashAttribute("messageType", "success");
		redirect.addFlashAttribute("message", "프로필 이미지 변경에 성공했습니다.");
		
		return "redirect:/";
	}
	
}
