package com.prgmrs.voucher.dto.request;

import com.prgmrs.voucher.exception.WrongRangeFormatException;

import java.time.LocalDateTime;

public record VoucherSearchRequest(LocalDateTime startDate, LocalDateTime endDate, String discountType) {
    public VoucherSearchRequest {
        if (startDate == null) {
            throw new WrongRangeFormatException("startDate cannot be null");
        }
        if (endDate == null) {
            throw new WrongRangeFormatException("endDate cannot be null");
        }
        if (discountType == null || discountType.isBlank()) {
            throw new WrongRangeFormatException("discountType cannot be null or blank");
        }
    }
}
