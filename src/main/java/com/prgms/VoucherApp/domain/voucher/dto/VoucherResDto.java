package com.prgms.VoucherApp.domain.voucher.dto;

import com.prgms.VoucherApp.domain.voucher.model.Voucher;
import com.prgms.VoucherApp.domain.voucher.model.VoucherType;

import java.math.BigDecimal;
import java.util.UUID;

public class VoucherResDto {

    private final UUID voucherId;
    private final BigDecimal amount;
    private final VoucherType voucherType;


    public VoucherResDto(Voucher voucher) {
        this.voucherId = voucher.getVoucherId();
        this.amount = voucher.getAmount();
        this.voucherType = voucher.getVoucherType();
    }

    public UUID getVoucherId() {
        return this.voucherId;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public VoucherType getVoucherType() {
        return this.voucherType;
    }

    public String getVoucherInfo() {
        String voucherInfo = switch (this.voucherType) {
            case FIXED_VOUCHER -> "Fixed Voucher, Discount Amount: " + this.amount;
            case PERCENT_VOUCHER -> "Percent Voucher, Discount percent Amount: " + this.amount;
        };

        return voucherInfo;
    }
}
