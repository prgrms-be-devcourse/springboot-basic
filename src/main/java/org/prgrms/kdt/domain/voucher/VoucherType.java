package org.prgrms.kdt.domain.voucher;

import java.util.UUID;

public enum VoucherType {

    FIXED_AMOUNT {
        @Override
        public Voucher of() {
            return new FixedAmountVoucher(UUID.randomUUID(), DEFAULT_AMOUNT);
        }
    },
    PERCENT_DISCOUNT {
        @Override
        public Voucher of() {
            return new PercentDiscountVoucher(UUID.randomUUID(), DEFAULT_PERCENT);
        }
    };

    private static final long DEFAULT_AMOUNT = 1000L;
    private static final long DEFAULT_PERCENT = 10L;

    public abstract Voucher of();
}
