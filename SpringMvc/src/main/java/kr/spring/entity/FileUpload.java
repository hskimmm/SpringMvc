package kr.spring.entity;

import lombok.Data;

@Data
public class FileUpload {
	private int id;
	private String filename;
	private String savefilename;
	private String filepath;
	private int board_id;
	private String deleteYn;
}
