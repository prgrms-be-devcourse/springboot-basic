package org.prgrms.kdt.voucher.domain;

import org.prgrms.kdt.common.exception.ExceptionMessage;
import org.prgrms.kdt.voucher.exception.InvalidVoucherTypeException;

import java.util.UUID;

public class VoucherFactory {
    public static Voucher createVoucher(VoucherType voucherType, UUID id, long discountValue){
        switch (voucherType){
            case FIXED_AMOUNT:
                return new FixedAmountVoucher(id, discountValue);
            case PERCENT_DISCOUNT:
                return new PercentDiscountVoucher(id, discountValue);
            default:
                throw new InvalidVoucherTypeException(ExceptionMessage.INVALID_VOUCHER_TYPE.getMessage());
        }
    }
}
