package com.prgrms.voucher.model;

import com.prgrms.voucher.model.discount.Discount;

public class FixedAmountVoucher extends Voucher {

    public FixedAmountVoucher(int voucherId, Discount discount, VoucherType voucherType) {
        super(voucherId, discount, voucherType);
    }

}
