package org.prgrms.kdt.domain.voucher;

import org.apache.commons.math3.random.RandomDataGenerator;

public enum VoucherType {

    FIXED_AMOUNT {
        @Override
        public Voucher of() {
            return new FixedAmountVoucher(new RandomDataGenerator().nextLong(0, 10000), FIXED_AMOUNT, DEFAULT_AMOUNT);
        }
    },
    PERCENT_DISCOUNT {
        @Override
        public Voucher of() {
            return new PercentDiscountVoucher(new RandomDataGenerator().nextLong(0, 10000), PERCENT_DISCOUNT, DEFAULT_PERCENT);
        }
    };

    private static final long DEFAULT_AMOUNT = 1000L;
    private static final long DEFAULT_PERCENT = 10L;

    public abstract Voucher of();
}
