package com.prgms.VoucherApp.dto;

import com.prgms.VoucherApp.domain.VoucherPolicy;

import java.util.UUID;

public class VoucherDto {

    private final UUID voucherId;
    private final Long discountAmount;
    private final VoucherPolicy voucherType;

    public VoucherDto(String voucherId, String discountAmount, String voucherType) {
        this.voucherId = UUID.fromString(voucherId);
        this.discountAmount = Long.parseLong(discountAmount);
        this.voucherType = VoucherPolicy.findByPolicy(voucherType);
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public Long getDiscountAmount() {
        return discountAmount;
    }

    public VoucherPolicy getVoucherType() {
        return voucherType;
    }
}
