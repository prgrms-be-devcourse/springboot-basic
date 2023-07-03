package com.devcourse.voucher.application.dto;

import com.devcourse.voucher.domain.VoucherType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record GetVoucherResponse(
        UUID id,
        VoucherType type,
        BigDecimal discount,
        LocalDateTime expiredAt,
        String status) {
}
