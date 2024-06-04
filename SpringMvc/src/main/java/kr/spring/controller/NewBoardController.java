package kr.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.spring.entity.Board;
import kr.spring.mapper.NewBoardMapper;

@Controller
public class NewBoardController {
	
	
	@Autowired
	NewBoardMapper newBoardMapper;
	
	@RequestMapping("/")
	public String newBoard() {
		return "newBoard";
	}
	
	//게시판리스트	
	@RequestMapping("/newBoardList.do")
	public @ResponseBody List<Board> newBoardList(){
		//@ResponseBody -> jackson-databind(객체를 JSON 데이터 포멧으로 변환)
		List<Board> newBoardList = newBoardMapper.getNewBoardList();
		
		return newBoardList; // 객체를 리턴 -> json 데이터 형식으로 변환해서 리턴한다는 뜻
	}
}
