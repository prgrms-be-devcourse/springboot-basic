package com.prgrms.voucher.service;

import com.prgrms.voucher.model.Voucher;
import com.prgrms.voucher.model.VoucherType;
import com.prgrms.voucher.model.discount.Discount;

public record VoucherResponse(VoucherType voucherType,
                              Discount discount,
                              int voucherId) {

    public VoucherResponse(Voucher voucher) {
        this(voucher.getVoucherType(), voucher.getVoucherDiscount(), voucher.getVoucherId());
    }

    @Override
    public String toString() {
        return voucherType + " : " + discount.getDiscountAmount();
    }

}
