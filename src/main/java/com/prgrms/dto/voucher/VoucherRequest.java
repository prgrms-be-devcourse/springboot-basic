package com.prgrms.dto.voucher;

import com.prgrms.model.voucher.discount.Discount;
import com.prgrms.model.voucher.VoucherType;

public record VoucherRequest(
        VoucherType voucherType,
        Discount discount
) {

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public Discount getDiscount() {
        return discount;
    }

}
