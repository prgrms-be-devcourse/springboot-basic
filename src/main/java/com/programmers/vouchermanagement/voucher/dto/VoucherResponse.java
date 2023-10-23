package com.programmers.vouchermanagement.voucher.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;

public record VoucherResponse(UUID voucherId, BigDecimal discountValue, VoucherType voucherType) {
    public static VoucherResponse from(Voucher voucher) {
        return new VoucherResponse(voucher.getVoucherId(), voucher.getDiscountValue(), voucher.getVoucherType());
    }
}
