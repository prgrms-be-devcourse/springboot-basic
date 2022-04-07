package org.prgrms.deukyun.voucherapp.voucher.entity;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * 정률 할인 바우처
 */
public class PercentDiscountVoucher implements Voucher {

    private final UUID id;
    public final long percent;

    public PercentDiscountVoucher(long percent) {
        checkArgument(percent >= 0 && percent <= 100, "amount must be between 0 and 100 inclusive.");

        this.id = UUID.randomUUID();
        this.percent = percent;
    }


    @Override
    public UUID getVoucherId() {
        return id;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (100 - percent) / 100;
    }
}
