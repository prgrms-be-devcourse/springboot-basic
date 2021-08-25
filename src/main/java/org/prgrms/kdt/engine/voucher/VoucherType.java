package org.prgrms.kdt.engine.voucher;

import org.prgrms.kdt.engine.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.engine.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdt.engine.voucher.domain.Voucher;

import java.util.UUID;

public enum VoucherType {
    FIXED {
        @Override
        public Voucher createVoucher(long amount) {
            return new FixedAmountVoucher(UUID.randomUUID(), amount);
        }
    },
    PERCENT {
        @Override
        public Voucher createVoucher(long percent) {
            return new PercentDiscountVoucher(UUID.randomUUID(), percent);
        }
    };

    public abstract Voucher createVoucher(long rate);
}
