package com.prgrms.devkdtorder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public enum VoucherType {
    FIXEDAMOUNT("1"),
    PERCENTDISCOUNT("2");

    private final String no;
    VoucherType(final String no){
        this.no = no;
    }

    public static Optional<VoucherType> findByNameOrNo(final String value){
        return Arrays.stream(VoucherType.values())
                .filter(e -> e.name().equals(value.toUpperCase()) || e.no.equals(value))
                .findAny();
    }

    public static List<String> voucherTypeNames() {
        return Arrays.stream(values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}
