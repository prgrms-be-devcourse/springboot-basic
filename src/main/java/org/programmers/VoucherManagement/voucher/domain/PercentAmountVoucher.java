package org.programmers.VoucherManagement.voucher.domain;

import lombok.EqualsAndHashCode;
import org.programmers.VoucherManagement.voucher.exception.VoucherException;

import java.util.UUID;

import static org.programmers.VoucherManagement.voucher.exception.VoucherExceptionMessage.NOT_INCLUDE_1_TO_100;

@EqualsAndHashCode(callSuper = false)
public class PercentAmountVoucher extends Voucher {
    public PercentAmountVoucher(UUID voucherId, DiscountType discountType, DiscountValue discountValue) {
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
