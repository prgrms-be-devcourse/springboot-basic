package com.pppp0722.vouchermanagement.member.service;

import com.pppp0722.vouchermanagement.member.model.Member;
import java.util.List;
import java.util.UUID;

public interface MemberService {

    Member createMember(UUID memberId, String name);

    List<Member> getAllMembers();

    Member getMemberById(UUID memberID);

    Member updateMember(UUID memberId, String name);

    Member deleteMember(UUID memberId);

    void deleteAllMembers();
}
