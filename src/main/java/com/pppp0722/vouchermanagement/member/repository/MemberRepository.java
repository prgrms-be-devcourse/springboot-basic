package com.pppp0722.vouchermanagement.member.repository;

import com.pppp0722.vouchermanagement.member.model.Member;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MemberRepository {

    Optional<Member> createMember(Member member);

    List<Member> readMembers();

    Optional<Member> readMember(UUID memberId);

    Optional<Member> updateMember(Member member);

    Optional<Member> deleteMember(Member member);
}