package com.prgrms.model.voucher;


import com.prgrms.model.voucher.dto.discount.Discount;

@FunctionalInterface
public interface TriFunction<ID,D,V,R> {
    R apply(Integer n, Discount d, VoucherType v);
}
