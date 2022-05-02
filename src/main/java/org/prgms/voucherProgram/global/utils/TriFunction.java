package org.prgms.voucherProgram.global.utils;

@FunctionalInterface
public interface TriFunction<A, B, C, D, R> {
    R apply(A a, B b, C c, D d);
}
