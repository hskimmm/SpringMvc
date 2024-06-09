package kr.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NewBoardController {
	
	@RequestMapping("/boardMain.do")
	public String newBoard() {
		return "board/newBoard";
	}
	
}
