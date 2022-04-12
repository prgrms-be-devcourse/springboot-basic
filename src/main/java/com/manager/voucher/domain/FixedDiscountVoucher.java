package com.manager.voucher.domain;

import java.time.LocalDateTime;

public class FixedDiscountVoucher implements Voucher{
    private int amount;
    private LocalDateTime expireDate;

    public FixedDiscountVoucher(int amount, LocalDateTime expireDate) {
        this.amount = amount;
        this.expireDate = expireDate;
    }

    @Override
    public boolean isExpired() {
        return (LocalDateTime.now()).isAfter(this.expireDate);
    }

    @Override
    public void applyDiscountTo(Item item) {
        if(isExpired()) return;
        item.decresePriceByAmount(amount);
    }

    @Override
    public String toString() {
        return "fixed Amount: " + amount;
    }
}
