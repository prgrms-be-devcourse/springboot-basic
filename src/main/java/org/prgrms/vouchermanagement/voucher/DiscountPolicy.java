package org.prgrms.vouchermanagement.voucher;

import java.util.UUID;

public interface DiscountPolicy {
    UUID getVoucherId();

    PolicyStatus getPolicyStatus();
}
