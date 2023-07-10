package me.kimihiqq.vouchermanagement.domain.voucher;

import lombok.extern.slf4j.Slf4j;
import me.kimihiqq.vouchermanagement.config.exception.InvalidDiscountRateException;
import me.kimihiqq.vouchermanagement.option.VoucherTypeOption;

import java.util.UUID;

@Slf4j
public class PercentDiscountVoucher extends Voucher {

    public PercentDiscountVoucher(UUID voucherId, long discountRate) {
        super(voucherId, VoucherTypeOption.PERCENT, discountRate);
    }

    @Override
    protected void validateDiscount(long discountRate) {
        if (discountRate <= 0 || discountRate > 100) {
            throw new InvalidDiscountRateException("Discount rate must be between 0 and 100.");
        }
    }

    @Override
    public long discount(long beforeDiscount) {
        long discountedPrice = beforeDiscount * getDiscount() / 100;
        log.info("Discount applied: {}% - Before discount: {}, Discounted price: {}", getDiscount(), beforeDiscount, discountedPrice);
        return discountedPrice;
    }
}