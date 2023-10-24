package com.programmers.vouchermanagement.voucher.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;

public class VoucherResponse {
    private final UUID voucherId;
    private final BigDecimal discountValue;
    private final VoucherType voucherType;

    private VoucherResponse(UUID voucherId, BigDecimal discountValue, VoucherType voucherType) {
        this.voucherId = voucherId;
        this.discountValue = discountValue;
        this.voucherType = voucherType;
    }

    public static VoucherResponse from(Voucher voucher) {
        return new VoucherResponse(voucher.getVoucherId(), voucher.getDiscountValue(), voucher.getVoucherType());
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public boolean isPercentVoucher() {
        return voucherType.isPercent();
    }

    public String getVoucherTypeName() {
        return voucherType.displayTypeName();
    }
}
