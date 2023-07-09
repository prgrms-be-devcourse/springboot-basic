package org.programmers.VoucherManagement.member.dto;

import org.programmers.VoucherManagement.member.domain.MemberStatus;

public class UpdateMemberRequest {
    private final MemberStatus memberStatus;

    public UpdateMemberRequest(MemberStatus memberStatus) {
        this.memberStatus = memberStatus;
    }

    public MemberStatus getMemberStatus() {
        return memberStatus;
    }
}
