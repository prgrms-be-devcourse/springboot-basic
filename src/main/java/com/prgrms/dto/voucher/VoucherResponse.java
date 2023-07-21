package com.prgrms.dto.voucher;

import com.prgrms.model.voucher.Voucher;
import com.prgrms.model.voucher.VoucherType;
import com.prgrms.model.voucher.discount.Discount;

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
