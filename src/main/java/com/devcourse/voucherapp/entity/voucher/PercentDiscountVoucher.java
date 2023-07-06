package com.devcourse.voucherapp.entity.voucher;

import static java.text.MessageFormat.format;

import com.devcourse.voucherapp.exception.DiscountAmountException;
import java.util.UUID;
import lombok.Getter;

@Getter
public class PercentDiscountVoucher implements Voucher {

    private static final String PERCENT_DISCOUNT_RATE_REGEX = "^[1-9]|[1-9][0-9]|100$";

    private final UUID voucherId;
    private final String typeNumber;
    private final int discountAmount;

    public PercentDiscountVoucher(String typeNumber, String discountAmount) {
        this.voucherId = UUID.randomUUID();
        this.typeNumber = typeNumber;
        this.discountAmount = getValidRate(discountAmount);
    }

    public PercentDiscountVoucher(String voucherId, String typeNumber, int discountAmount) {
        this.voucherId = UUID.fromString(voucherId);
        this.typeNumber = typeNumber;
        this.discountAmount = discountAmount;
    }

    @Override
    public String toString() {
        return format("{0} | 비율 할인 | {1}%", voucherId, discountAmount);
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
