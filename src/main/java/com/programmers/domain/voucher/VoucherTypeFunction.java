package com.programmers.domain.voucher;

@FunctionalInterface
public interface VoucherTypeFunction<T, U, V, R> {
    R apply(T t, U u, V v);
}
