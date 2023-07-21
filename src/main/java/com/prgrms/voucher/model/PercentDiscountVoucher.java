package com.prgrms.voucher.model;

import com.prgrms.voucher.model.discount.Discount;

public class PercentDiscountVoucher extends Voucher {

    public PercentDiscountVoucher(int voucherId, Discount discount, VoucherType voucherType) {
        super(voucherId, discount, voucherType);
    }

}
