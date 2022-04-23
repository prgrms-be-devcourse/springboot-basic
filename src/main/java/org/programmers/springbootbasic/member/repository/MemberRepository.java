package org.programmers.springbootbasic.member.repository;

import org.programmers.springbootbasic.member.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member insert(Member member);

    Optional<Member> findById(Long memberId);

    List<Member> findAll();

    void remove(Long memberId);

}