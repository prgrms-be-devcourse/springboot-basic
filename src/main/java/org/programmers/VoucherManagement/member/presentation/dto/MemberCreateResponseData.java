package org.programmers.VoucherManagement.member.presentation.dto;

import org.programmers.VoucherManagement.member.domain.MemberStatus;

import java.util.UUID;

public record MemberCreateResponseData(
        UUID memberId,
        String name,
        MemberStatus memberStatus
) {
}
