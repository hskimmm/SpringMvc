package kr.spring.entity;

//Lombok = setter, getter, toString 자동으로 생성해주는 api
public class Board {
	private int id; //게시판 번호
	private String title; //게시판 제목
	private String content; //게시판 내용
	private String writer; //게시판 작성자
	private String indate; //게시판 작성일
	private int count; //게시판 조회수
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getIndate() {
		return indate;
	}
	public void setIndate(String indate) {
		this.indate = indate;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	
}
