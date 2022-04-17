package org.programmers.springbootbasic.voucher.model;

import java.util.Arrays;
import java.util.UUID;
import java.util.function.BiFunction;

public enum VoucherType {
    FIXED(1, "FixedAmountVoucher", FixedAmountVoucher::new),
    PERCENT(2, "PercentDiscountVoucher", PercentDiscountVoucher::new);

    private final int number;
    private final String type;
    private final BiFunction<UUID, Long, Voucher> voucherFunction;

    VoucherType(int number, String type, BiFunction<UUID, Long, Voucher> voucherFunction) {
        this.number = number;
        this.type = type;
        this.voucherFunction = voucherFunction;
    }

    public static VoucherType findByNumber(int number) {
        return Arrays.stream(VoucherType.values())
                .filter(voucher -> voucher.number == number)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 바우처 넘버입니다."));
    }

    public static VoucherType findByType(String type) {
        var voucherType = Arrays.stream(VoucherType.values())
                .filter(voucher -> voucher.type.equals(type))
                .findFirst();
        return voucherType.orElseThrow(() -> new IllegalArgumentException("잘못된 바우처 타입입니다."));
    }

    public Voucher create(Long value) {
        return voucherFunction.apply(UUID.randomUUID(), value);
    }

    public Voucher createWithUUID(UUID voucherId, Long value) {
        return voucherFunction.apply(voucherId, value);
    }
}
