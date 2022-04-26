package org.prgrms.vouchermanager.domain.blacklist.domain;

import java.util.UUID;

public class Blacklist {
    private final UUID customerId;
    private final String email;

    public Blacklist(UUID customerId, String email) {
        if (email.isBlank()) throw new IllegalArgumentException("email은 공백이 될 수 없습니다.");
        if (customerId == null) throw new IllegalArgumentException("blacklist의 UUID는 null이 될 수 없습니다.");

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
