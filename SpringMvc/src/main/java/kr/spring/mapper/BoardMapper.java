package kr.spring.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.spring.entity.Board;

@Mapper
public interface BoardMapper {
	public List<Board> getList();
	public void boardInsert(Board vo);
	public Board boardDetail(int idx);
	public void boardUpdate(Board vo);
	public void boardDelete(int idx);
	public void boardCount(int idx);
}
