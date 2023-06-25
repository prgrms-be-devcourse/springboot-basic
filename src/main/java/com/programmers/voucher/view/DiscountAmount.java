package com.programmers.voucher.view;

public class DiscountAmount {
    private final long amount;

    public DiscountAmount(VoucherCommand voucherCommand, long amount) {
        this.amount = amount;
        validatePositive();
        validatePercent(voucherCommand);
    }

    private void validatePositive() {
        if (amount < 0) {
            throw new IllegalArgumentException("할인 금액은 음수가 될 수 없습니다."); //TODO constant 추가
        }
    }

    private void validatePercent(VoucherCommand voucherCommand) {
        if (voucherCommand == VoucherCommand.PERCENT_DISCOUNT && amount >= 100) {
            throw new IllegalArgumentException("할인율은 100을 넘을 수 없습니다.");
        }
    }
}
