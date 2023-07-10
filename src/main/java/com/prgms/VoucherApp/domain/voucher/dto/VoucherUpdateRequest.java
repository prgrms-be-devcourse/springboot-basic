package com.prgms.VoucherApp.domain.voucher.dto;

import com.prgms.VoucherApp.domain.voucher.model.VoucherType;

import java.math.BigDecimal;
import java.util.UUID;

public record VoucherUpdateRequest(

    UUID id,
    BigDecimal amount,
    VoucherType voucherType
) {
}
