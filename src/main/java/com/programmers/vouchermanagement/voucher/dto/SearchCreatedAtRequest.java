package com.programmers.vouchermanagement.voucher.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record SearchCreatedAtRequest(@NotNull LocalDate startDate, @NotNull LocalDate endDate) {
}
