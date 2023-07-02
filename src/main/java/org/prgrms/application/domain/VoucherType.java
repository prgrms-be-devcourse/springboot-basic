package org.prgrms.application.domain;

import java.util.Arrays;
import java.util.Optional;

public enum VoucherType {
    FIXED,
    PERCENT;

    public static Optional<VoucherType> findBySelection(String selection){
        return Arrays.stream(values())
                .filter(s -> s.name().equals(selection.toUpperCase()))
                .findFirst();
    }


}
