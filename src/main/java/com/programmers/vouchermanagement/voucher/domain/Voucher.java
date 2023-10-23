package com.programmers.vouchermanagement.voucher.domain;

import com.programmers.vouchermanagement.voucher.dto.GeneralVoucherDTO;

import java.math.BigDecimal;
import java.util.UUID;

public class Voucher {
    private static final String INVALID_DISCOUNT_INPUT_MESSAGE =
            "Input should be a number greater than 0 and smaller than 100";
    private final UUID voucherID;
    private final BigDecimal discountValue;
    private final VoucherType voucherType;

    public Voucher(UUID voucherID, VoucherType voucherType, BigDecimal discountValue) {
        this.voucherID = voucherID;
        this.voucherType = voucherType;
        this.voucherType.validateDiscountValue(discountValue);
        this.discountValue = discountValue;
    }

    public UUID getVoucherId() {
        return voucherID;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public BigDecimal discount(BigDecimal priceBeforeDiscount) {
        return voucherType.discount(discountValue, priceBeforeDiscount);
    }

    ;
}
