package com.programmers.springbasic.domain.voucher.entity;

import com.programmers.springbasic.domain.voucher.model.VoucherType;

import java.time.LocalDate;
import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {
    private static final long DEFAULT_EXPIRE_DURATION = 7;

    private VoucherType voucherType = VoucherType.PERCENT_DISCOUNT_VOUCHER;

    public PercentDiscountVoucher(double value, UUID customerId) {
        this.code = UUID.randomUUID();
        this.value = value;
        this.expirationDate = setDefaultExpirationDate();
        this.isActive = true;
        this.customerId = customerId;
    }

    // DB row to Entity Mapping
    public PercentDiscountVoucher(UUID voucherCode, double value, LocalDate expirationDate, boolean isActive, UUID customerId) {
        this.code = voucherCode;
        this.value = value;
        this.expirationDate = expirationDate;
        this.isActive = isActive;
        this.customerId = customerId;
    }

    private LocalDate setDefaultExpirationDate() {
        LocalDate date = LocalDate.now();
        date.plusDays(DEFAULT_EXPIRE_DURATION);

        return date;
    }

    @Override
    public double discount(double inputAmount) {
        return inputAmount * (this.value / 100);
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
