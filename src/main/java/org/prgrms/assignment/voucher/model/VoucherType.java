package org.prgrms.assignment.voucher.model;

import java.util.HashMap;
import java.util.Map;

public enum VoucherType {

    FIXED( "FixedAmountVoucher"),

    PERCENT("PercentDiscountVoucher");

    VoucherType(String voucherTypeName) {
        this.voucherTypeName = voucherTypeName;
    }

    private final String voucherTypeName;

    public static VoucherType of(String voucherTypeName) {
        return VoucherType.valueOf(voucherTypeName.toUpperCase());
    }

}
