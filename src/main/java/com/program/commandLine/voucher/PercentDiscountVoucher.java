package com.program.commandLine.voucher;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private static final long MAX_VOUCHER_PERCENT = 90;
    private static final String VOUCHER_TYPE = "Percent_discount";
    private final UUID voucherID;
    private final int percent;

    public PercentDiscountVoucher(UUID voucherID, int percent) {
        if (percent < 0) throw new IllegalArgumentException("! Percent should be positive");
        if (percent == 0) throw new IllegalArgumentException("! Percent should not be zero");
        if (percent > MAX_VOUCHER_PERCENT)
            throw new IllegalArgumentException("! Percent should be less than %d".formatted(MAX_VOUCHER_PERCENT));
        this.voucherID = voucherID;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherID;
    }

    @Override
    public String getVoucherType() {
        return VOUCHER_TYPE;
    }

    @Override
    public int getVoucherDiscount() {
        return percent;
    }

    @Override
    public int discountPrice(int beforeDiscount) {
        return (int)(beforeDiscount * (1 - (double)percent / 100));
    }
}
