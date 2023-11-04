package com.programmers.vouchermanagement.voucher.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

public record SearchCreatedAtRequest(
        @NotBlank(message = "Start-date cannot be blank.") LocalDate startDate,
        @NotBlank(message = "End-date cannot be blank.") LocalDate endDate
) {
}
