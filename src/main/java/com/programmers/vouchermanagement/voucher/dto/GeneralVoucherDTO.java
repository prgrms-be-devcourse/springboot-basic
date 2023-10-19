package com.programmers.vouchermanagement.voucher.dto;

import com.programmers.vouchermanagement.voucher.domain.VoucherType;

import java.util.UUID;

public class GeneralVoucherDTO {
    private final UUID voucherId;
    private final long discountValue;
    private final VoucherType voucherType;

    public GeneralVoucherDTO(UUID voucherId, long discountValue, VoucherType voucherType) {
        this.voucherId = voucherId;
        this.discountValue = discountValue;
        this.voucherType = voucherType;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public long getDiscountValue() {
        return discountValue;
    }

    public String getVoucherType() {
        return voucherType.name().toLowerCase();
    }

    public String getVoucherTypeName() {
        return voucherType.displayTypeName();
    }
}
