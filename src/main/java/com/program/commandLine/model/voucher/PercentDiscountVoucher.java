package com.program.commandLine.model.voucher;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private static final long MAX_VOUCHER_PERCENT = 90;

    private final UUID voucherID;
    private final int percent;
    private boolean used = false;


    public PercentDiscountVoucher(UUID voucherID, int percent) {
        if (percent < 0) throw new IllegalArgumentException("! Percent should be positive");
        if (percent == 0) throw new IllegalArgumentException("! Percent should not be zero");
        if (percent > MAX_VOUCHER_PERCENT)
            throw new IllegalArgumentException("! Percent should be less than %d".formatted(MAX_VOUCHER_PERCENT));
        this.voucherID = voucherID;
        this.percent = percent;
    }

    public PercentDiscountVoucher(UUID voucherID, int percent, boolean used) {
        this.voucherID = voucherID;
        this.percent = percent;
        this.used = used;
    }

    @Override
    public UUID getVoucherId() {
        return voucherID;
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.PERCENT_DISCOUNT;
    }

    @Override
    public int getVoucherDiscount() {
        return percent;
    }


    @Override
    public boolean getUsed() {
        return used;
    }

    @Override
    public int discountPrice(int beforeDiscount) {
        return (int) (beforeDiscount * (1 - (double) percent / 100));
    }

    @Override
    public void used() {
        if (used) throw new IllegalArgumentException("! 이미 사용된 바우처 입니다.");
        used = true;
    }
}
