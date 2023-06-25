package com.programmers.springweekly.dto;

import com.programmers.springweekly.domain.voucher.VoucherType;

import java.util.UUID;

public class ReadVoucherDto {

    private final UUID voucherId;
    private final String discountAmount;
    private final VoucherType voucherType;

    public ReadVoucherDto(String voucherId, String discountAmount, String voucherType) {
        this.voucherId = UUID.fromString(voucherId);
        this.discountAmount = discountAmount;
        this.voucherType = VoucherType.findVoucherMenu(voucherType);
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }
}
