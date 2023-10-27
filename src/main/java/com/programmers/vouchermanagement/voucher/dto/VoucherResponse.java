package com.programmers.vouchermanagement.voucher.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;

public class VoucherResponse {
    private final UUID voucherId;
    private final BigDecimal discountValue;
    private final VoucherType voucherType;
    private final UUID customerId;

    private VoucherResponse(UUID voucherId, BigDecimal discountValue, VoucherType voucherType, UUID customerId) {
        this.voucherId = voucherId;
        this.discountValue = discountValue;
        this.voucherType = voucherType;
        this.customerId = customerId;
    }

    public static VoucherResponse from(Voucher voucher) {
        return new VoucherResponse(voucher.getVoucherId(), voucher.getDiscountValue(), voucher.getVoucherType(), voucher.getCustomerId());
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public boolean isPercentVoucher() {
        return voucherType.isPercent();
    }

    public String getVoucherTypeName() {
        return voucherType.displayTypeName();
    }
}
