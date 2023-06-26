package org.prgrms.kdt.voucher.model;

import java.util.HashMap;
import java.util.Map;

public enum VoucherType {
    FIXED(1, "FixedAmountVoucher"),
    PERCENT(2, "PercentDiscountVoucher");

    VoucherType(int voucherTypeNum, String voucherTypeName) {
        this.voucherTypeNum = voucherTypeNum;
        this.voucherTypeName = voucherTypeName;
    }

    private final int voucherTypeNum;
    private final String voucherTypeName;
    private static final Map<Integer, VoucherType> voucherTypeNameMap = new HashMap<>();
    static {
        for(VoucherType voucherType : VoucherType.values()) {
            voucherTypeNameMap.put(voucherType.voucherTypeNum, voucherType);
        }
    }

    public String getVoucherTypeName() {
        return voucherTypeName;
    }

    public static VoucherType of(Integer voucherTypeNum) {
        return voucherTypeNameMap.get(voucherTypeNum);
    }
}
