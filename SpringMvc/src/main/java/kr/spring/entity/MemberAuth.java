package kr.spring.entity;

import lombok.Data;

@Data
public class MemberAuth {
	private int no; //일련번호
	private String memId; //회원아이디
	private String auth; //회원권한
}
