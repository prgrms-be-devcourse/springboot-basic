package com.programmers.springbasic.domain.voucher.entity;


import com.programmers.springbasic.domain.voucher.model.VoucherType;

import java.time.LocalDate;
import java.util.UUID;

public class FixedAmountVoucher extends Voucher {
    private static final long DEFAULT_EXPIRE_DURATION = 7;

    private final VoucherType voucherType = VoucherType.FIXED_AMOUNT_VOUCHER;

    public FixedAmountVoucher(double value, UUID customerId) {
        this.code = UUID.randomUUID();
        this.value = value;
        this.expirationDate = getDefaultExpirationDate();
        this.isActive = true;
        this.customerId = customerId;
    }

    // DB row to Entity Mapping
    public FixedAmountVoucher(UUID voucherCode, double value, LocalDate expirationDate, boolean isActive, UUID customerId) {
        this.code = voucherCode;
        this.value = value;
        this.expirationDate = expirationDate;
        this.isActive = isActive;
        this.customerId = customerId;
    }

    private LocalDate getDefaultExpirationDate() {
        LocalDate date = LocalDate.now();
        date.plusDays(DEFAULT_EXPIRE_DURATION);

        return date;
    }

    @Override
    public double discount(double inputAmount) {
        double value = inputAmount - this.value;

        if (value < 0) {
            throw new RuntimeException("할인 금액이 더 큰 경우 할인을 적용할 수 없습니다.");
        }
        return value;
    }

    @Override
    public void updateValue(double value) {
        this.value = value;
    }

    @Override
    public VoucherType getVoucherType() {
        return this.voucherType;
    }
}
