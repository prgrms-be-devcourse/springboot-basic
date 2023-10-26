package org.prgrms.vouchermanagement.customer;

import java.util.UUID;

public class Customer {
    private final UUID userId;
    private final String userName;
    private final int userAge;

    public Customer(UUID userId, String userName, int userAge) {
        this.userId = userId;
        this.userName = userName;
        this.userAge = userAge;
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
}
