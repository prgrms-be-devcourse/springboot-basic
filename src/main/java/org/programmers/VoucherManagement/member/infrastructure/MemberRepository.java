package org.programmers.VoucherManagement.member.infrastructure;

import org.programmers.VoucherManagement.member.domain.Member;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MemberRepository {
    List<Member> findAll();

    List<Member> findAllByMemberStatus();

    Optional<Member> findById(UUID MemberId); //조회

    void insert(Member member); //저장

    void update(Member member); //수정

    void delete(Member member); //삭제

    void deleteAll();  //전체 삭제

}
