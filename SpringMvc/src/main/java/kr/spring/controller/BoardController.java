package kr.spring.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.spring.entity.Board;
import kr.spring.mapper.BoardMapper;

@Controller
public class BoardController {
	
	
	@Autowired
	private BoardMapper boardMapper;
	
	
	/**
	 * @apiNote 게시판 목록을 조회한다.
	 * @author hskim
	 * @since 2024-06-10
	 * @param model
	 * @return
	 */
	@RequestMapping("/boardList.do")
	public String getBoardList(Model model) {
		//게시판 리스트 가져오기
		List<Board> list = new ArrayList<Board>();
		list = boardMapper.getList();

		model.addAttribute("boardList", list);
		
		return "boardList";
	}
	
	
	/**
	 * @apiNote 게시판 추가(폼) 페이지로 이동한다.
	 * @author hskim
	 * @since 2024-06-10
	 * @return
	 */
	@GetMapping("/boardForm.do")
	public String boardForm() {
		return "boardForm";
	}
	
	
	/**
	 * @apiNote 게시판을 추가한다.
	 * @author hskim
	 * @since 2024-06-10
	 * @param vo
	 * @return
	 * @throws NullPointException
	 */
	@PostMapping("/boardInsert.do")
	public String boardInsert(Board vo) { // JSP input 값을 Board 클래스로 받음(title, content, writer)
		boardMapper.boardInsert(vo);
		
		return "redirect:/boardList.do";
	}
	
	
	/**
	 * @apiNote 특정 게시판의 상세페이지를 조회한다.
	 * @author hskim
	 * @since 2024-06-10
	 * @param idx
	 * @param model
	 * @return
	 * @throws NullPointException
	 */
	@GetMapping("/boardDetail.do")
	public String boardDetail(@RequestParam("idx") int idx, Model model) { // 방법1.JSP에서 쿼리스트링으로 넘어온 값을 가져온다.
		Board vo = boardMapper.boardDetail(idx);
		model.addAttribute("list", vo);
		
		// 조회수 증가(상세 페이지 진입시 count + 1)
		boardMapper.boardCount(idx);
		
		return "boardDetail";
	}
	
	
	/**
	 * @apiNote 특정 게시판의 수정화면으로 이동한다.
	 * @author hskim
	 * @since 2024-06-10
	 * @param idx
	 * @param model
	 * @return
	 * @throws NullPointException
	 */
	@GetMapping("/boardUpdateForm.do/{idx}")
	public String boardUpdateForm(@PathVariable int idx, Model model) {
		Board vo = boardMapper.boardDetail(idx);
		model.addAttribute("list", vo);
		
		
		return "boardUpdate";
	}
	
	
	/**
	 * @apiNote 게시판을 수정한다.
	 * @author hskim
	 * @since 2024-06-10
	 * @param vo
	 * @return
	 * @throws NullPointException
	 */
	@PostMapping("/boardUpdate.do")
	public String boardUpdate(Board vo) {
		boardMapper.boardUpdate(vo);
		
		return "redirect:/boardList.do";
	}
	
	
	/**
	 * @apiNote 게시판을 삭제한다.
	 * @author hskim
	 * @since 2024-06-10
	 * @param idx
	 * @return
	 * @throws NullPointException
	 */
	@GetMapping("/boardDelete.do/{idx}")
	public String boardDelete(@PathVariable int idx) { // 방법2.JSP에서 넘어온 값을 가져온다
		boardMapper.boardDelete(idx);
		
		return "redirect:/boardList.do";
	}
}
