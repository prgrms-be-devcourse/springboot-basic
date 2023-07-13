package com.prgrms.model.voucher;

import com.prgrms.dto.voucher.VoucherRequest;
import com.prgrms.model.voucher.discount.Discount;
import org.springframework.stereotype.Component;

@Component
public class VoucherCreator {
    private VoucherCreator() {
    }

    public Voucher createVoucher(int id, VoucherRequest voucherRequest) {
        VoucherType voucherType = voucherRequest.getVoucherType();
        Discount discount = voucherRequest.getDiscount();

        return switch (voucherType) {
            case FIXED_AMOUNT_VOUCHER -> new FixedAmountVoucher(id, discount, voucherType);
            case PERCENT_DISCOUNT_VOUCHER -> new PercentDiscountVoucher(id, discount, voucherType);
        };
    }

}
