package com.manager.voucher.domain;


import java.time.LocalDateTime;

public class PercentDiscountVoucher implements Voucher{
    private int percent;
    private LocalDateTime expireDate;

    public PercentDiscountVoucher(int percent) {
        this.percent = percent;
        this.expireDate = LocalDateTime.now().plusMonths(1);
    }

    @Override
    public boolean isExpired() {
        return (LocalDateTime.now()).isAfter(this.expireDate);
    }

    @Override
    public String toString() {
        return "percent discount: " + percent;
    }
}
