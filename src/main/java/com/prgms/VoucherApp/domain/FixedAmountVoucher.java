package com.prgms.VoucherApp.domain;

import com.prgms.VoucherApp.dto.VoucherDto;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private final long fixedAmount;

    public FixedAmountVoucher(UUID voucherId, long fixedAmount) {
        this.voucherId = voucherId;
        this.fixedAmount = fixedAmount;
    }

    @Override
    public long discount(long beforeAmount) {
        if (isResultNegative(beforeAmount)) {
            return 0L;
        }

        return beforeAmount - fixedAmount;
    }

    @Override
    public UUID getUUID() {
        return this.voucherId;
    }

    @Override
    public VoucherDto convertVoucherDto() {
        String voucherId = String.valueOf(this.voucherId);
        String discountAmount = String.valueOf(this.fixedAmount);
        return new VoucherDto(voucherId, discountAmount, "fix");
    }

    private boolean isResultNegative(long beforeAmount) {
        return beforeAmount - fixedAmount < 0;
    }

    @Override
    public String toString() {
        return "고정 비용 할인권, 할인금액 : [" + fixedAmount + "]";
    }
}
