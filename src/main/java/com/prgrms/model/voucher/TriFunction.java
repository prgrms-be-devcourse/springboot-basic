package com.prgrms.model.voucher;

@FunctionalInterface
public interface TriFunction<Integer, Discount, VoucherType, Voucher> {
    Voucher apply(Integer n, Discount d, VoucherType v);
}
