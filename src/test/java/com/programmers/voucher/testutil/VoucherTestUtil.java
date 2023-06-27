package com.programmers.voucher.testutil;

import com.programmers.voucher.domain.FixedAmountVoucher;
import com.programmers.voucher.domain.PercentDiscountVoucher;
import com.programmers.voucher.domain.Voucher;

import java.util.UUID;

public class VoucherTestUtil {
    public static Voucher createFixedVoucher(UUID voucherId, long amount) {
        return new FixedAmountVoucher(voucherId, amount);
    }

    public static Voucher createPercentVoucher(UUID voucherId, long amount) {
        return new PercentDiscountVoucher(voucherId, amount);
    }
}
