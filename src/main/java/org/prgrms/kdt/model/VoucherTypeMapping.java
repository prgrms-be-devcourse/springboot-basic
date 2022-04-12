package org.prgrms.kdt.model;

import java.util.HashMap;
import java.util.Map;

public class VoucherTypeMapping {
    private static final Map<Integer, VoucherType> voucherTypeMap = new HashMap<>();

    static {
        voucherTypeMap.put(1, VoucherType.FIXED_AMOUNT);
        voucherTypeMap.put(2, VoucherType.PERCENT_DISCOUNT);
    }

    public static VoucherType getVoucherType(int voucherType) {
        return voucherTypeMap.get(voucherType);
    }

}
