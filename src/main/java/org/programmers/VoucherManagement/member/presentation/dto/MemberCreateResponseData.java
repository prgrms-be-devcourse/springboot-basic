package org.programmers.VoucherManagement.member.presentation.dto;

import org.programmers.VoucherManagement.member.domain.MemberStatus;

public record MemberCreateResponseData(
        String memberId,
        String name,
        MemberStatus memberStatus
) {
}
