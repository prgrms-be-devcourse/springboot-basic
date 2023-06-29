package org.programmers.VoucherManagement.voucher.domain;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {
    public FixedAmountVoucher(UUID voucherId, DiscountType discountType, int discountValue) {
        super(voucherId, discountType, discountValue);
    }

}
