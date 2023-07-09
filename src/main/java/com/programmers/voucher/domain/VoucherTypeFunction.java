package com.programmers.voucher.domain;

@FunctionalInterface
public interface VoucherTypeFunction<T, U, V, S, R> {
    R apply(T t, U u, V v, S s);
}
