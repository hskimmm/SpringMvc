package kr.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.spring.entity.Board;
import kr.spring.mapper.NewBoardMapper;

@RequestMapping("/board")
@RestController //@Controller + @ResponsBody 결합체, @ResponsBody -> 모든 객체를 JSON으로 응답
public class NewBoardRestController {
	
	
	@Autowired
	NewBoardMapper newBoardMapper;
	
	
	/**
	 * @apiNote 게시판 목록을 조회한다.
	 * @author hskim
	 * @since 2024-06-10
	 * @return
	 */
	@GetMapping("/all")
	public List<Board> newBoardList(){
		//@ResponseBody -> jackson-databind(객체를 JSON 데이터 포멧으로 변환)
		List<Board> newBoardList = newBoardMapper.getNewBoardList();
		
		return newBoardList; // 객체를 리턴 -> json 데이터 형식으로 변환해서 리턴한다는 뜻
	}
	
	
	/**
	 * @apiNote 특정 게시판의 상세 페이지를 조회한다.
	 * @author hskim
	 * @since 2024-06-10
	 * @param idx
	 * @return
	 * @throws NullPointException
	 */
	@GetMapping("/{idx}")
	public Board newBoardDetail(@PathVariable("idx") int idx){
		Board content = newBoardMapper.getNewBoardDetail(idx);
		return content;
	}
	
	
	/**
	 * @apiNote 게시판을 추가한다.
	 * @author hskim
	 * @since 2024-06-10
	 * @param vo
	 * @throws NullPointException
	 */
	@PostMapping("/new")
	public void newBoardInsert(Board vo) {		
		newBoardMapper.newBoardInsert(vo);
	}
	
	
	/**
	 * @apiNote 게시판을 수정한다.
	 * @author hskim
	 * @since 2024-06-10
	 * @param vo
	 * @throws NullPointException
	 */
	@PutMapping("/update")
	public void newBoardUpdate(@RequestBody Board vo) {
		// @RequestBody 를 사용해야 Ajax로부터 넘어온 Json 데이터를 Board vo 객체에 담을 수 있다.
		newBoardMapper.newBoardUpdate(vo);
	}
	
	
	/**
	 * @apiNote 게시판을 삭제한다.
	 * @author hskim
	 * @since 2024-06-10
	 * @param idx
	 * @throws NullPointException
	 */
	@DeleteMapping("/{idx}")
	public void newBoardDelete(@PathVariable("idx") int idx) {
		newBoardMapper.newBoardDelete(idx);
	}
	
	
	/**
	 * @apiNote 게시판의 조회수를 업데이트한다.
	 * @apiNote 조회수가 업데이트된 게시판 목록을 조회한다.
	 * @author hskim
	 * @since 2024-06-10
	 * @param idx
	 * @return
	 * @throws NullPointException
	 */
	@PutMapping("/count/{idx}")
	public Board newBoardCount(@PathVariable("idx") int idx) {
		newBoardMapper.newBoardCount(idx); //게시판 조회수 증가
		Board newBoardList = newBoardMapper.getNewBoardDetail(idx); //조회수 업데이트 된 게시판 가져오기
		return newBoardList;
	}
}
