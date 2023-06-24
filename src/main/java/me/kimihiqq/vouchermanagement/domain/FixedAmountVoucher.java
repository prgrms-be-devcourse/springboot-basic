package me.kimihiqq.vouchermanagement.domain;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private final String type;
    private final long discount;

    public FixedAmountVoucher(UUID voucherId,String type, long discountAmount) {
        this.voucherId = voucherId;
        this.type = "Fixed";
        this.discount = discountAmount;
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
    public long getDiscount() { return discount;}

    @Override
    public long discount(long beforeDiscount) {
        return Math.max(0, beforeDiscount - discount);
    }
}
