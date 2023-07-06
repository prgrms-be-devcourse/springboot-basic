package com.prgrms.model.voucher.discount;

import com.prgrms.model.voucher.VoucherType;

public class DiscountCreator {
        public Discount createDiscount(double value, VoucherType voucherType) {

            return switch (voucherType) {
                case FIXED_AMOUNT_VOUCHER -> new FixedDiscount(value);
                case PERCENT_DISCOUNT_VOUCHER -> new PercentDiscount(value);
            };
        }
}
