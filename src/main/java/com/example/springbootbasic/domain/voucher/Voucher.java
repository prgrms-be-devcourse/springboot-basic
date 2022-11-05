package com.example.springbootbasic.domain.voucher;

public interface Voucher {
    Long getVoucherId();
    long discount(long discountBefore);
}
