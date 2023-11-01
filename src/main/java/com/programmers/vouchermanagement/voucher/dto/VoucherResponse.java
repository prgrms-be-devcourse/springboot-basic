package com.programmers.vouchermanagement.voucher.dto;

import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;

import java.util.UUID;

public record VoucherResponse(UUID voucherId, long discountValue, String voucherTypeName, boolean isPercent) {
    public static VoucherResponse from(Voucher voucher) {
        VoucherType voucherType = voucher.voucherType();
        return new VoucherResponse(voucher.voucherId(), voucher.discountValue(), voucherType.displayTypeName(), voucherType.isPercent());
    }
}
