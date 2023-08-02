package com.prgrms.voucher.model;

import com.prgrms.voucher.model.discount.Discount;
import java.time.LocalDateTime;

public class PercentDiscountVoucher extends Voucher {

    public PercentDiscountVoucher(int voucherId, Discount discount, VoucherType voucherType,
            LocalDateTime createdAt) {
        super(voucherId, discount, voucherType, createdAt);
    }

}
