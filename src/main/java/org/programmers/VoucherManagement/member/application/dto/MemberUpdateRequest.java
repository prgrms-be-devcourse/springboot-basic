package org.programmers.VoucherManagement.member.application.dto;

import org.programmers.VoucherManagement.member.domain.MemberStatus;

public record MemberUpdateRequest(MemberStatus memberStatus) {
}
