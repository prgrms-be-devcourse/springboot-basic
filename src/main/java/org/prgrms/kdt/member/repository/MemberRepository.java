package org.prgrms.kdt.member.repository;

import org.prgrms.kdt.member.domain.Member;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface MemberRepository {
    Map<UUID, Member> findByAllBlackList();

    void init(List<String> lines);
}
