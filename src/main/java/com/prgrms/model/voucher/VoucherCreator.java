package com.prgrms.model.voucher;

import com.prgrms.model.voucher.discount.Discount;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VoucherCreator {

    private UUID id = UUID.randomUUID();

    public Voucher createVoucher(Discount discount, VoucherType voucherType) {

        return switch (voucherType) {
            case FIXED_AMOUNT_VOUCHER -> new FixedAmountVoucher(id, discount, voucherType);
            case PERCENT_DISCOUNT_VOUCHER -> new PercentDiscountVoucher(id, discount, voucherType);
        };
    }
    public UUID getVoucherID() {
        return id;
    }
}
