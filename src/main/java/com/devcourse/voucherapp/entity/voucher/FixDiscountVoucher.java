package com.devcourse.voucherapp.entity.voucher;

import static java.text.MessageFormat.format;

import com.devcourse.voucherapp.exception.DiscountAmountException;
import java.util.UUID;
import lombok.Getter;

public class FixDiscountVoucher implements Voucher {

    private static final String FIX_DISCOUNT_PRICE_REGEX = "^[1-9][0-9]*$";

    @Getter
    private final UUID voucherId;

    private final int discountPrice;

    public FixDiscountVoucher(String discountPrice) {
        this.voucherId = UUID.randomUUID();
        this.discountPrice = getValidPrice(discountPrice);
    }

    @Override
    public String toString() {
        return format("{0} | 고정 할인 | {1}원", voucherId, discountPrice);
    }

    private int getValidPrice(String discountPrice) {
        if (isNotValid(discountPrice)) {
            throw new DiscountAmountException(discountPrice);
        }

        return Integer.parseInt(discountPrice);
    }

    private boolean isNotValid(String discountPrice) {
        return !discountPrice.matches(FIX_DISCOUNT_PRICE_REGEX);
    }
}
