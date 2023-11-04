package com.programmers.springbootbasic.domain.voucher.domain.VoucherType;

import static com.programmers.springbootbasic.exception.ErrorCode.INVALID_VOUCHER;

import com.programmers.springbootbasic.exception.exceptionClass.VoucherException;
import java.util.function.Function;

public enum VoucherTypeEnum {
    FIXED(FixedAmountVoucher::new),
    PERCENT(PercentDiscountVoucher::new);

    private final Function<Integer, VoucherType> voucherType;

    VoucherTypeEnum(Function<Integer, VoucherType> voucherType) {
        this.voucherType = voucherType;
    }

    public VoucherType getVoucherType(Integer benefit) {
        return voucherType.apply(benefit);
    }

    public static VoucherTypeEnum of(String voucherType) {
        try {
            return VoucherTypeEnum.valueOf(voucherType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new VoucherException(INVALID_VOUCHER);
        }
    }
}
