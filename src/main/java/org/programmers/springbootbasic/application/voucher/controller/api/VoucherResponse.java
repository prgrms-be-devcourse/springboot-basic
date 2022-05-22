package org.programmers.springbootbasic.application.voucher.controller.api;

import org.programmers.springbootbasic.application.voucher.model.VoucherType;

import java.time.LocalDateTime;
import java.util.UUID;

public record VoucherResponse(
        UUID voucherId,
        Long value,
        LocalDateTime createdAt,
        VoucherType voucherType
) {
}
