package org.programmers.VoucherManagement.member.dto.request;

import org.programmers.VoucherManagement.member.domain.MemberStatus;

public class MemberUpdateRequest {
    private final MemberStatus memberStatus;

    public MemberUpdateRequest(MemberStatus memberStatus) {
        this.memberStatus = memberStatus;
    }

    public MemberStatus getMemberStatus() {
        return memberStatus;
    }
}
