package com.programmers.springbootbasic.presentation.controller.dto.Voucher;

import com.programmers.springbootbasic.domain.voucher.VoucherType;


public record VoucherCreationRequest(
        VoucherType voucherType,
        int amountOrPercent
) {
}
