package com.programmers.vouchermanagement.member.domain;

import java.util.UUID;

public class Member {

    private final UUID memberId;
    private final String name;
    private final MemberType memberType;

    public Member(UUID memberId, String name, MemberType memberType) {
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
