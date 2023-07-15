package com.programmers.springmission.voucher.domain;

import com.programmers.springmission.voucher.domain.enums.VoucherType;

public interface VoucherPolicy {
    VoucherType getVoucherType();

    long discount(long beforeDiscount, long amount);

    void validateAmount(long amount);
}

