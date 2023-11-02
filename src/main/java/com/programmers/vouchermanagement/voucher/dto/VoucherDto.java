package com.programmers.vouchermanagement.voucher.dto;

import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;

import java.util.UUID;

public record VoucherDto(UUID voucherId, long discountValue, String voucherTypeName, boolean isPercent) {
    public static VoucherDto from(Voucher voucher) {
        VoucherType voucherType = voucher.voucherType();
        return new VoucherDto(voucher.voucherId(), voucher.discountValue(), voucherType.displayTypeName(), voucherType.isPercent());
    }
}
