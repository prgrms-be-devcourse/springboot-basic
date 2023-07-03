package com.devcourse.springbootbasic.application.domain.voucher;

import com.devcourse.springbootbasic.application.constant.ErrorMessage;
import com.devcourse.springbootbasic.application.dto.DiscountValue;
import com.devcourse.springbootbasic.application.dto.VoucherType;
import com.devcourse.springbootbasic.application.exception.InvalidDataException;

import java.text.MessageFormat;
import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {

    public PercentDiscountVoucher(UUID uuid, VoucherType voucherType, DiscountValue discountValue) {
        super(uuid, voucherType, discountValue);
    }

    @Override
    public double discountedPrice(long originalPrice) {
        var result = originalPrice - (1 - discountValue.getValue() / 100);
        if (result >= 0) {
            return result;
        }
        throw new InvalidDataException(ErrorMessage.INVALID_DISCOUNT_VALUE.getMessageText());
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }

    @Override
    public DiscountValue getDiscountValue() {
        return discountValue;
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0}(id: {1}, type: {2}, discountValue: {3})", voucherType.name(), voucherId, voucherType.getTypeString(), discountValue.getValue());
    }

}
