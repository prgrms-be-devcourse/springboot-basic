package org.programmers.spbw1.voucher;

import java.util.Map;
import java.util.Optional;

public enum VoucherType {
    FIXED("1~100,000"),
    PERCENT("1~100");

    private String range;
    VoucherType(String range) {
        this.range = range;
    }

    private static final Map<String, VoucherType> map = Map.of(
            "1", FIXED,
            "2", PERCENT
    );

    public static Optional<VoucherType> getVoucherTypeBySelection(String selection){
        if(map.containsKey(selection))
            return Optional.of(map.get(selection));
        return Optional.empty();
    }

    public static String getRange(VoucherType type){
        return type.range;
    }

    public static boolean validRange(VoucherType type, Long amount){
        if (type.equals(VoucherType.FIXED) && (amount <= 0 || amount > 100000))
            return false;
        else
            return !type.equals(VoucherType.PERCENT) || (amount > 0 && amount <= 100);
    }
}
