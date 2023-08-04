package org.prgrms.assignment.voucher.model;

import org.prgrms.assignment.voucher.exception.ErrorCode;
import org.prgrms.assignment.voucher.exception.GlobalCustomException;

public enum VoucherType {

    FIXED( "FixedAmountVoucher"),

    PERCENT("PercentDiscountVoucher");

    VoucherType(String voucherTypeName) {
        this.voucherTypeName = voucherTypeName;
    }

    private final String voucherTypeName;

    public static VoucherType of(String voucherTypeName) {
        try {
            return VoucherType.valueOf(voucherTypeName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new GlobalCustomException(ErrorCode.NO_VOUCHER_TYPE_ERROR);
        }
    }

}
