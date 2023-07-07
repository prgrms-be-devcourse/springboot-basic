package com.programmers.springmission.voucher.domain;

public interface VoucherPolicy {
    long getAmount();

    long discount(long beforeDiscount);
}

