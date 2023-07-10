package com.prgms.VoucherApp.domain.voucher.dto;

import com.prgms.VoucherApp.domain.voucher.model.Voucher;
import com.prgms.VoucherApp.domain.voucher.model.VoucherType;

import java.math.BigDecimal;
import java.util.UUID;

public record VoucherResponse(

    UUID voucherId,
    BigDecimal amount,
    VoucherType voucherType
) {
    public VoucherResponse(Voucher voucher) {
        this(voucher.getVoucherId(), voucher.getAmount(), voucher.getVoucherType());
    }
}
