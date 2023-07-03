package com.devcourse.voucher.application.dto;

import com.devcourse.voucher.domain.VoucherType;

import java.time.LocalDateTime;

public record CreateVoucherRequest(
        VoucherType type,
        int discount,
        LocalDateTime expiredAt) {

    public static CreateVoucherRequest of(String type, int discount, LocalDateTime expiredAt) {
        return new CreateVoucherRequest(VoucherType.from(type), discount, expiredAt);
    }
}
