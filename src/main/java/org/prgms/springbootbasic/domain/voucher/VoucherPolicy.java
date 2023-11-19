package org.prgms.springbootbasic.domain.voucher;

public interface VoucherPolicy {
    long discount(long beforeDiscount, long discountDegree);
}
