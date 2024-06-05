package kr.spring.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.spring.entity.Board;

@Mapper
public interface NewBoardMapper {
	public List<Board> getNewBoardList();
	public Board getNewBoardDetail(int idx);
	public void newBoardInsert(Board vo);
	public void newBoardUpdate(Board vo);
	public void newBoardDelete(int idx);
	public void newBoardCount(int idx);
}
