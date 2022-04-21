package org.prgrms.voucherapp.global.enums;

import java.util.Arrays;
import java.util.Optional;

public enum CustomerStatus {
    BLOCKED,
    BRONZE,
    SILVER,
    GOLD,
    VIP;

    public static Optional<CustomerStatus> getStatus(int option) {
        return Arrays.stream(values())
                .filter(o -> o.ordinal() == option-1)
                .findFirst();
    }
}
