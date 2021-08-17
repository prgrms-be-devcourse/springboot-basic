package com.prgrms.devkdtorder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum VoucherType {
    FiXEDAMOUNT,
    PERCENTDISCOUNT;


    public static List<String> voucherTypeNames() {
        return Arrays.stream(values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}
