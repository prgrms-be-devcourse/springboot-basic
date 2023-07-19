package com.tangerine.voucher_system.application.voucher.model;

import com.tangerine.voucher_system.application.global.exception.ErrorMessage;
import com.tangerine.voucher_system.application.global.exception.InvalidDataException;

import java.util.Objects;

public record DiscountValue(
        VoucherType voucherType,
        double value
) {

    public DiscountValue {
        validatePositive(value);
        validatePercent(voucherType, value);
    }

    private void validatePositive(double parsedValue) {
        if (parsedValue < 0) {
            throw new InvalidDataException(ErrorMessage.INVALID_VALUE.getMessageText());
        }
    }

    private void validatePercent(VoucherType voucherType, double parsedValue) {
        if (Objects.equals(voucherType, VoucherType.PERCENT_DISCOUNT) && parsedValue > 100) {
            throw new InvalidDataException(ErrorMessage.INVALID_VALUE.getMessageText());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiscountValue that = (DiscountValue) o;
        return Double.compare(that.value, value) == 0 && voucherType == that.voucherType;
    }

}
