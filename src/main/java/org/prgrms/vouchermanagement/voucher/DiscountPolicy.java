package org.prgrms.vouchermanagement.voucher;

public interface DiscountPolicy {
    long getAmountOrPercent();

    PolicyStatus getPolicyStatus();

    long discount(long price);
}
