package org.prgms.controller.dto;

import org.prgms.domain.FixedAmountVoucher;
import org.prgms.domain.PercentDiscountVoucher;
import org.prgms.domain.Voucher;
import org.prgms.domain.VoucherType;

public record CreateVoucherRequest(String voucherKind, long discountAmount) {

    public Voucher toVoucher() {
        if (this.voucherKind.equals(VoucherType.FIXED.name())) {
            return new FixedAmountVoucher(discountAmount);
        }

        return new PercentDiscountVoucher(discountAmount);
    }
}
