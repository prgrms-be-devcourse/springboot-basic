package com.programmers.voucher.model.voucher;

import com.programmers.voucher.io.Message;

import java.util.UUID;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public enum VoucherType {
    FIXED("1", "FixedAmountVoucher", FixedAmountVoucher::new),
    PERCENT("2", "PercentDiscountVoucher", PercentDiscountVoucher::new);

    private final String type;
    private final String name;
    private final BiFunction<UUID, Long, Voucher> converter;

    VoucherType(String type, String name, BiFunction<UUID, Long, Voucher> converter) {
        this.type = type;
        this.name = name;
        this.converter = converter;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public static VoucherType toVoucherType(String inputString) {
        return Stream.of(VoucherType.values())
                .filter(type -> type.getType().equals(inputString))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(Message.WRONG_ORDER_MESSAGE.toString()));
    }

    public static VoucherType toVoucherTypeByName(String voucherName) {
        return Stream.of(VoucherType.values())
                .filter(type -> type.getName().equals(voucherName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(Message.WRONG_ORDER_MESSAGE.toString()));
    }

    public Voucher convertToVoucher(UUID voucherId, long discount) {
        return converter.apply(voucherId, discount);
    }
}
