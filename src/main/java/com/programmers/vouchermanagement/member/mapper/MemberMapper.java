package com.programmers.vouchermanagement.member.mapper;

import com.programmers.vouchermanagement.member.domain.Member;
import com.programmers.vouchermanagement.member.domain.MemberType;

import java.util.UUID;

public class MemberMapper {

    public static Member toEntity(String[] memberData) {

        UUID memberId = UUID.fromString(memberData[0]);
        String name = memberData[1];
        MemberType memberType = MemberType.BLACK;

        return new Member(memberId, name, memberType);
    }
}
