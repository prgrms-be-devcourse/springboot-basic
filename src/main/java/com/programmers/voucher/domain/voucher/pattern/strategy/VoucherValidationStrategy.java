package com.programmers.voucher.domain.voucher.pattern.strategy;

public interface VoucherValidationStrategy {
    void validateAmount(long amount);
}
