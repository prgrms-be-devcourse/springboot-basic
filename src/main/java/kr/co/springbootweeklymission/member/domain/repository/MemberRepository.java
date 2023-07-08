package kr.co.springbootweeklymission.member.domain.repository;

import kr.co.springbootweeklymission.member.domain.entity.Member;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MemberRepository {
    Member save(Member member);

    Optional<Member> findById(UUID memberId);

    List<Member> findAllByBlack();

    List<Member> findAll();

    void update(Member member);

    void delete(Member member);
}
