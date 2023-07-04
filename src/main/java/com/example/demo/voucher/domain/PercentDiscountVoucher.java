package com.example.demo.voucher.domain;


import com.example.demo.voucher.application.VoucherType;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final long percent;
    private final String name = VoucherType.PERCENT_DISCOUNT_VOUCHER.name();

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        validateValue(percent);
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }

    private void validateValue(long percent) {
        if (percent >= 100 || percent <= 0) {
            throw new IllegalArgumentException("1~99 사이의 숫자만 입력 가능합니다.");
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public long getValue() {
        return percent;
    }
}
