package com.programmers.VoucherManagementApplication.dto;

@FunctionalInterface
public interface VoucherFunction<T, V, E, R> {
    R apply(T t, V v, E e);
}
