package org.weekly.weekly.customer.domain;

import org.weekly.weekly.customer.exception.CustomerValidator;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
    UUID uuid;
    String name;
    String email;
    LocalDateTime createAt;

    private Customer(UUID uuid, String name, String email) {
        this.uuid = uuid;
        this.name = name;
        this.email = email;
    }

    public static Customer of(UUID uuid, String name, String email) {
        CustomerValidator.validateEmailFormat(email);
        return new Customer(uuid, name, email);
    }

}
