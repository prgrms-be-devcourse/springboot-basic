package org.programmers.VoucherManagement.voucher.domain;

import java.util.UUID;

public class PercentAmountVoucher extends Voucher{
    public PercentAmountVoucher(UUID voucherId, DiscountType discountType, DiscountValue discountValue) {
        super(voucherId, discountType, discountValue);
    }
}
