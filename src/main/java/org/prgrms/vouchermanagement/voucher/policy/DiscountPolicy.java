package org.prgrms.vouchermanagement.voucher.policy;

public interface DiscountPolicy {
    long getAmountOrPercent();

    PolicyStatus getPolicyStatus();

    long discount(long price);
}
