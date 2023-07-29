package org.programmers.VoucherManagement.voucher.domain;

import org.programmers.VoucherManagement.voucher.exception.VoucherException;

import static org.programmers.VoucherManagement.global.response.ErrorCode.NOT_INCLUDE_1_TO_100;

public class PercentAmountVoucher extends Voucher {
    public PercentAmountVoucher(String voucherId, DiscountType discountType, DiscountValue discountValue) {
        validatePercentDiscountValue(discountValue);
        this.voucherId = voucherId;
        this.discountType = discountType;
        this.discountValue = discountValue;
    }

    private void validatePercentDiscountValue(DiscountValue discountValue) {
        if (discountValue.getValue() < 0 || discountValue.getValue() > 100) {
            throw new VoucherException(NOT_INCLUDE_1_TO_100);
        }
    }
}
