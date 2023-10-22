package com.programmers.springbootbasic.domain.voucher.domain.VoucherType;

import static com.programmers.springbootbasic.domain.voucher.domain.VoucherType.VoucherTypeEnum.FIXED;
import static com.programmers.springbootbasic.exception.ErrorCode.INVALID_FIXED_VOUCHER_BENEFIT;

import com.programmers.springbootbasic.exception.exceptionClass.VoucherException;

public class FixedAmountVoucher implements VoucherType{

    public FixedAmountVoucher(Integer benefit) {
        if (benefit < 0) {
            throw new VoucherException(INVALID_FIXED_VOUCHER_BENEFIT);
        }
    }

    @Override
    public String getVoucherTypeName() {
        return FIXED.name();
    }

    @Override
    public double getDiscountedPrice(double price, int benefitValue) {
        return price - benefitValue;
    }
}
