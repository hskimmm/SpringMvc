package kr.spring.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.spring.entity.Board;

@Mapper
public interface NewBoardMapper {
	public List<Board> getNewBoardList();

}
