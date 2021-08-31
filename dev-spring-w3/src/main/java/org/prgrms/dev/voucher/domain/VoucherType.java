package org.prgrms.dev.voucher.domain;

import java.util.Arrays;
import java.util.UUID;
import java.util.function.BiFunction;

public enum VoucherType {
    FIXED("fixed", (voucherId, value) -> new FixedAmountVoucher(voucherId, value)),
    PERCENT("percent", (voucherId, value) -> new PercentDiscountVoucher(voucherId, value));

    private final String type;

    private final BiFunction<UUID, Long ,Voucher> voucherMaker;

    VoucherType(String type, BiFunction<UUID, Long, Voucher> voucherMaker) {
        this.type = type;
        this.voucherMaker = voucherMaker;
    }

    public Voucher create(UUID voucherId, long value) {
        return this.voucherMaker.apply(voucherId, value);
    }

    public static Voucher getVoucherType(String inputType, UUID voucherId, long value) {
        VoucherType voucherType = Arrays.stream(VoucherType.values())
                .filter(voucher -> voucher.type.equals(inputType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid input..."));

        return voucherType.create(voucherId, value);
    }
}
