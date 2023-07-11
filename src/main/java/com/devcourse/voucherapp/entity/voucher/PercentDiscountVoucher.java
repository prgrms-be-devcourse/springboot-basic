package com.devcourse.voucherapp.entity.voucher;

import com.devcourse.voucherapp.utils.exception.voucher.DiscountAmountException;
import java.util.UUID;
import lombok.Getter;

@Getter
public class PercentDiscountVoucher implements Voucher {

    private static final String PERCENT_DISCOUNT_RATE_REGEX = "^[1-9]|[1-9][0-9]|100$";

    private final UUID id;
    private final VoucherType type;
    private final int discountAmount;

    public PercentDiscountVoucher(UUID id, VoucherType type, String discountAmount) {
        this.id = id;
        this.type = type;
        this.discountAmount = getValidRate(discountAmount);
    }

    private int getValidRate(String discountAmount) {
        if (isNotValid(discountAmount)) {
            throw new DiscountAmountException(discountAmount);
        }

        return Integer.parseInt(discountAmount);
    }

    private boolean isNotValid(String discountAmount) {
        return !discountAmount.matches(PERCENT_DISCOUNT_RATE_REGEX);
    }
}
