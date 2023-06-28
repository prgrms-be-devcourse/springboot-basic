package com.devcourse.voucher.application.dto;

import com.devcourse.voucher.domain.VoucherStatus;
import com.devcourse.voucher.domain.VoucherType;

import java.time.LocalDateTime;
import java.util.UUID;

public record GetVoucherResponse(
        UUID id,
        VoucherType type,
        LocalDateTime expiredAt,
        VoucherStatus status) {
}
