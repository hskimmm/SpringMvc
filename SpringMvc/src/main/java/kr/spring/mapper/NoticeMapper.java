package kr.spring.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.spring.entity.Board;

@Mapper
public interface NoticeMapper {
	public int createNotice(Board notice);
	public List<Board> NoticeList();
}
