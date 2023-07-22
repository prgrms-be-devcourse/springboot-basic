package com.devcourse.voucherapp.entity.voucher;

import com.devcourse.voucherapp.exception.ExceptionRule;
import com.devcourse.voucherapp.exception.VoucherException;
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
        if (!discountAmount.matches(FIX_DISCOUNT_PRICE_REGEX)) {
            throw new VoucherException(ExceptionRule.VOUCHER_DISCOUNT_AMOUNT_INVALID, discountAmount);
        }

        return Integer.parseInt(discountAmount);
    }
}
