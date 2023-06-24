package kr.co.springbootweeklymission.domain.member.dao;

import kr.co.springbootweeklymission.domain.member.entity.Member;

import java.util.List;

public interface MemberRepository {
    List<Member> findMembersByBlack();
}
