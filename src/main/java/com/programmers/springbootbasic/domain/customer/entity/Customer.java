package com.programmers.springbootbasic.domain.customer.entity;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class Customer {

    private final UUID customerId;
    private final String email;
    private String name;
    private boolean isBlacklist;

    public void addBlacklist() {
        this.isBlacklist = true;
    }

    public void removeBlacklist() {
        this.isBlacklist = false;
    }

    public String getInformation() {
        return String.format("""
                === Customer ===
                Email: %s
                Name: %s
                """, email, name);
    }
}
