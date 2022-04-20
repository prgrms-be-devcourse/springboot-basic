package org.prgrms.vouchermanager.blacklist;

import java.util.UUID;

public class Blacklist {
    private final UUID customerId;
    private final String email;

    public Blacklist(UUID customerId, String email) {
        if (email.isBlank()) throw new IllegalArgumentException();
        if (customerId == null) throw new IllegalArgumentException();
        this.customerId = customerId;
        this.email = email;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "BlockCustomer{" +
                "customerId=" + customerId +
                ", email='" + email + '\'' +
                '}';
    }
}
