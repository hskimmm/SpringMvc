package kr.spring.controller;


import java.io.File;
import java.util.List;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.spring.entity.Board;
import kr.spring.entity.FileUpload;
import kr.spring.entity.Member;
import kr.spring.entity.MemberAuth;
import kr.spring.mapper.NoticeMapper;

@Controller
public class NoticeController {
	
	private final String uploadDir = "C:/upload";
	
	@Autowired
	private NoticeMapper noticeMapper;
	
	
	/**
	 * @apiNote 공지사항 페이지 이동
	 * @author hskim
	 * @since 2024-06-24
	 * @param model
	 * @return
	 */
	@RequestMapping("/noticeMain.do")
	public String newNotice(Model model, HttpSession session) {
		try {
			//공지사항 목록 조회
			List<Board> noticeList = noticeMapper.noticeList();
			model.addAttribute("noticeList", noticeList);
			
			//권한 처리
			Member member = (Member) session.getAttribute("member");
			List<MemberAuth> memberAuthlist = member.getMemberAuthList();
			
			for(MemberAuth auth : memberAuthlist) {
				if(auth.getAuth().equals("ROLE_ADMIN")) {
					session.setAttribute("noticeAuth", auth.getAuth());
					model.addAttribute("memberAuth", auth.getAuth());
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "notice/notice_list";
	}
	
	
	/**
	 * @apiNote 공지사항 생성 페이지 이동
	 * @author hskim
	 * @since 2024-06-24
	 * @return
	 */
	@RequestMapping("/createNoticeForm.do")
	public String createNoticeForm() {
		return "notice/create_notice";
	}
	
	
	/**
	 * @apiNote 공지사항 생성
	 * @author hskim
	 * @since 2024-06-26
	 * @param notice
	 * @param file
	 * @param memId
	 * @param redirect
	 * @param model
	 */
	@RequestMapping("/noticeCreate.do")
	public @ResponseBody void createNotice(Board notice, MultipartFile file, String memId, RedirectAttributes redirect, Model model) {
		try {
			//공지사항 생성
			notice.setWriter(memId);

			int result = noticeMapper.createNotice(notice);
			
			if(result > 0) {
				redirect.addFlashAttribute("messageType", "success");
				redirect.addFlashAttribute("message", "공지사항이 등록되었습니다.");
			} else {
				redirect.addFlashAttribute("messageType", "error");
				redirect.addFlashAttribute("message", "공지사항 등록 중 에러가 발생하였습니다.");
			}
			
			//파일 업로드
			if(!file.isEmpty()) {
				//파일 지정 경로에 저장
				String originalFileName = file.getOriginalFilename();
				int index = originalFileName.lastIndexOf('.');
				String fileExt = originalFileName.substring(index + 1).toLowerCase();
				
				if(fileExt.equals("jpg") || fileExt.equals("jpeg") || fileExt.equals("png") || fileExt.equals("gif")) {
					File dir = new File(uploadDir);
					
					if(!dir.exists()) {
						dir.mkdirs();
					}
					
					String filePath = dir + File.separator + System.currentTimeMillis() + "-" + originalFileName;
					
					File dest = new File(filePath);
					file.transferTo(dest);
					
					//DB 저장
					FileUpload fileUpload = new FileUpload();
					fileUpload.setFilename(originalFileName);
					fileUpload.setFilepath(filePath);
					
					noticeMapper.createFile(fileUpload);
				} else {
					throw new Exception("이미지 파일만 업로드 가능합니다.");
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * @apiNote 공지사항 상세 페이지 이동
	 * @author hskim
	 * @since 2024-06-24
	 * @param idx
	 * @param model
	 * @return
	 */
	@RequestMapping("/noticeDetail.do")
	public String noticeDetail(@RequestParam int idx, Model model, HttpSession session) {
		try {
			//조회수 증가
			noticeMapper.noticeCount(idx);
			
			//상세 페이지 조회
			Board noticeDetail = noticeMapper.noticeDetail(idx);
			model.addAttribute("notice", noticeDetail);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "notice/notice_detail";
	}
	
	
	/**
	 * @apiNote 공지사항 삭제
	 * @author hskim
	 * @since 2024-06-24
	 * @param idx
	 * @param redirect
	 */
	@RequestMapping("/noticeDelete.do")
	public @ResponseBody void noticeDelete(@RequestParam int idx, RedirectAttributes redirect) {
		try {
			int result = noticeMapper.noticeDelete(idx);
			
			if(result > 0) {
				redirect.addFlashAttribute("messageType", "success");
				redirect.addFlashAttribute("message", "공지사항을 삭제하였습니다.");
			} else {
				redirect.addFlashAttribute("meesageType", "error");
				redirect.addFlashAttribute("message", "공지사항 삭제에 실패하였습니다.");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @apiNote 공지사항 수정 페이지로 이동
	 * @author hskim
	 * @since 2024-06-25
	 * @param idx
	 * @param model
	 * @return
	 */
	@RequestMapping("/noticeUpdateForm.do")
	public String noticeUpdateForm(@RequestParam int idx, Model model) {
		Board noticeDetail = noticeMapper.noticeDetail(idx);
		model.addAttribute("notice", noticeDetail);
		
		return "notice/notice_update";
	}
	
	
	/**
	 * @apiNote 공지사항 수정
	 * @author hskim
	 * @since 2024-06-25
	 * @param notice
	 * @param redirect
	 */
	@RequestMapping("/noticeUpdate.do")
	public @ResponseBody void noticeUpdate(Board notice, RedirectAttributes redirect) {
		try {
			int updateResult = noticeMapper.noticeUpdate(notice);
			
			if(updateResult > 0) {
				redirect.addFlashAttribute("messageType", "success");
				redirect.addFlashAttribute("message", "공지사항을 수정하였습니다.");
			} else {
				redirect.addFlashAttribute("messageType", "error");
				redirect.addFlashAttribute("message", "공지사항 수정에 실패하였습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
