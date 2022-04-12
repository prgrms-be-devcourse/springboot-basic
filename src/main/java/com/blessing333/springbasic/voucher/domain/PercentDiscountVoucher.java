package com.blessing333.springbasic.voucher.domain;

import lombok.Getter;

@Getter
public class PercentDiscountVoucher extends Voucher {
    private final int percent;

    public PercentDiscountVoucher(int percent) {
        super();
        this.percent = percent;
    }

    @Override
    public long discount(long beforePrice) {
        double discountRate = (100 - percent) / 100.0;
        return Math.round(beforePrice * discountRate);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        return sb.append("바우처 아이디 : ").append(this.getVoucherId().toString()).append("\n")
                .append("바우처 타입 : 비율 할인\n")
                .append("할인 비율 : ").append(this.percent).append("\n")
                .toString();
    }
}
