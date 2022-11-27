package com.example.springbootbasic.domain.voucher;

import static com.example.springbootbasic.exception.voucher.VoucherExceptionMessage.*;

public abstract class Voucher {

    private final Long voucherId;
    private final Long voucherDiscountValue;
    private final VoucherType voucherType;

    protected Voucher(Long voucherId, Long voucherDiscountValue, VoucherType voucherType) {
        validateVoucherId(voucherId);
        validateDiscountValue(voucherDiscountValue);
        validateVoucherType(voucherType);
        this.voucherId = voucherId;
        this.voucherDiscountValue = voucherDiscountValue;
        this.voucherType = voucherType;
    }

    private void validateVoucherType(VoucherType voucherType) {
        if (voucherType == null) {
            throw new IllegalArgumentException(WRONG_VOUCHER_ID_EXCEPTION.getMessage());
        }
    }

    private void validateDiscountValue(Long voucherDiscountValue) {
        if (voucherDiscountValue <= 0) {
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

    public Long getVoucherDiscountValue() {
        return voucherDiscountValue;
    }

    public VoucherType getVoucherType() { return voucherType; }

    @Override
    public String toString() {
        return "Voucher{" +
                "voucherId=" + voucherId +
                ", voucherDiscountValue=" + voucherDiscountValue +
                ", voucherType=" + voucherType +
                '}';
    }
}
