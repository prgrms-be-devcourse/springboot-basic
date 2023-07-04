package com.prgms.VoucherApp.domain.voucher.dto;

import com.prgms.VoucherApp.domain.voucher.Voucher;
import com.prgms.VoucherApp.domain.voucher.VoucherType;

import java.math.BigDecimal;
import java.util.UUID;

public class VoucherDto {

    private final UUID voucherId;
    private final VoucherType voucherType;
    private final BigDecimal discountAmount;

    public VoucherDto(UUID voucherId, VoucherType voucherType, BigDecimal discountAmount) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.discountAmount = discountAmount;
    }

    public VoucherDto(Voucher voucher) {
        this.voucherId = voucher.getVoucherId();
        this.voucherType = voucher.getVoucherType();
        this.discountAmount = voucher.getVoucherPolicy().getDiscountAmount();
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public String getVoucherInfo() {
        String voucherInfo = switch (voucherType) {
            case FIXED_VOUCHER -> "Fixed Voucher, Discount Amount: " + discountAmount;
            case PERCENT_VOUCHER -> "Percent Voucher, Discount percent Amount: " + discountAmount;
        };

        return voucherInfo;
    }
}
