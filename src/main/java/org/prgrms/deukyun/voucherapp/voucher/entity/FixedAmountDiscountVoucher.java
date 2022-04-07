package org.prgrms.deukyun.voucherapp.voucher.entity;

import java.util.UUID;

/**
 * 정액 할인 바우처
 */
public class FixedAmountDiscountVoucher implements Voucher {

    private final UUID id;
    public final long amount;

    public FixedAmountDiscountVoucher(long amount) {
        this.id = UUID.randomUUID();
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return id;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

}
