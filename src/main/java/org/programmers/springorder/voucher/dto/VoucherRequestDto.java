package org.programmers.springorder.voucher.dto;

import org.programmers.springorder.exception.ErrorCode;
import org.programmers.springorder.exception.VoucherException;
import org.programmers.springorder.voucher.model.VoucherType;
import org.springframework.lang.NonNull;

public record VoucherRequestDto(
        @NonNull long discountValue,
        @NonNull VoucherType voucherType) {
    public VoucherRequestDto(long discountValue, VoucherType voucherType) {
        validatingVoucherInfo(discountValue, voucherType);
        this.discountValue = discountValue;
        this.voucherType = voucherType;
    }

    private static void validatingVoucherInfo(long discountValue, VoucherType voucherType) {
        if (voucherType == VoucherType.FIXED && (discountValue < 100 || discountValue > 10000)) {
            throw new VoucherException(ErrorCode.INVALID_DISCOUNT_VALUE);
        }
        if (voucherType == VoucherType.PERCENT && (discountValue < 3 || discountValue > 50)) {
            throw new VoucherException(ErrorCode.INVALID_DISCOUNT_PERCENT);
        }
    }
}
