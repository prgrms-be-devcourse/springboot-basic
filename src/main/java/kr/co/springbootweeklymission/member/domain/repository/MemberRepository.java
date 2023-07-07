package kr.co.springbootweeklymission.member.domain.repository;

import kr.co.springbootweeklymission.member.domain.entity.Member;

import java.util.List;

public interface MemberRepository {
    List<Member> findMembersByBlack();
}
