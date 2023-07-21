package com.prgrms.voucher.model;

import com.prgrms.voucher.model.discount.Discount;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class VoucherCreator {

    private VoucherCreator() { }

    public Voucher createVoucher(int id, VoucherType voucherType, Discount discount,
            LocalDateTime createdAt) {

        return switch (voucherType) {
            case FIXED_AMOUNT_VOUCHER ->
                    new FixedAmountVoucher(id, discount, voucherType, createdAt);
            case PERCENT_DISCOUNT_VOUCHER ->
                    new PercentDiscountVoucher(id, discount, voucherType, createdAt);
        };
    }

}
