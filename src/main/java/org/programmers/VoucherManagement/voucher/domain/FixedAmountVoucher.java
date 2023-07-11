package org.programmers.VoucherManagement.voucher.domain;

import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode(callSuper = false)
public class FixedAmountVoucher extends Voucher {
    public FixedAmountVoucher(UUID voucherId, DiscountType discountType, DiscountValue discountValue) {
        this.voucherId = voucherId;
        this.discountType = discountType;
        this.discountValue = discountValue;
    }

}
