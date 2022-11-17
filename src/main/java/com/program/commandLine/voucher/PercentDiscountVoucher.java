package com.program.commandLine.voucher;

import java.util.Optional;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private static final long MAX_VOUCHER_PERCENT = 90;

    private final UUID voucherID;
    private final int percent;
    private UUID assignedCustomerId;
    private boolean using = false;


    public PercentDiscountVoucher(UUID voucherID, int percent) {
        if (percent < 0) throw new IllegalArgumentException("! Percent should be positive");
        if (percent == 0) throw new IllegalArgumentException("! Percent should not be zero");
        if (percent > MAX_VOUCHER_PERCENT)
            throw new IllegalArgumentException("! Percent should be less than %d".formatted(MAX_VOUCHER_PERCENT));
        this.voucherID = voucherID;
        this.percent = percent;
    }

    public PercentDiscountVoucher(UUID voucherID, int percent, UUID assignedCustomerId, boolean using) {
        this.voucherID = voucherID;
        this.percent = percent;
        this.assignedCustomerId = assignedCustomerId;
        this.using = using;
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
    public UUID getAssignedCustomerId() {
        return assignedCustomerId;
    }

    @Override
    public boolean getUsing() {
        return using;
    }

    @Override
    public int discountPrice(int beforeDiscount) {
        return (int)(beforeDiscount * (1 - (double)percent / 100));
    }
}
