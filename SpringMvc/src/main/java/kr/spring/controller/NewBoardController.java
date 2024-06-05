package kr.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	//게시판상세
	@RequestMapping("/newBoardDetail.do")
	public @ResponseBody Board newBoardDetail(@RequestParam int idx){
		Board content = newBoardMapper.getNewBoardDetail(idx);
		return content;
	}
	
	//게시판작성
	@RequestMapping("/newBoardInsert.do")
	public @ResponseBody void newBoardInsert(Board vo) {		
		newBoardMapper.newBoardInsert(vo);
	}
	
	//게시판수정
	@RequestMapping("/newBoardUpdate.do")
	public @ResponseBody void newBoardUpdate(Board vo) {
		newBoardMapper.newBoardUpdate(vo);
	}
	
	//게시판삭제
	@RequestMapping("/newBoardDelete.do")
	public @ResponseBody void newBoardDelete(@RequestParam int idx) {
		newBoardMapper.newBoardDelete(idx);
	}
	
	//게시판조회수
	@RequestMapping("/newBoardCount.do")
	public @ResponseBody Board newBoardCount(@RequestParam int idx) {
		newBoardMapper.newBoardCount(idx); //게시판 조회수 증가
		Board newBoardList = newBoardMapper.getNewBoardDetail(idx); //조회수 업데이트 된 게시판 가져오기
		return newBoardList;
	}
}
