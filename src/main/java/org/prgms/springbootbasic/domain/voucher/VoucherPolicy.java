package org.prgms.springbootbasic.domain.voucher;

import java.util.UUID;

public interface VoucherPolicy {
    UUID getVoucherId();
    long discount(long beforeDiscount);
    long getDiscountDegree();
}
