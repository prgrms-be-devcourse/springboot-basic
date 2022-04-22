package com.pppp0722.vouchermanagement.member.service;

import com.pppp0722.vouchermanagement.member.model.Member;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MemberService {

    Optional<Member> createMember(UUID memberId, String name);

    List<Member> getAllMembers();

    Optional<Member> getMemberById(UUID memberID);

    Optional<Member> updateMember(UUID memberId, String name);

    Optional<Member> deleteMember(UUID memberId);
}
