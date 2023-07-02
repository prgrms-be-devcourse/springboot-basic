package org.prgrms.kdt.member.dao;

import org.prgrms.kdt.member.domain.Member;

import java.util.List;

public interface MemberRepository {
    List<Member> findAllBlackMember();

    Member insert(Member member);
}
