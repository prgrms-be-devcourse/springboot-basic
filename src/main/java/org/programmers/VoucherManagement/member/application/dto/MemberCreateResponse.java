package org.programmers.VoucherManagement.member.application.dto;

import org.programmers.VoucherManagement.member.domain.MemberStatus;

import java.util.UUID;

public record MemberCreateResponse(
        UUID memberId,
        String name,
        MemberStatus memberStatus
) {
}
