package org.prgrms.springbootbasic.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Builder
public class Customer {
    private UUID customerId;
    private String name;
    private String email;
    private final LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;

    public void changeName(String name) {
        this.name = name;
    }
}
