package org.prgrms.kdt.model.discount;

public interface DiscountPolicy {
    long discount(long beforeDiscount, long discount);

    void validateBeforeDiscountAmount(long beforeDiscount);
    void validateDiscountAmount(long discount);
}
