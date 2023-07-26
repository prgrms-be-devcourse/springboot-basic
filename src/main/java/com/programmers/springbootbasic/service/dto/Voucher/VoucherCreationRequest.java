package com.programmers.springbootbasic.service.dto.Voucher;

import com.programmers.springbootbasic.domain.voucher.VoucherType;


public record VoucherCreationRequest(
        VoucherType voucherType,
        int amountOrPercent
) {
}
