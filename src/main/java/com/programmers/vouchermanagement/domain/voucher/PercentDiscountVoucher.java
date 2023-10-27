package com.programmers.vouchermanagement.domain.voucher;

import com.programmers.vouchermanagement.message.ErrorMessage;

import java.time.LocalDateTime;
import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {

    public PercentDiscountVoucher(UUID voucherId, String voucherName, float discountAmount, LocalDateTime createdAt) {
        super(voucherId, voucherName, discountAmount, createdAt);
        validateDiscountAmount(discountAmount);
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.PERCENTAGE;
    }

    @Override
    public void validateDiscountAmount(float discountAmount) {
        if (discountAmount <= 0 || discountAmount > 100) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_PERCENTAGE_DISCOUNT_AMOUNT.getMessage());
        }
    }

    @Override
    public String joinInfo(String separator) {
        return String.join(separator, id.toString(), name, String.valueOf(discountAmount), createdAt.toString(), VoucherType.PERCENTAGE.name());
    }

    @Override
    public String toString() {
        return System.lineSeparator() +
                "++++++ Percentage Discount voucher ++++++" + System.lineSeparator() +
                "Voucher Id     : " + id + System.lineSeparator() +
                "Voucher Name   : " + name + System.lineSeparator() +
                "Discount percentage: " + discountAmount + "%" + System.lineSeparator() +
                "Created Time   :" + createdAt + System.lineSeparator();
    }

//    public float discount(float beforeDiscount) {
//        return beforeDiscount - (beforeDiscount * discountAmount / 100);
//    }

}
