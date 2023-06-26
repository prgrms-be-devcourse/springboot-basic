package org.devcourse.voucher.domain.voucher;

@FunctionalInterface
public interface DiscountPolicy {

    Money discount(Money originPrice);

}
