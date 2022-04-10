package org.prgrms.kdt.domain.member.repository;

import org.prgrms.kdt.domain.member.model.Member;

import java.util.List;

public interface MemberRepository {

    List<Member> findAll();
}
