package org.programmers.springbootbasic.voucher.domain;


import java.util.UUID;

import static org.programmers.springbootbasic.voucher.domain.VoucherType.FIXED;
import static org.programmers.springbootbasic.voucher.domain.VoucherType.RATE;

public interface Voucher {
    long discount(long beforeDiscount);

    UUID getId();

    int getAmount();

    VoucherType getType();

    Long getMemberId();

    static Voucher create(int amount, VoucherType voucherType) {
        if (voucherType == FIXED) {
            return new FixedDiscountVoucher(UUID.randomUUID(), amount);
        }
        if (voucherType == RATE) {
            return new RateDiscountVoucher(UUID.randomUUID(), amount);
        }
        throw new IllegalArgumentException("유효하지 않은 바우처 종류를 만들려고 시도했습니다.");
    }
}
