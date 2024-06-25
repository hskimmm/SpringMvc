package kr.spring.entity;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Member {
	private int memIdx; //인덱스
	private String memId; //회원아이디
	private String memPassword; //회원비밀번호
	private String memName; //회원이름
	private int memAge; //회원나이
	private String memGender; //회원성별
	private String memEmail; //회원이메일
	private String memProfile; //회원파일
	private List<MemberAuth> memberAuthList;
}
