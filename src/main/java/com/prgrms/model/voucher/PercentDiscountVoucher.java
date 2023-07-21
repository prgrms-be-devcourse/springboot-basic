package com.prgrms.model.voucher;

import com.prgrms.model.voucher.discount.Discount;

public class PercentDiscountVoucher extends Voucher {

    public PercentDiscountVoucher(int voucherId, Discount discount, VoucherType voucherType) {
        super(voucherId, discount, voucherType);
    }

}
