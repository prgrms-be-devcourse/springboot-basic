package com.devcourse.voucher.application.dto;

import com.devcourse.voucher.domain.Voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public record GetVoucherResponse(
        UUID id,
        Voucher.Type type,
        int discount,
        LocalDateTime expiredAt,
        String status) {
}
