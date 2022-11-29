package org.prgrms.voucherapplication.domain.voucher.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Stream;

import static org.prgrms.voucherapplication.global.exception.ExceptionCode.NOT_EXIST;

public enum VoucherType {
    FIXED(1,"원하시는 할인 금액을 입력해주세요.", FixedAmountVoucher::new),
    PERCENT(2,"원하시는 할인 퍼센트를 입력해주세요.", PercentDiscountVoucher::new);

    private final int order;
    private final String discountGuide;
    private final TriFunction<UUID, Integer, LocalDateTime, Voucher> constructor;

    VoucherType(int order, String discountGuide, TriFunction<UUID, Integer, LocalDateTime, Voucher> constructor) {
        this.order = order;
        this.discountGuide = discountGuide;
        this.constructor = constructor;
    }

    public String getDiscountGuide() {
        return discountGuide;
    }

    public static String getNames() {
        StringBuilder names = new StringBuilder();
        VoucherType[] values = VoucherType.values();
        for (VoucherType voucherType : values) {
            names.append(voucherType.order).append(". ").append(voucherType.name().toLowerCase()).append(" ");
        }
        return names.toString();
    }

    public static VoucherType of(String name) {
        return Stream.of(VoucherType.values())
                .filter(voucherType -> voucherType.name().equals(name))
                .findFirst()
                .orElseThrow(() -> new VoucherTypeOfException(NOT_EXIST));
    }

    public Voucher createVoucher(UUID uuid, int discount, LocalDateTime createdAt) {
        return this.constructor.apply(uuid, discount, createdAt);
    }

    @FunctionalInterface
    interface TriFunction<T, U, V, R> {
        R apply(T t, U u, V v);
    }
}
