package org.prgrms.kdt.domain;

import org.prgrms.kdt.strategy.FixedAmountVoucher;
import org.prgrms.kdt.strategy.PercentDiscountVoucher;
import org.prgrms.kdt.strategy.Voucher;

import java.util.UUID;

public class VoucherMachine {

    private static final long DEFAULT_AMOUNT = 1000L;
    private static final long DEFAULT_PERCENT = 10L;

    public Voucher createVoucher(VoucherType voucherType) {
        if (voucherType == VoucherType.FIXED_AMOUNT) {
            return new FixedAmountVoucher(UUID.randomUUID(), DEFAULT_AMOUNT);
        } else {
            return new PercentDiscountVoucher(UUID.randomUUID(), DEFAULT_PERCENT);
        }
    }
}
