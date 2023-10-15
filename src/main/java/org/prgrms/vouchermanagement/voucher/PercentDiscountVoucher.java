package org.prgrms.vouchermanagement.voucher;

import java.util.UUID;

public class PercentDiscountVoucher implements DiscountPolicy {

    private final UUID voucherId;
    private final long percent;
    private final PolicyStatus policyStatus;

    public PercentDiscountVoucher(UUID voucherId, long percent, PolicyStatus policyStatus) {
        this.voucherId = voucherId;
        this.percent = percent;
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
