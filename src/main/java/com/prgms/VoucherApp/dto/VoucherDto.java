package com.prgms.VoucherApp.dto;

import com.prgms.VoucherApp.domain.VoucherType;

import java.util.UUID;

public class VoucherDto {

    private final UUID voucherId;
    private final Long discountAmount;
    private final VoucherType voucherType;

    public VoucherDto(String voucherId, String discountAmount, String voucherType) {
        this.voucherId = UUID.fromString(voucherId);
        this.discountAmount = Long.parseLong(discountAmount);
        this.voucherType = VoucherType.findByPolicy(voucherType);
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public Long getDiscountAmount() {
        return discountAmount;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public String getVoucherInfo() {
        String voucherInfo = switch (this.voucherType) {
            case FIXED_VOUCHER -> "Fixed Voucher, Discount Amount: " + this.discountAmount;
            case PERCENT_VOUCHER -> "Percent Voucher, Discount percent Amount: " + this.discountAmount;
        };

        return voucherInfo;
    }
}
