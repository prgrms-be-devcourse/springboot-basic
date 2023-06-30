package org.prgrms.vouchermission;

import java.time.LocalDate;

public enum VoucherFactory {

    PERCENT {
        @Override
        public Voucher createVoucher(long percent, LocalDate createdDate, LocalDate expirationDate) {
            return new PercentDiscountVoucher(percent, createdDate, expirationDate);
        }
    },
    AMOUNT {
        @Override
        public Voucher createVoucher(long amount, LocalDate createdDate, LocalDate expirationDate) {
            return new FixedAmountVoucher(amount, createdDate, expirationDate);
        }
    };

    public abstract Voucher createVoucher(long value, LocalDate createdDate, LocalDate expirationDate);
}
