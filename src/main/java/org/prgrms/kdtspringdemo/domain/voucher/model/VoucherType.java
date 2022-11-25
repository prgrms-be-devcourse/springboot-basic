package org.prgrms.kdtspringdemo.domain.voucher.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;

public enum VoucherType {
    FIXED(FixedAmountVoucher::new), PERCENT(PercentDiscountVoucher::new);

    final TriFunction<UUID, Long, LocalDateTime, Voucher> constructor;

    VoucherType(TriFunction<UUID, Long, LocalDateTime, Voucher> constructor) {
        this.constructor = constructor;
    }

    public static VoucherType getTypeByName(String string) throws IllegalArgumentException {
        try {
            return VoucherType.valueOf(string.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("잘못된 바우쳐 타입 입니다.");
        }
    }

    //    public Voucher newInstance(UUID id, Long value) {
//        return this.constructor.apply(id, value);
//    }
    public Voucher newInstance(UUID id, Long value, LocalDateTime createdAt) {
        return this.constructor.apply(id, value, createdAt);
    }

    @FunctionalInterface
    interface TriFunction<A, B, C, R> {

        R apply(A a, B b, C c);

        default <V> TriFunction<A, B, C, V> andThen(
                Function<? super R, ? extends V> after) {
            Objects.requireNonNull(after);
            return (A a, B b, C c) -> after.apply(apply(a, b, c));
        }
    }
}
