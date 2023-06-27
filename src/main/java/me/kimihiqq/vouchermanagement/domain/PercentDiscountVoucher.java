package me.kimihiqq.vouchermanagement.domain;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final String type;
    private final long discount;


    public PercentDiscountVoucher(UUID voucherId, String type, long discountRate) {
        if (discountRate < 0 || discountRate > 100) {
            throw new IllegalArgumentException("Discount rate must be between 0 and 100.");
        }
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
        long discountedPrice = beforeDiscount * discount / 100;
        log.info("Discount applied: {}% - Before discount: {}, Discounted price: {}", discount, beforeDiscount, discountedPrice);
        return discountedPrice;
    }
}