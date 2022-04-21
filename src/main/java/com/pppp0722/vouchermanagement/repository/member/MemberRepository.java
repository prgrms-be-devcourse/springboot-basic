package com.pppp0722.vouchermanagement.repository.member;

import com.pppp0722.vouchermanagement.entity.member.Member;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MemberRepository {

    Member createMember(Member member);

    List<Member> readMembers();

    Optional<Member> readMember(UUID memberId);

    Member updateMember(Member member);

    Member deleteMember(Member member);
}