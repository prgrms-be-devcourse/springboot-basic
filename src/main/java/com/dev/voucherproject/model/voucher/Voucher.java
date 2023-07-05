package com.dev.voucherproject.model.voucher;

import java.util.UUID;

public abstract class Voucher {
    private UUID voucherId;
    private VoucherPolicy voucherPolicy;

    public Voucher(UUID voucherId, VoucherPolicy voucherPolicy) {
        this.voucherId = voucherId;
        this.voucherPolicy = voucherPolicy;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public VoucherPolicy getVoucherPolicy() {
        return voucherPolicy;
    }

    public abstract long discount(long beforeDiscount);

    public abstract long getDiscountFigure();

    public static Voucher of(UUID uuid, VoucherPolicy voucherPolicy, long discountFigure) {
        if (voucherPolicy == VoucherPolicy.FIXED_AMOUNT_VOUCHER) {
            return new FixedAmountVoucher(uuid, voucherPolicy, discountFigure);
        }

        return new PercentDiscountVoucher(uuid, voucherPolicy, discountFigure);
    }
}
