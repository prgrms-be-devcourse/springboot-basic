package com.prgrms.voucher.model.discount;

import com.prgrms.voucher.model.VoucherType;
import org.springframework.stereotype.Component;

@Component
public class DiscountCreator {

    private DiscountCreator() { }

    public Discount createDiscount(VoucherType voucherType, double discountAmount) {

        return switch (voucherType) {
            case FIXED_AMOUNT_VOUCHER -> new FixedDiscount(discountAmount);
            case PERCENT_DISCOUNT_VOUCHER -> new PercentDiscount(discountAmount);
        };
    }
}
