package com.pppp0722.vouchermanagement.service.member;

import com.pppp0722.vouchermanagement.entity.member.Member;
import java.util.List;
import java.util.UUID;

public interface MemberService {

    Member createMember(UUID memberId, String name);

    List<Member> getAllMembers();
}
