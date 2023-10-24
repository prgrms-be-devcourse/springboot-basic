package org.prgrms.vouchermanagement.customer;

import java.util.UUID;

public class Customers {
    private final UUID userId;
    private final String userName;
    private final UUID voucherId;

    public Customers(UUID userId, String userName, UUID voucherId) {
        this.userId = userId;
        this.userName = userName;
        this.voucherId = voucherId;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public UUID getVoucherId() {
        return voucherId;
    }
}
