package com.prgrms.voucher.service.dto;

import com.prgrms.voucher.model.Voucher;
import com.prgrms.voucher.model.VoucherType;
import com.prgrms.voucher.model.discount.Discount;

public record VoucherServiceResponse(VoucherType voucherType,
                                     Discount discount,
                                     int voucherId) {

    public VoucherServiceResponse(Voucher voucher) {
        this(voucher.getVoucherType(), voucher.getVoucherDiscount(), voucher.getVoucherId());
    }

    @Override
    public String toString() {
        return voucherType + " : " + discount.getDiscountAmount();
    }

}
