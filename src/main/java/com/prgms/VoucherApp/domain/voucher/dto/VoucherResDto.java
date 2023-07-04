package com.prgms.VoucherApp.domain.voucher.dto;

import com.prgms.VoucherApp.domain.voucher.Voucher;
import com.prgms.VoucherApp.domain.voucher.VoucherType;

import java.math.BigDecimal;
import java.util.UUID;

public class VoucherResDto {

    private final UUID voucherId;
    private final BigDecimal discountAmount;
    private final VoucherType voucherType;

    public VoucherResDto(String voucherId, String discountAmount, String voucherTypeName) {
        this.voucherId = UUID.fromString(voucherId);
        this.discountAmount = BigDecimal.valueOf(Double.parseDouble(discountAmount));
        this.voucherType = VoucherType.findByVoucherTypeName(voucherTypeName);
    }

    public VoucherResDto(Voucher voucher) {
        this.voucherId = voucher.getVoucherId();
        this.discountAmount = voucher.getDiscountAmount();
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
