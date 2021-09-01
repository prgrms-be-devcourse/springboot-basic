package org.prgrms.kdt.enumType;


import org.prgrms.kdt.domain.FixedAmountVoucher;
import org.prgrms.kdt.domain.PercentDiscountVoucher;
import org.prgrms.kdt.domain.Voucher;

import java.util.UUID;

public enum VoucherStatus{
    PERCENT{
        @Override
        public Voucher createVoucher(long discount) {
            return new PercentDiscountVoucher(UUID.randomUUID(), discount);
        }
    },
    FIXED{
        @Override
        public Voucher createVoucher(long discount) {
            return new FixedAmountVoucher(UUID.randomUUID(), discount);
        }
    };

    public abstract Voucher createVoucher(long discount);
}
