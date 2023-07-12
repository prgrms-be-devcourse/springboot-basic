package com.prgrms.springbootbasic.domain.customer;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;

@Getter
public class Customer {

    private final UUID customerId;
    private final String name;
    private final String email;
    private LocalDateTime createAt;

    public Customer(UUID customerId, String name, String email, LocalDateTime createAt) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createAt = createAt;
    }
}
