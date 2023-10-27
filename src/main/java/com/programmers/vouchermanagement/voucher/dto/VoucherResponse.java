package com.programmers.vouchermanagement.voucher.dto;

import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;

import java.util.UUID;

public class VoucherResponse {
    private final UUID voucherId;
    private final long discountValue;
    private final VoucherType voucherType;

    private VoucherResponse(UUID voucherId, long discountValue, VoucherType voucherType) {
        this.voucherId = voucherId;
        this.discountValue = discountValue;
        this.voucherType = voucherType;
    }

    public static VoucherResponse from(Voucher voucher) {
        return new VoucherResponse(voucher.voucherId(), voucher.discountValue(), voucher.voucherType());
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public long getDiscountValue() {
        return discountValue;
    }

    public boolean isPercentVoucher() {
        return voucherType.isPercent();
    }

    public String getVoucherTypeName() {
        return voucherType.displayTypeName();
    }
}
