package org.programmers.VoucherManagement.member.application.dto;

import org.programmers.VoucherManagement.member.domain.MemberStatus;

public record MemberCreateResponse(
        String memberId,
        String name,
        MemberStatus memberStatus
) {
}
