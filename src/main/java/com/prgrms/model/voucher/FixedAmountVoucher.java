package com.prgrms.model.voucher;

import com.prgrms.model.voucher.discount.Discount;

public class FixedAmountVoucher extends Voucher {

    public FixedAmountVoucher(int voucherId, Discount discount, VoucherType voucherType) {
        super(voucherId, discount, voucherType);
    }

}
