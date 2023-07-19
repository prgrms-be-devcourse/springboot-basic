package com.tangerine.voucher_system.application.voucher.controller.dto;

import com.tangerine.voucher_system.application.voucher.model.DiscountValue;
import com.tangerine.voucher_system.application.voucher.model.VoucherType;

import java.time.LocalDate;

public record CreateVoucherRequest(
        VoucherType voucherType,
        DiscountValue discountValue,
        LocalDate createdAt
) {
}
