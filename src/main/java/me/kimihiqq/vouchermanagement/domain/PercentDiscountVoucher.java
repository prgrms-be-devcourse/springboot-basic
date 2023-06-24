package me.kimihiqq.vouchermanagement.domain;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final String type;
    private final long discount;

    public PercentDiscountVoucher(UUID voucherId, String type, long discountRate) {
        this.voucherId = voucherId;
        this.type = "Percent";
        this.discount = discountRate;
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
    public long getDiscount() {return discount;}


    @Override
    public long discount(long beforeDiscount) {
        return (beforeDiscount * (discount / 100));
    }

}