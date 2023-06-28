package com.devcourse.voucher.application.dto;

import java.time.LocalDateTime;

public record CreateVoucherRequest(
        String typeSymbol,
        int discount,
        LocalDateTime expiredAt) {
}
