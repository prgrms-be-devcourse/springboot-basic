package com.manager.voucher.domain;


import java.time.LocalDateTime;

public class PercentDiscountVoucher implements Voucher{
    private int percent;
    private LocalDateTime expireDate;

    public PercentDiscountVoucher(int percent, LocalDateTime expireDate) {
        this.percent = percent;
        this.expireDate = expireDate;
    }

    @Override
    public boolean isExpired() {
        return (LocalDateTime.now()).isAfter(this.expireDate);
    }

    @Override
    public void applyDiscountTo(Item item) {
        if(isExpired()) return;
        item.decresePriceByPercent(percent);
    }

    @Override
    public String toString() {
        return "percent discount: " + percent;
    }
}
