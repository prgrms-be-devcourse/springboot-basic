package com.tangerine.voucher_system.application.voucher.service.dto;

import com.tangerine.voucher_system.application.voucher.model.DiscountValue;
import com.tangerine.voucher_system.application.voucher.model.VoucherType;

import java.time.LocalDate;
import java.util.UUID;

public record VoucherResult(
        UUID voucherId,
        VoucherType voucherType,
        DiscountValue discountValue,
        LocalDate createdAt
) {
}
