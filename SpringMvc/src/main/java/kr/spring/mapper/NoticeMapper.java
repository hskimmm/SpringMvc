package kr.spring.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.spring.entity.Board;
import kr.spring.entity.FileUpload;

@Mapper
public interface NoticeMapper {
	public int createNotice(Board notice);
	public List<Board> noticeList();
	public Board noticeDetail(int idx);
	public int noticeDelete(int idx);
	public void noticeCount(int idx);
	public int noticeUpdate(Board notice);
	public void createFile(FileUpload fileUpload);
	public FileUpload fileDetail(int idx);
	public void updateFile(FileUpload fileUpload);
	public int deleteFile(FileUpload fileUpload);
	public int fileCountSelect(FileUpload fileUpload);
}
