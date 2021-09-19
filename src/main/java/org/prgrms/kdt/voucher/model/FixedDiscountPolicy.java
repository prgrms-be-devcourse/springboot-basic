package org.prgrms.kdt.voucher.model;

import java.text.MessageFormat;
import org.prgrms.kdt.exception.InvalidDataException;

public class FixedDiscountPolicy implements DiscountPolicy {

    @Override
    public long discount(long beforeDiscount, long discount) {
        validateBeforeDiscountAmount(beforeDiscount);
        validateDiscountAmount(discount);
        return beforeDiscount > discount ? beforeDiscount - discount : 0;
    }

    @Override
    public void validateBeforeDiscountAmount(long beforeDiscount) {
        if (beforeDiscount < 0) {
            throw new InvalidDataException(
                MessageFormat
                    .format("beforeDiscount amount should be positive: {0}", beforeDiscount));
        }
    }

    @Override
    public void validateDiscountAmount(long discount) {
        if (discount < 0) {
            throw new InvalidDataException(
                MessageFormat.format("fixed discount amount should be positive: {0}", discount));
        }
        if (discount == 0) {
            throw new InvalidDataException(
                MessageFormat.format("fixed discount amount should not be zero: {0}", discount));
        }
    }
}
