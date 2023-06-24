package com.devcourse.voucherapp.entity;

import java.util.UUID;

public class FixDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final int discountPrice;

    public FixDiscountVoucher(UUID voucherId, int discountPrice) {
        this.voucherId = voucherId;
        this.discountPrice = discountPrice;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }
}
