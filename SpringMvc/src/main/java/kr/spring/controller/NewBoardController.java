package kr.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NewBoardController {
	
	@RequestMapping("/")
	public String newBoard() {
		return "newBoard";
	}
	
}
