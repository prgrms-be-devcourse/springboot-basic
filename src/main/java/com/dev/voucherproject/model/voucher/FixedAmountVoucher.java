package com.dev.voucherproject.model.voucher;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }


    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    public long discount(long beforeDiscount) {
        if (amount > beforeDiscount) {
            return 0;
        }

        return beforeDiscount - amount;
    }

    @Override
    public long getDiscountNumber() {
        return this.amount;
    }

    @Override
    public VoucherDto conversionDto() {
        return VoucherDto.fromEntity(VoucherPolicy.FIXED_AMOUNT_VOUCHER, this);
    }
}
