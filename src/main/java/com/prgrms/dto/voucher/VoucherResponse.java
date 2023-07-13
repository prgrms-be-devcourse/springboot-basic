package com.prgrms.dto.voucher;

import com.prgrms.model.voucher.Vouchers;
import com.prgrms.model.voucher.discount.Discount;
import com.prgrms.model.voucher.Voucher;
import com.prgrms.model.voucher.VoucherType;

import java.util.List;

public class VoucherResponse {
    private final VoucherType voucherType;
    private final Discount discount;

    public VoucherResponse(Voucher voucher) {
        this.voucherType = voucher.getVoucherType();
        this.discount = voucher.getVoucherDiscount();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(voucherType).append(" : ")
                .append(discount.getDiscountAmount());

        return sb.toString();
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public Discount getDiscount() {
        return discount;
    }

    public List<VoucherResponse> convertVoucherResponse(Vouchers vouchers) {
        return vouchers.getVouchers()
                .stream()
                .map(VoucherResponse::new)
                .toList();
    }
}
