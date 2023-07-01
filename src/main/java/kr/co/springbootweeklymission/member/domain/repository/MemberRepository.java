package kr.co.springbootweeklymission.member.domain.repository;

import kr.co.springbootweeklymission.member.domain.entity.Member;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MemberRepository {
    Member save(Member member);

    List<Member> findMembersByBlack();

    Optional<Member> findById(UUID memberId);

    void update(Member member);

    void deleteById(UUID memberId);
}
