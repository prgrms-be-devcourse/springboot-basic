package org.prgrms.kdtspringorder.BlackCustomer;

import java.util.UUID;

public class BlacklistCustomer {
    private final UUID customerId;
    private final String name;

    public BlacklistCustomer(UUID customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    @Override
    public String toString() {
        return "BlacklistCustomer{" +
                "customerId=" + customerId +
                ", name='" + name ;
    }
}
