package com.programmers.springweekly.dto;

import java.util.UUID;

public class ReadVoucherDto {

    private final UUID voucherId;
    private final long discountAmount;
    private final String voucherType;

    public ReadVoucherDto(String voucherId, String discountAmount, String voucherType) {
        this.voucherId = UUID.fromString(voucherId);
        this.discountAmount = Long.parseLong(discountAmount);
        this.voucherType = voucherType;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public long getDiscountAmount() {
        return discountAmount;
    }

    public String getVoucherType() {
        return voucherType;
    }
}
