package com.devcourse.voucherapp.entity.voucher;

import static java.text.MessageFormat.format;

import com.devcourse.voucherapp.exception.VoucherInputException;
import java.util.UUID;
import lombok.Getter;

public class PercentDiscountVoucher implements Voucher {

    private static final String PERCENT_DISCOUNT_RATE_REGEX = "^[1-9]|[1-9][0-9]|100$";
    private static final String INVALID_PERCENT_DISCOUNT_RATE_MESSAGE = "입력하신 퍼센트가 조건에 맞지 않습니다.";

    @Getter
    private final UUID voucherId;

    private final int discountRate;

    public PercentDiscountVoucher(String discountRate) {
        this.voucherId = UUID.randomUUID();
        this.discountRate = getValidRate(discountRate);
    }

    @Override
    public String toString() {
        return format("{0} | 비율 할인 | {1}%", voucherId, discountRate);
    }

    private int getValidRate(String discountRate) {
        if (isNotValid(discountRate)) {
            throw new VoucherInputException(INVALID_PERCENT_DISCOUNT_RATE_MESSAGE);
        }

        return Integer.parseInt(discountRate);
    }

    private boolean isNotValid(String discountRate) {
        return !discountRate.matches(PERCENT_DISCOUNT_RATE_REGEX);
    }
}
