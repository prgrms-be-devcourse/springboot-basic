package org.programmers.VoucherManagement.member.dto.request;

import org.programmers.VoucherManagement.member.domain.MemberStatus;

public record MemberCreateRequest(
        String name,
        MemberStatus memberStatus
) {
}
