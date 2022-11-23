package com.example.springbootbasic.domain.voucher;

import static com.example.springbootbasic.exception.voucher.VoucherExceptionMessage.*;

public abstract class Voucher {

    private final Long voucherId;
    private final Long discountValue;
    private final VoucherType voucherType;

    Voucher(Long voucherId, Long discountValue, VoucherType voucherType) {
        validateVoucherId(voucherId);
        validateDiscountValue(discountValue);
        validateVoucherType(voucherType);
        this.voucherId = voucherId;
        this.discountValue = discountValue;
        this.voucherType = voucherType;
    }

    private void validateVoucherType(VoucherType voucherType) {
        if (voucherType == null) {
            throw new IllegalArgumentException(WRONG_VOUCHER_ID_EXCEPTION.getMessage());
        }
    }

    private void validateDiscountValue(Long discountValue) {
        if (discountValue <= 0) {
            throw new IllegalArgumentException(WRONG_VOUCHER_DISCOUNT_VALUE_EXCEPTION.getMessage());
        }

    }

    private void validateVoucherId(Long voucherId) {
        if (voucherId < 0) {
            throw new IllegalArgumentException(WRONG_VOUCHER_TYPE_EXCEPTION.getMessage());
        }
    }

    public Long getVoucherId() {
        return voucherId;
    }

    public Long getDiscountValue() {
        return discountValue;
    }

    public VoucherType getVoucherType() { return voucherType; }

    @Override
    public String toString() {
        return "Voucher{" +
                "voucherId=" + voucherId +
                ", discountValue=" + discountValue +
                ", voucherType=" + voucherType +
                '}';
    }
}
