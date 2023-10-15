package org.prgrms.vouchermanagement.voucher;

import java.util.UUID;

public class FixedAmountVoucher implements DiscountPolicy {

    private final UUID voucherId;
    private final long amount;
    private final PolicyStatus policyStatus;

    public FixedAmountVoucher(UUID voucherId, long amount, PolicyStatus policyStatus) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.policyStatus = policyStatus;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public PolicyStatus getPolicyStatus() {
        return policyStatus;
    }
}
