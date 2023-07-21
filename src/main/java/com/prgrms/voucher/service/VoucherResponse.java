package com.prgrms.voucher.service;

import com.prgrms.voucher.model.Voucher;
import com.prgrms.voucher.model.VoucherType;
import com.prgrms.voucher.model.discount.Discount;

public record VoucherResponse(VoucherType voucherType,
                              Discount discount) {

    public VoucherResponse(Voucher voucher) {
        this(voucher.getVoucherType(), voucher.getVoucherDiscount());
    }

    @Override
    public String toString() {
        return voucherType + " : " + discount.getDiscountAmount();
    }

}
