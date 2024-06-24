package kr.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NewBoardController {
	
	/**
	 * @apiNote 게시판 페이지로 이동한다.
	 * @author hskim
	 * @since 2024-06-10
	 * @return
	 */
	@RequestMapping("/boardMain.do")
	public String newBoard() {
		return "board/newBoard";
	}
}
