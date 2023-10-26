package org.prgrms.vouchermanagement.voucher.policy;

public class FixedAmountVoucher implements DiscountPolicy {

    private final long amount;
    private final PolicyStatus policyStatus = PolicyStatus.FIXED;

    public FixedAmountVoucher(long amount) {
        this.amount = amount;
    }

    @Override
    public long getAmountOrPercent() {
        return amount;
    }

    @Override
    public PolicyStatus getPolicyStatus() {
        return policyStatus;
    }

    @Override
    public long discount(long price) {
        return price - amount;
    }
}
