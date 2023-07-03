package com.programmers.springbasic.domain.voucher.entity;

import com.programmers.springbasic.domain.voucher.view.VoucherOption;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
public class PercentDiscountVoucher extends Voucher {
    public PercentDiscountVoucher(double percent) {
        super();
        this.value = percent;
        this.voucherType = VoucherOption.PERCENT_DISCOUNT_VOUCHER;
    }

    public PercentDiscountVoucher(double percent, UUID customerId) {
        super();
        this.value = percent;
        this.voucherType = VoucherOption.PERCENT_DISCOUNT_VOUCHER;
        this.customerId = customerId;
    }

    public PercentDiscountVoucher(UUID voucherCode, double value, LocalDate expirationDate, boolean isActive, UUID customerId) {
        this.code = voucherCode;
        this.value = value;
        this.voucherType = VoucherOption.PERCENT_DISCOUNT_VOUCHER;
        this.expirationDate = expirationDate;
        this.isActive = isActive;
        this.customerId = customerId;
    }
}
