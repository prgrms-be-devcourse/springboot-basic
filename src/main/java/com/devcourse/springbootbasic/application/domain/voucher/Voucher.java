package com.devcourse.springbootbasic.application.domain.voucher;

import com.devcourse.springbootbasic.application.constant.ErrorMessage;
import com.devcourse.springbootbasic.application.dto.DiscountValue;
import com.devcourse.springbootbasic.application.dto.VoucherType;
import com.devcourse.springbootbasic.application.exception.InvalidDataException;

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
            case FIXED_AMOUNT -> originalPrice - discountValue.getValue();
            case PERCENT_DISCOUNT -> originalPrice - (1 - discountValue.getValue() / 100);
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
        return MessageFormat.format("{0}(id: {1}, type: {2}, discountValue: {3})", voucherType.name(), voucherId, voucherType.getTypeString(), discountValue.getValue());
    }
}
