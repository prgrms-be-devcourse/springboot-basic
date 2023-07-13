package com.prgrms.springbootbasic.domain.customer;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Customer {

    private final UUID customerId;
    private String name;
    private String email;
    private final LocalDateTime createAt;

    public Customer(UUID customerId, String name, String email, LocalDateTime createAt) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createAt = createAt;
    }
}
