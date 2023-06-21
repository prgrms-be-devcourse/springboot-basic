package org.prgms.vouchermanagement.voucher;

import org.prgms.vouchermanagement.constant.ExceptionMessageConstant;

public enum VoucherType {
    FIXED_AMOUNT_VOUCHER_TYPE(1),
    PERCENT_DISCOUNT_VOUCHER_TYPE(2);

    private final int type;

    VoucherType(int type) {
        this.type = type;
    }

    public static VoucherType getVoucherType(int type) {
        return switch (type) {
            case 1 -> FIXED_AMOUNT_VOUCHER_TYPE;
            case 2 -> PERCENT_DISCOUNT_VOUCHER_TYPE;
            default -> throw new IllegalStateException(ExceptionMessageConstant.VOUCHER_TYPE_INPUT_EXCEPTION);
        };
    }
}
