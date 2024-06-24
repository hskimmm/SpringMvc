package kr.spring.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.spring.entity.Board;
import kr.spring.mapper.NoticeMapper;

@Controller
public class NoticeController {
	
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
	public String newNotice(Model model) {
		try {
			List<Board> noticeList = noticeMapper.noticeList();
			model.addAttribute("noticeList", noticeList);
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
	 * @since 2024-06-24
	 * @param notice
	 * @param memId
	 * @param redirect
	 */
	@RequestMapping("/noticeCreate.do")
	public @ResponseBody void createNotice(Board notice, String memId, RedirectAttributes redirect) {
		try {
			notice.setWriter(memId);
			
			int result = noticeMapper.createNotice(notice);
			
			if(result > 0) {
				redirect.addFlashAttribute("messageType", "success");
				redirect.addFlashAttribute("message", "공지사항이 등록되었습니다.");
			} else {
				redirect.addFlashAttribute("messageType", "error");
				redirect.addFlashAttribute("message", "공지사항 등록 중 에러가 발생하였습니다.");
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
	public String noticeDetail(@RequestParam int idx, Model model) {
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
}
