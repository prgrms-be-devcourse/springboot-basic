package com.prgrms.voucher.model.voucher;

import com.prgrms.common.util.Generator;
import com.prgrms.voucher.model.VoucherType;
import com.prgrms.voucher.model.discount.Discount;
import java.time.LocalDateTime;

public class PercentDiscountVoucher extends Voucher {

    public PercentDiscountVoucher(Generator generator, Discount discount, VoucherType voucherType) {
        super(generator, discount, voucherType);
    }

    public PercentDiscountVoucher(String voucherId, Discount discount, VoucherType voucherType,
            LocalDateTime createdAt) {
        super(voucherId, discount, voucherType, createdAt);
    }

}
