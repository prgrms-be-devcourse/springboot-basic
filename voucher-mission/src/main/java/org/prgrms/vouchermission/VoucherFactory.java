package org.prgrms.vouchermission;

import java.time.LocalDate;

public enum VoucherFactory {

    PERCENT {
        private final static long TEMP_VOUCHER_ID = Long.MIN_VALUE;

        @Override
        public Voucher createVoucher(long voucherId, long percent, LocalDate createdDate, LocalDate expirationDate) {
            return new PercentDiscountVoucher(voucherId, percent, createdDate, expirationDate);
        }

        @Override
        public Voucher createTempVoucher(long percent, LocalDate createdDate, LocalDate expirationDate) {
            return new PercentDiscountVoucher(TEMP_VOUCHER_ID, percent, createdDate, expirationDate);
        }

    },
    AMOUNT {
        private final static long TEMP_VOUCHER_ID = Long.MIN_VALUE;

        @Override
        public Voucher createVoucher(long voucherId, long amount, LocalDate createdDate, LocalDate expirationDate) {
            return new FixedAmountVoucher(voucherId, amount, createdDate, expirationDate);
        }

        @Override
        public Voucher createTempVoucher(long amount, LocalDate createdDate, LocalDate expirationDate) {
            return new PercentDiscountVoucher(TEMP_VOUCHER_ID, amount, createdDate, expirationDate);
        }
    };

    public abstract Voucher createVoucher(long voucherId, long value, LocalDate createdDate, LocalDate expirationDate);
    public abstract Voucher createTempVoucher(long value, LocalDate createdDate, LocalDate expirationDate);
}

