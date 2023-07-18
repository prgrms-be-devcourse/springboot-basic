package org.programmers.VoucherManagement.member.dto.request;

import org.programmers.VoucherManagement.member.domain.MemberStatus;

public class MemberCreateRequest {
    private final String name;
    private final MemberStatus memberStatus;

    public MemberCreateRequest(String name, MemberStatus memberStatus) {
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
