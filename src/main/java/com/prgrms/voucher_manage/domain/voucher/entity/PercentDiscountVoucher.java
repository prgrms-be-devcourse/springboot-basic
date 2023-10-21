package com.prgrms.voucher_manage.domain.voucher.entity;

import static com.prgrms.voucher_manage.domain.voucher.entity.VoucherType.*;

public class PercentDiscountVoucher extends Voucher {
    public PercentDiscountVoucher(Long discountPercent) {
        super(discountPercent, PERCENT);
    }
}
