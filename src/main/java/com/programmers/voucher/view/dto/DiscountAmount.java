package com.programmers.voucher.view.dto;

public class DiscountAmount {
    private final long amount;

    public DiscountAmount(VoucherType voucherType, long amount) {
        this.amount = amount;
        validatePositive();
        validatePercent(voucherType);
    }

    private void validatePositive() {
        if (amount < 0) {
            throw new IllegalArgumentException("할인 금액은 음수가 될 수 없습니다."); //TODO constant 추가
            //TODO log 추가
        }
    }

    private void validatePercent(VoucherType voucherType) {
        if (voucherType == VoucherType.PERCENT_DISCOUNT && amount >= 100) {
            throw new IllegalArgumentException("할인율은 100을 넘을 수 없습니다.");
        }
    }

    public long getAmount() {
        return amount;
    }
}
