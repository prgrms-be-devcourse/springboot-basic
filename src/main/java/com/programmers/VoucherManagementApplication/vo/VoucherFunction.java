package com.programmers.VoucherManagementApplication.vo;

@FunctionalInterface
public interface VoucherFunction<T, V, E, R> {
    R apply(T t, V v, E e);
}
