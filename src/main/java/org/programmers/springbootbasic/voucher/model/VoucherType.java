package org.programmers.springbootbasic.voucher.model;

import java.util.Arrays;
import java.util.UUID;
import java.util.function.BiFunction;

public enum VoucherType {
    FIXED(1, "amount", FixedAmountVoucher::new),
    PERCENT(2, "percent", PercentDiscountVoucher::new);

    private final int number;
    private final String type;
    private final BiFunction<UUID, Long, Voucher> voucherFunction;

    VoucherType(int number, String type, BiFunction<UUID, Long, Voucher> voucherFunction) {
        this.number = number;
        this.type = type;
        this.voucherFunction = voucherFunction;
    }

    public static VoucherType findByNumber(int number) {
        var voucherType = Arrays.stream(VoucherType.values())
                .filter(voucher -> voucher.number == number)
                .findFirst();
        return voucherType.orElseThrow(() -> new IllegalArgumentException("잘못된 바우처 넘버입니다."));
    }

    public Voucher create(Long value) {
        return voucherFunction.apply(UUID.randomUUID(), value);
    }
}
