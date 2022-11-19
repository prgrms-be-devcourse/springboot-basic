package org.programmers.program.voucher.validator;

import org.programmers.program.voucher.model.VoucherType;
import java.util.function.BiPredicate;

import static org.programmers.program.voucher.model.VoucherType.*;


public class VoucherValidator implements BiPredicate<VoucherType, Long> {
    @Override
    public boolean test(VoucherType type, Long discountAmount) {
        return
            (type.equals(FIXED) && discountAmount >= FIXED.getLowerBound() && discountAmount <= FIXED.getUpperBound()) ||
            (type.equals(PERCENT) && discountAmount >= PERCENT.getLowerBound() && discountAmount <= PERCENT.getUpperBound());
    }
}