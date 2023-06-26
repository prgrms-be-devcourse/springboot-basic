package com.prgms.VoucherApp.dto;

import com.prgms.VoucherApp.domain.VoucherType;

import java.math.BigDecimal;
import java.util.UUID;

public class VoucherDto {

    private final UUID voucherId;
    private final BigDecimal discountAmount;
    private final VoucherType voucherType;

    public VoucherDto(String voucherId, String discountAmount, String voucherType) {
        this.voucherId = UUID.fromString(voucherId);
        this.discountAmount = BigDecimal.valueOf(Double.parseDouble(discountAmount));
        this.voucherType = VoucherType.findByPolicy(voucherType);
    }

    public UUID getVoucherId() {
        return this.voucherId;
    }

    public BigDecimal getDiscountAmount() {
        return this.discountAmount;
    }

    public VoucherType getVoucherType() {
        return this.voucherType;
    }

    public String getVoucherInfo() {
        String voucherInfo = switch (this.voucherType) {
            case FIXED_VOUCHER -> "Fixed Voucher, Discount Amount: " + this.discountAmount;
            case PERCENT_VOUCHER -> "Percent Voucher, Discount percent Amount: " + this.discountAmount;
        };

        return voucherInfo;
    }
}
