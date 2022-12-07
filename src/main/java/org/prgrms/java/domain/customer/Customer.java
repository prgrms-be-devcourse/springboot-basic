package org.prgrms.java.domain.customer;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
public class Customer {
    private UUID customerId;
    private String name;
    private String email;
    private final LocalDateTime createdAt;
    private boolean isBlocked;

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s, %s", customerId, name, email, createdAt, isBlocked);
    }
}
