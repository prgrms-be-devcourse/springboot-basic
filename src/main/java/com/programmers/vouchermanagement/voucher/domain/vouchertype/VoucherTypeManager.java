package com.programmers.vouchermanagement.voucher.domain.vouchertype;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.function.Supplier;

public enum VoucherTypeManager {
    FIXED(FixedAmountVoucherType::getInstance),
    PERCENT(PercentVoucherType::getInstance);

    private final Supplier<VoucherType> voucherTypeSupplier;

    VoucherTypeManager(Supplier<VoucherType> voucherTypeSupplier) {
        this.voucherTypeSupplier = voucherTypeSupplier;
    }

    public static VoucherType get(String typeName) {
        return getGenerator(typeName).voucherTypeSupplier.get();
    }

    private static VoucherTypeManager getGenerator(String typeName) {
        return Arrays.stream(values())
                .filter(type -> type.name().equals(typeName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("There is no such type: {0}.", typeName)));
    }
}
