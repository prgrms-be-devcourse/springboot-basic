package org.prgrms.kdt.member.dao;

import org.prgrms.kdt.member.domain.Member;
import org.prgrms.kdt.member.domain.MemberName;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MemberRepository {
    Member insert(Member member);
    Member update(Member member);
    List<Member> findAll();
    Optional<Member> findById(UUID memberId);
    Optional<Member> findByName(MemberName memberName);
    List<Member> findAllBlackMember();
}
