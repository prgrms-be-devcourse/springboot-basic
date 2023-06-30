package com.programmers.springbasic.domain.voucher.entity;

import com.programmers.springbasic.domain.voucher.model.VoucherOption;
import lombok.Getter;

@Getter
public class PercentDiscountVoucher extends Voucher {
    public PercentDiscountVoucher(double percent) {
        super();
        this.value = percent;
        this.voucherType = VoucherOption.PERCENT_DISCOUNT_VOUCHER;
    }
}
