package kr.spring.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.spring.entity.Board;
import kr.spring.mapper.NoticeMapper;

@Controller
public class NoticeController {
	
	@Autowired
	private NoticeMapper noticeMapper;
	
	@RequestMapping("/noticeMain.do")
	public String newNotice(Model model) {
		List<Board> noticeList = noticeMapper.NoticeList();
		model.addAttribute("noticeList", noticeList);
		
		return "notice/notice_list";
	}
	
	@RequestMapping("/createNoticeForm.do")
	public String createNoticeForm() {
		return "notice/create_notice";
	}
	
	@RequestMapping("/noticeCreate.do")
	public @ResponseBody void createNotice(Board notice, String memId, RedirectAttributes redirect) {
		notice.setWriter(memId);
		
		int result = noticeMapper.createNotice(notice);
		
		if(result > 0) {
			redirect.addFlashAttribute("messageType", "success");
			redirect.addFlashAttribute("message", "공지사항이 등록되었습니다.");
		} else {
			redirect.addFlashAttribute("messageType", "error");
			redirect.addFlashAttribute("message", "공지사항 등록 중 에러가 발생하였습니다.");
		}		
	}
}
