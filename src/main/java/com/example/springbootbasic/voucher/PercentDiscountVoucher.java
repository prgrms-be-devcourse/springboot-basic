package com.example.springbootbasic.voucher;

import java.text.MessageFormat;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final int percent;

    public PercentDiscountVoucher(String percent) throws NumberFormatException {
        this.voucherId = UUID.randomUUID();
        this.percent = Integer.parseInt(percent);
        validateInput();
    }

    public PercentDiscountVoucher(int percent) {
        this.voucherId = UUID.randomUUID();
        this.percent = percent;
        validateInput();
    }

    public PercentDiscountVoucher(UUID voucherId, int percent) {
        this.voucherId = voucherId;
        this.percent = percent;
        validateInput();
    }

    private void validateInput() {
        if (this.percent < 0 || this.percent > 100) {
            throw new IllegalStateException("퍼센트는 0 ~ 100 사이의 숫자여야 합니다. 입력한 숫자: %d".formatted(this.percent));
        }
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - beforeDiscount * (percent / 100);
    }

    @Override
    public void printInfo() {
        System.out.println(MessageFormat.format("VoucherType: {0}, percent: {1}%", this.getClass().getSimpleName(), percent));
    }

    public long getPercent() {
        return percent;
    }

    public UUID getVoucherId() {
        return voucherId;
    }
}
