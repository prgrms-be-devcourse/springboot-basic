package com.example.voucher.domain.discount;

import com.example.voucher.domain.Voucher;

public class PercentDiscountPolicy implements DiscountPolicy {
    private final double percent;
    private double discountedAmount;  // 할인 적용된 값을 저장하는 변수

    public PercentDiscountPolicy(double percent) {
        this.percent = percent;
    }

    @Override
    public void applyDiscount(Voucher voucher) {
        // 할인율 적용 로직 구현
        double originalAmount = voucher.getAmount();  // 원래 금액 가져오기
        discountedAmount = originalAmount * percent;  // 할인 적용된 금액 계산하여 저장
    }

    public double getDiscountedAmount() {
        return discountedAmount;  // 할인 적용된 값을 반환하는 메서드
    }
}