package me.kimihiqq.vouchermanagement.domain.voucher;

import lombok.extern.slf4j.Slf4j;
import me.kimihiqq.vouchermanagement.option.VoucherTypeOption;

import java.util.UUID;

@Slf4j
public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final VoucherTypeOption type;
    private final long discount;


    public PercentDiscountVoucher(UUID voucherId, long discountRate) {
        validateDiscountRate(discountRate);
        this.voucherId = voucherId;
        this.type = VoucherTypeOption.PERCENT;
        this.discount = discountRate;
    }

    private void validateDiscountRate(long discountRate) {
        if (discountRate <= 0 || discountRate > 100) {
            throw new IllegalArgumentException("Discount rate must be between 0 and 100.");
        }
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public String getType() {
        return type.name();
    }

    @Override
    public long getDiscount() {
        return discount;
    }

    @Override
    public long discount(long beforeDiscount) {
        long discountedPrice = beforeDiscount * discount / 100;
        log.info("Discount applied: {}% - Before discount: {}, Discounted price: {}", discount, beforeDiscount, discountedPrice);
        return discountedPrice;
    }
}