package org.prgrms.dev.voucher.domain;

import java.util.Arrays;
import java.util.UUID;
import java.util.function.Function;

public enum VoucherType {
    FIXED("fixed", value -> new FixedAmountVoucher(UUID.randomUUID(), value)),
    PERCENT("percent", value -> new PercentDiscountVoucher(UUID.randomUUID(), value));

    private final String type;

    private final Function<Long, Voucher> voucherMaker;

    VoucherType(String type, Function<Long, Voucher> voucherMaker) {
        this.type = type;
        this.voucherMaker = voucherMaker;
    }

    public Voucher create(Long value) {
        return this.voucherMaker.apply(value);
    }

    public static Voucher getVoucherType(String inputType, long value) {
        VoucherType voucherType = Arrays.stream(VoucherType.values())
                .filter(voucher -> voucher.type.equals(inputType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid input..."));

        return voucherType.create(value);
    }
}
