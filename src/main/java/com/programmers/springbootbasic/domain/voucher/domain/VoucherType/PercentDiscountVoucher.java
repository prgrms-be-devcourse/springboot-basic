package com.programmers.springbootbasic.domain.voucher.domain.VoucherType;

import static com.programmers.springbootbasic.domain.voucher.domain.VoucherType.VoucherTypeEnum.PERCENT;
import static com.programmers.springbootbasic.exception.ErrorCode.INVALID_PERCENT_VOUCHER_BENEFIT;

import com.programmers.springbootbasic.exception.exceptionClass.VoucherException;

public class PercentDiscountVoucher implements VoucherType{

    public PercentDiscountVoucher(Integer benefit) {
        if (benefit < 0 || benefit > 100) {
            throw new VoucherException(INVALID_PERCENT_VOUCHER_BENEFIT);
        }
    }

    @Override
    public String getVoucherTypeName() {
        return PERCENT.name();
    }

    @Override
    public double getDiscountedPrice(double price, int benefitValue) {
        return price * (100 - benefitValue) / 100;
    }
}
