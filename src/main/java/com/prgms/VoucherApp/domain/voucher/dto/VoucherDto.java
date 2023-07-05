package com.prgms.VoucherApp.domain.voucher.dto;

import com.prgms.VoucherApp.domain.voucher.Voucher;
import com.prgms.VoucherApp.domain.voucher.VoucherType;

import java.math.BigDecimal;
import java.util.UUID;

public class VoucherDto {

    private final UUID voucherId;
    private final BigDecimal discountAmount;
    private final VoucherType voucherType;

    public VoucherDto(UUID voucherId, BigDecimal discountAmount, VoucherType voucherType) {
        this.voucherId = voucherId;
        this.discountAmount = discountAmount;
        this.voucherType = voucherType;
    }

    public VoucherDto(Voucher voucher) {
        this.voucherId = voucher.getVoucherId();
        this.discountAmount = voucher.getAmount();
        this.voucherType = voucher.getVoucherType();
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
