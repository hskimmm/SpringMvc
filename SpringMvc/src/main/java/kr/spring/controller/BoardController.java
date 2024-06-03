package kr.spring.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.spring.entity.Board;
import kr.spring.mapper.BoardMapper;

@Controller
public class BoardController {
	
	
	@Autowired
	private BoardMapper boardMapper;
	
	//HandlerMapping 클래스
	//게시판 리스트
	@RequestMapping("/boardList.do")
	public String getBoardList(Model model) {
		//게시판 리스트 가져오기
		List<Board> list = new ArrayList<Board>();
		list = boardMapper.getList();

		model.addAttribute("boardList", list);
		
		return "boardList";
	}
}
