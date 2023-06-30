package kr.co.springbootweeklymission.member.domain.repository;

import kr.co.springbootweeklymission.member.domain.entity.Member;

import java.util.List;

public interface MemberRepository {
    Member save(Member member);

    List<Member> findMembersByBlack();
}
