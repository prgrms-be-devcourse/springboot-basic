package org.prgms.kdt.application.Member.repository;

import org.prgms.kdt.application.Member.domain.Member;

import java.util.List;

public interface MemberRepository {
    List<Member> getBlacklist();
}
