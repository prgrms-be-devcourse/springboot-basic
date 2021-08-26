package org.prgrms.kdt.domain.customer;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private final Name name;
    private final Email email;
    private final LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime lastLoginAt;
    private CustomerStatus customerStatus = CustomerStatus.WHITE;


    public Customer(UUID customerId, Name name, Email email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = LocalDateTime.now();
    }
}
