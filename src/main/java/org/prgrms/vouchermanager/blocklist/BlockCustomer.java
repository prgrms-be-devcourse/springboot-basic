package org.prgrms.vouchermanager.blocklist;

import java.util.UUID;

public class BlockCustomer {
    private final UUID customerId;
    private final String email;

    public BlockCustomer(UUID customerId, String email) {
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
