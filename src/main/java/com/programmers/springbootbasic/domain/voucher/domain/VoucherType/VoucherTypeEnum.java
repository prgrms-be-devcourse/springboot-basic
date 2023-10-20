package com.programmers.springbootbasic.domain.voucher.domain.VoucherType;

import static com.programmers.springbootbasic.exception.ErrorCode.INVALID_VOUCHER;

import com.programmers.springbootbasic.exception.exceptionClass.CustomException;
import java.util.function.Function;
import java.util.function.Supplier;

public enum VoucherTypeEnum {
    FIXED(FixedAmountVoucher::new),
    PERCENT(PercentDiscountVoucher::new);

    private Function<Integer,VoucherType> voucherType;

    VoucherTypeEnum(Function<Integer,VoucherType> voucherType) {
        this.voucherType = voucherType;
    }

    public VoucherType getVoucherType(Integer benefit) {
        return voucherType.apply(benefit);
    }

    public static VoucherTypeEnum of(String voucherType) {
        try {
            return VoucherTypeEnum.valueOf(voucherType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomException(INVALID_VOUCHER);
        }
    }
}
