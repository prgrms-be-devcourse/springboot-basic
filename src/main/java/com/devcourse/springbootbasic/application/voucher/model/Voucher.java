package com.devcourse.springbootbasic.application.voucher.model;

import com.devcourse.springbootbasic.application.global.exception.ErrorMessage;
import com.devcourse.springbootbasic.application.voucher.vo.DiscountValue;
import com.devcourse.springbootbasic.application.voucher.vo.VoucherType;
import com.devcourse.springbootbasic.application.global.exception.InvalidDataException;

import java.text.MessageFormat;
import java.util.UUID;

public class Voucher {
    private final UUID voucherId;
    private final VoucherType voucherType;
    private final DiscountValue discountValue;

    public Voucher(UUID voucherId, VoucherType voucherType, DiscountValue discountAmount) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.discountValue = discountAmount;
    }

    public double discountedPrice(long originalPrice) {
        double result = switch (voucherType) {
            case FIXED_AMOUNT -> originalPrice - discountValue.value();
            case PERCENT_DISCOUNT -> originalPrice - (1 - discountValue.value() / 100);
        };
        validateDiscountedPrice(result);
        return result;
    }

    private void validateDiscountedPrice(double price) {
        if (price < 0) {
            throw new InvalidDataException(ErrorMessage.INVALID_DISCOUNT_VALUE.getMessageText());
        }
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public DiscountValue getDiscountValue() {
        return discountValue;
    }

    public String toString() {
        return MessageFormat.format("{0}(id: {1}, type: {2}, discountValue: {3})", voucherType.name(), voucherId, voucherType.getTypeString(), discountValue.value());
    }
}
