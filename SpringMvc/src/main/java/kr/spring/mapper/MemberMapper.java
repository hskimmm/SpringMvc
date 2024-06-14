package kr.spring.mapper;

import org.apache.ibatis.annotations.Mapper;

import kr.spring.entity.Member;
import kr.spring.entity.MemberAuth;

@Mapper
public interface MemberMapper {
	public int memberRegisterCheck(String memberId);
	public int memRegister(Member member);
	public Member memberLogin(Member member);
	public int memberUpdate(Member member);
	public Member getMember(String memId);
	public void memberProfileUpdate(Member member);
	public void memberAuthInsert(MemberAuth saveAuth);
	public void memberAuthDelete(String memId);
}
