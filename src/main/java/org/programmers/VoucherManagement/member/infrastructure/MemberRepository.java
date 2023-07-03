package org.programmers.VoucherManagement.member.infrastructure;

import org.programmers.VoucherManagement.member.domain.Member;

import java.util.List;

public interface MemberRepository {
    List<Member> findAllByMemberStatus();
}
