package com.tangerine.voucher_system.application.voucher.controller.dto;

import com.tangerine.voucher_system.application.voucher.model.VoucherType;

public record CreateVoucherRequest(
        VoucherType voucherType,
        double discountValue
) {
}
