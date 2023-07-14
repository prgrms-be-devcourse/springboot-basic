package com.devcourse.voucherapp.entity.voucher;

import static java.text.MessageFormat.format;

import com.devcourse.voucherapp.entity.VoucherType;
import com.devcourse.voucherapp.exception.DiscountAmountException;
import java.util.UUID;
import lombok.Getter;

@Getter
public class FixDiscountVoucher implements Voucher {

    private static final String FIX_DISCOUNT_PRICE_REGEX = "^[1-9][0-9]*$";

    private final UUID id;
    private final VoucherType type;
    private final int discountAmount;

    public FixDiscountVoucher(UUID id, VoucherType type, String discountAmount) {
        this.id = id;
        this.type = type;
        this.discountAmount = getValidPrice(discountAmount);
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
