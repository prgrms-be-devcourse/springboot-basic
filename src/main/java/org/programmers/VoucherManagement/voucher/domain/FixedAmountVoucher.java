package org.programmers.VoucherManagement.voucher.domain;

public class FixedAmountVoucher extends Voucher {
    public FixedAmountVoucher(String voucherId, DiscountType discountType, DiscountValue discountValue) {
        this.voucherId = voucherId;
        this.discountType = discountType;
        this.discountValue = discountValue;
    }
}
