package com.manager.voucher.domain;

import java.time.LocalDateTime;

public class FixedDiscountVoucher implements Voucher{
    private int amount;
    private LocalDateTime expireDate;

    public FixedDiscountVoucher(int amount) {
        this.amount = amount;
        this.expireDate = LocalDateTime.now().plusMonths(1);
    }

    @Override
    public boolean isExpired() {
        return (LocalDateTime.now()).isAfter(this.expireDate);
    }

    @Override
    public String toString() {
        return "fixed Amount: " + amount;
    }
}
