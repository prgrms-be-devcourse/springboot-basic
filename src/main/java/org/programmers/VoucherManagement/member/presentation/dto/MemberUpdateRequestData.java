package org.programmers.VoucherManagement.member.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public record MemberUpdateRequestData(
        @NotBlank
        String memberStatus
) {
}
