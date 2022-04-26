package org.prgms.voucher.controller;

import org.prgms.voucher.domain.FixedAmountVoucher;
import org.prgms.voucher.domain.PercentDiscountVoucher;
import org.prgms.voucher.domain.Voucher;

public record CreateVoucherRequest(int voucherKind, long discountAmount) {

    public Voucher toVoucher() {
        if (this.voucherKind == 1) {
            return new FixedAmountVoucher(discountAmount);
        } else {
            return new PercentDiscountVoucher(discountAmount);
        }
    }
}
