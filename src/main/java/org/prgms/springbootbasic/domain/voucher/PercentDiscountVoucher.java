package org.prgms.springbootbasic.domain.voucher;

import lombok.extern.slf4j.Slf4j;
import org.prgms.springbootbasic.exception.OutOfRangeException;

@Slf4j
public class PercentDiscountVoucher implements VoucherPolicy {
    @Override
    public long discount(long beforeDiscount, long discountDegree) {
        if (beforeDiscount < 0) {
            throw new OutOfRangeException("beforeDiscount is less than 0.");
        }

        return beforeDiscount * discountDegree / 100L;
    }
}
