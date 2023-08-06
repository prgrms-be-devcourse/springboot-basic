package com.programmers.springbootbasic.presentation.controller.dto.Voucher;

import com.programmers.springbootbasic.domain.voucher.VoucherType;
import jakarta.validation.constraints.NotNull;


public record VoucherCreationRequest(
        @NotNull
        VoucherType voucherType,
        @NotNull
        int amountOrPercent
) {
}
