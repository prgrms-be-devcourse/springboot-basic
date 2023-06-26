package org.prgrms.kdt.member.repository;

import org.prgrms.kdt.member.domain.Member;

import java.io.IOException;
import java.util.List;

public interface MemberRepository {
    List<Member> findAllBlackMember() throws IOException;
}
