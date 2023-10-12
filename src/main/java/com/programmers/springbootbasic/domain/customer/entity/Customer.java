package com.programmers.springbootbasic.domain.customer.entity;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
public class Customer {
    @Getter
    private final UUID customerId;
    private final String name;

    public String getInformation() {
        return String.format("""
                === Customer ===
                ID: %s
                Name: %s
                """, customerId.toString(), name);
    }
}
