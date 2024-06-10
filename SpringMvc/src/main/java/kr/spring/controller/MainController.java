package kr.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	
	/**
	 * @apiNote context root 로 이동한다.
	 * @author hskim
	 * @since 2024-06-10 
	 * @return
	 */
	@RequestMapping("/")
	public String main() {
		return "index";
	}
}
