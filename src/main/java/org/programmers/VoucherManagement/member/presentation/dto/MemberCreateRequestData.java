package org.programmers.VoucherManagement.member.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public record MemberCreateRequestData(
        @NotBlank
        String name,
        @NotBlank
        String memberStatus
) {
}
