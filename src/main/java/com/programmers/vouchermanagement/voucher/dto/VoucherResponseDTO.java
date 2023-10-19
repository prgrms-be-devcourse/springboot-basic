package com.programmers.vouchermanagement.voucher.dto;

import java.util.UUID;

import com.programmers.vouchermanagement.voucher.domain.VoucherType;

public class VoucherResponseDTO {
    private final UUID voucherId;
    private final long discountValue;
    private final String voucherType;

    public VoucherResponseDTO(UUID voucherId, long discountValue, VoucherType voucherType) {
        this.voucherId = voucherId;
        this.discountValue = discountValue;
        this.voucherType = voucherType.displayTypeName();
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public long getDiscountValue() {
        return discountValue;
    }

    public String getVoucherType() {
        return voucherType;
    }
}
