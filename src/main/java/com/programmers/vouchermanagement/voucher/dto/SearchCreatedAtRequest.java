package com.programmers.vouchermanagement.voucher.dto;

import java.time.LocalDate;

public record SearchCreatedAtRequest(LocalDate startDate, LocalDate endDate) {
}
