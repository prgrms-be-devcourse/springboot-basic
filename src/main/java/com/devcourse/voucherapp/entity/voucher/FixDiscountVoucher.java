package com.devcourse.voucherapp.entity.voucher;

import static java.text.MessageFormat.format;

import com.devcourse.voucherapp.exception.DiscountAmountException;
import java.util.UUID;
import lombok.Getter;

@Getter
public class FixDiscountVoucher implements Voucher {

    private static final String FIX_DISCOUNT_PRICE_REGEX = "^[1-9][0-9]*$";

    private final UUID voucherId;
    private final String typeNumber;
    private final int discountAmount;

    public FixDiscountVoucher(String typeNumber, String discountAmount) {
        this.voucherId = UUID.randomUUID();
        this.typeNumber = typeNumber;
        this.discountAmount = getValidPrice(discountAmount);
    }

    public FixDiscountVoucher(String voucherId, String typeNumber, int discountAmount) {
        this.voucherId = UUID.fromString(voucherId);
        this.typeNumber = typeNumber;
        this.discountAmount = discountAmount;
    }

    @Override
    public String toString() {
        return format("{0} | 고정 할인 | {1}원", voucherId, discountAmount);
    }

    private int getValidPrice(String discountAmount) {
        if (isNotValid(discountAmount)) {
            throw new DiscountAmountException(discountAmount);
        }

        return Integer.parseInt(discountAmount);
    }

    private boolean isNotValid(String discountAmount) {
        return !discountAmount.matches(FIX_DISCOUNT_PRICE_REGEX);
    }
}
