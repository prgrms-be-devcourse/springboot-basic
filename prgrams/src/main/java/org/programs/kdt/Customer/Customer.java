package org.programs.kdt.Customer;

import lombok.Getter;
import org.programs.kdt.Exception.InvalidValueException;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.programs.kdt.Exception.ErrorCode.INVALID_CUSTOMER_EMAIL;
import static org.programs.kdt.Exception.ErrorCode.INVALID_CUSTOMER_NAME;

@Getter
public class Customer {

    private final UUID customerId;
    private String name;
    private final String email;
    private final LocalDateTime createdAt;
    private CustomerType customerType = CustomerType.NORMAL;

    public Customer(UUID customerId, String name, String email) {
        validate(name, email);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdAt = LocalDateTime.now();

    }

    public Customer(UUID customerId, String name, String email, LocalDateTime createdAt) {
        validate(name, email);
        this.name = name;
        this.customerId = customerId;
        this.email = email;
        this.createdAt = createdAt;

    }

    public Customer(UUID customerId, String name, String email, LocalDateTime createdAt, CustomerType customerType) {
        validate(name, email);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.customerType = customerType;

    }

    private void validate(String name, String email) {
        validateName(name);
        validateEmail(email);
    }

    private void validateName(String name) {
        if (name == null) {
            throw new InvalidValueException(INVALID_CUSTOMER_NAME);
        }
        if (name.isBlank()) {
            throw new InvalidValueException(INVALID_CUSTOMER_NAME);
        } else if (name.length() > 15) {
            throw new InvalidValueException(INVALID_CUSTOMER_NAME);
        }
    }

    private void validateEmail(String email) {
        if (email == null) {
            throw new InvalidValueException(INVALID_CUSTOMER_NAME);
        }
        if (email.isBlank()) {
            throw new InvalidValueException(INVALID_CUSTOMER_EMAIL);
        }
        else if (email.length() > 25) {
            throw new InvalidValueException(INVALID_CUSTOMER_EMAIL);
        }
    }


    public void changeName(String name) {
        validateName(name);
        this.name = name;
    }

    public void setBlacklist() {
        this.customerType = CustomerType.BLACKLIST;
    }

}
