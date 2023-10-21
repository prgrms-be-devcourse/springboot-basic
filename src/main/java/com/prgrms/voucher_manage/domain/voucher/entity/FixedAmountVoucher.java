package com.prgrms.voucher_manage.domain.voucher.entity;

import static com.prgrms.voucher_manage.domain.voucher.entity.VoucherType.FIXED;

public class FixedAmountVoucher extends Voucher {
    public FixedAmountVoucher(Long discountPrice) {
        super(discountPrice, FIXED);
    }
}
