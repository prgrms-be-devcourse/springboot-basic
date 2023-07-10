package me.kimihiqq.vouchermanagement.domain.voucher;

import lombok.extern.slf4j.Slf4j;
import me.kimihiqq.vouchermanagement.config.exception.InvalidDiscountAmountException;
import me.kimihiqq.vouchermanagement.option.VoucherTypeOption;

import java.util.UUID;

@Slf4j
public class FixedAmountVoucher extends Voucher {

    public FixedAmountVoucher(UUID voucherId, long discountAmount) {
        super(voucherId, VoucherTypeOption.FIXED, discountAmount);
        validateDiscountAmount(discountAmount);
    }

    private void validateDiscountAmount(long discountAmount) {
        if (discountAmount <= 0) {
            throw new InvalidDiscountAmountException("Discount amount must be positive.");
        }
    }

    @Override
    public long discount(long beforeDiscount) {
        long discountedPrice = Math.max(0, beforeDiscount - getDiscount());
        log.info("Discount applied: {} - Price before discount: {}, Price after discount: {}", getDiscount(), beforeDiscount, discountedPrice);
        return discountedPrice;
    }
}