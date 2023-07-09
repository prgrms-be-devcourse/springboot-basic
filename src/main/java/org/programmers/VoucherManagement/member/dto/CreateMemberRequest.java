package org.programmers.VoucherManagement.member.dto;

import org.programmers.VoucherManagement.member.domain.MemberStatus;

public class CreateMemberRequest {
    private final String name;
    private final MemberStatus memberStatus;

    public CreateMemberRequest(String name, MemberStatus memberStatus) {
        this.name = name;
        this.memberStatus = memberStatus;
    }

    public String getName() {
        return name;
    }

    public MemberStatus getMemberStatus() {
        return memberStatus;
    }
}
