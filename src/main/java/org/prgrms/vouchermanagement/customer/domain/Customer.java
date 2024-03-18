package org.prgrms.vouchermanagement.customer.domain;

import java.util.UUID;

public class Customer {
    private final UUID userId;
    private final String userName;
    private final int userAge;
    private final boolean isBlacked;

    public Customer(UUID userId, String userName, int userAge) {
        this.userId = userId;
        this.userName = userName;
        this.userAge = userAge;
        this.isBlacked = false;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public int getUserAge() {
        return userAge;
    }

    public boolean isBlacked() {
        return isBlacked;
    }
}
