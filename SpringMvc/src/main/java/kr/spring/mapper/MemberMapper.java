package kr.spring.mapper;

import org.apache.ibatis.annotations.Mapper;

import kr.spring.entity.Member;

@Mapper
public interface MemberMapper {
	public int memberRegisterCheck(String memberId);
	public int memRegister(Member member);
	public Member memberLogin(Member member);
}
