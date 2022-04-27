package org.prgms.controller;

import org.prgms.domain.FixedAmountVoucher;
import org.prgms.domain.PercentDiscountVoucher;
import org.prgms.domain.Voucher;

import java.text.MessageFormat;

import static com.google.common.base.Preconditions.checkArgument;

public record CreateVoucherRequest(String voucherKind, long discountAmount) {

    public CreateVoucherRequest {
        checkArgument(voucherKind.equals("FixedAmountVoucher") || voucherKind.equals("PercentDiscountVoucher"),
                MessageFormat.format("voucher 종류는 두가지 밖에 없습니다. {0}", voucherKind));
    }

    public Voucher toVoucher() {
        if (this.voucherKind.equals("FixedAmountVoucher")) {
            return new FixedAmountVoucher(discountAmount);
        } else {
            return new PercentDiscountVoucher(discountAmount);
        }
    }
}
