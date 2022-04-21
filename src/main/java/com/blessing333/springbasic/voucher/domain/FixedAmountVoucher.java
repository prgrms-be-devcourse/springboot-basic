package com.blessing333.springbasic.voucher.domain;

import lombok.Getter;

@Getter
public class FixedAmountVoucher extends Voucher{
    private final long discountAmount;

    public FixedAmountVoucher(long discountAmount){
        super();
        validateDiscountAmount(discountAmount);
        this.discountAmount = discountAmount;
    }

    @Override
    public long discount(long beforePrice) {
        long discountedPrice = beforePrice - discountAmount;
        return discountedPrice > 0 ? discountedPrice : 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        return sb.append("바우처 아이디 : ").append(this.getVoucherId().toString()).append("\n")
        .append("바우처 타입 : 고정금액 할인\n")
        .append("할인 금액 : ").append(this.discountAmount).append("\n")
        .toString();
    }

    private void validateDiscountAmount(long discountAmount){
        if(discountAmount < 0)
            throw new IllegalArgumentException("할인 금액은 0보다 작을 수 없습니다");
    }
}
