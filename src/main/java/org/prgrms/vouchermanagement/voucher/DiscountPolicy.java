package org.prgrms.vouchermanagement.voucher;

import java.util.UUID;

public interface DiscountPolicy {
    UUID getVoucherId();

    long getAmountOrPercent();

    PolicyStatus getPolicyStatus();
}
