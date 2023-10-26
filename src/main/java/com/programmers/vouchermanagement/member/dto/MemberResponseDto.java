package com.programmers.vouchermanagement.member.dto;

import com.programmers.vouchermanagement.member.domain.MemberType;

import java.util.UUID;

public class MemberResponseDto {

    private final UUID memberId;
    private final String name;
    private final MemberType memberType;

    public MemberResponseDto(UUID memberId, String name, MemberType memberType) {
        this.memberId = memberId;
        this.name = name;
        this.memberType = memberType;
    }

    public UUID getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public MemberType getMemberType() {
        return memberType;
    }
}
