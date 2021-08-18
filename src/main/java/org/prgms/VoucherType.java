package org.prgms;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum VoucherType {
    FIXED_AMOUNT,
    PERCENT_DISCOUNT;


    public static List<String> voucherTypes() {
        return Arrays.stream(values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}
