package org.devcourse.voucher.stub;

import org.devcourse.voucher.application.voucher.model.FixedAmountVoucher;
import org.devcourse.voucher.application.voucher.model.PercentDiscountVoucher;
import org.devcourse.voucher.application.voucher.model.Voucher;

import java.util.List;
import java.util.UUID;

public class VoucherStubs {
    public static List<Voucher> voucherList() {
        return List.of(
                new FixedAmountVoucher(UUID.randomUUID(), 1000),
                new FixedAmountVoucher(UUID.randomUUID(), 2000),
                new PercentDiscountVoucher(UUID.randomUUID(), 30),
                new PercentDiscountVoucher(UUID.randomUUID(), 60),
                new FixedAmountVoucher(UUID.randomUUID(), 10000)
        );
    }

    public static Voucher fixedAmountVoucher(UUID voucherId) {
        return new FixedAmountVoucher(voucherId, 10000);
    }
}
