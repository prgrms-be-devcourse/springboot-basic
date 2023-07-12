package com.example.springbootbasic.voucher;

import java.text.MessageFormat;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private final long fixedAmount;

    public FixedAmountVoucher(String fixedAmount) {
        this.voucherId = UUID.randomUUID();
        this.fixedAmount = Long.parseLong(fixedAmount);
        validateInput();
    }

    public FixedAmountVoucher(long fixedAmount) {
        this.voucherId = UUID.randomUUID();
        this.fixedAmount = fixedAmount;
        validateInput();
    }

    public FixedAmountVoucher(UUID voucherId, long fixedAmount) {
        this.voucherId = voucherId;
        this.fixedAmount = fixedAmount;
        validateInput();
    }

    private void validateInput() {
        if (this.fixedAmount < 0) {
            throw new IllegalStateException("할인금액은 음수가 될 수 없습니다. 입력한 금액 : %d".formatted(this.fixedAmount));
        }
        if (this.fixedAmount == 0) {
            throw new IllegalStateException("할인금액은 0이 될 수 없습니다. 입력한 금액 : %d".formatted(this.fixedAmount));
        }
    }

    @Override
    public long discount(long beforeDiscount) {
        return Math.max(beforeDiscount - fixedAmount, 0);
    }

    @Override
    public void printInfo() {
        System.out.println(MessageFormat.format("VoucherType: {0}, fixedAmount: {1}$", this.getClass().getSimpleName(), this.fixedAmount));
    }

    public long getFixedAmount() {
        return fixedAmount;
    }

    public UUID getVoucherId() {
        return voucherId;
    }
}
