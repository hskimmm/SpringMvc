package kr.spring.entity;

import lombok.Data;

//Lombok = setter, getter, toString 자동으로 생성해주는 api
@Data
public class Board {
	private int id; //게시판 번호
	private String title; //게시판 제목
	private String content; //게시판 내용
	private String writer; //게시판 작성자
	private String indate; //게시판 작성일
	private int count; //게시판 조회수
}
