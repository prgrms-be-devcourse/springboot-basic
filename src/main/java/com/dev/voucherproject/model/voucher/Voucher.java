package com.dev.voucherproject.model.voucher;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    long discount(long beforeDiscount);

    long getDiscountNumber();

    static Voucher of(VoucherPolicy voucherPolicy, UUID uuid, long discountNumber) {
        if (voucherPolicy == VoucherPolicy.FIXED_AMOUNT_VOUCHER) {
            return new FixedAmountVoucher(uuid, discountNumber);
        }
        return new PercentDiscountVoucher(uuid, discountNumber);
    }
}
