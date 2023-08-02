package com.prgrms.voucher.model;

import com.prgrms.voucher.model.discount.Discount;
import com.prgrms.voucher.model.voucher.FixedAmountVoucher;
import com.prgrms.voucher.model.voucher.PercentDiscountVoucher;
import com.prgrms.voucher.model.voucher.Voucher;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class VoucherCreator {

    private VoucherCreator() { }

    public Voucher createVoucher(String id, VoucherType voucherType, Discount discount,
            LocalDateTime createdAt) {

        return switch (voucherType) {
            case FIXED_AMOUNT_VOUCHER ->
                    new FixedAmountVoucher(id, discount, voucherType, createdAt);
            case PERCENT_DISCOUNT_VOUCHER ->
                    new PercentDiscountVoucher(id, discount, voucherType, createdAt);
        };
    }

}
