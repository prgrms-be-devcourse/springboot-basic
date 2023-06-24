package me.kimihiqq.vouchermanagement.domain;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final String type;
    private final double discountRate;

    public PercentDiscountVoucher(UUID voucherId, double discountRate) {
        this.voucherId = voucherId;
        this.type = "Percent";
        this.discountRate = discountRate;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public long discount(long beforeDiscount) {
        return (long) (beforeDiscount * (1 - discountRate));
    }

}