package org.prgrms.springbootbasic.engine.domain;

import com.opencsv.bean.CsvBindByName;
import org.prgrms.springbootbasic.exception.VoucherException;

import java.time.LocalDateTime;
import java.util.*;

public class Customer {
    @CsvBindByName
    private final UUID customerId;
    @CsvBindByName
    private String name;
    @CsvBindByName
    private String email;
    @CsvBindByName
    private LocalDateTime lastLoginAt;
    @CsvBindByName
    private final LocalDateTime createdAt;
    private Boolean isBlack;

    public Customer(UUID customerId, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt) {
        validate(name);
        validate(email);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
        this.isBlack = false;
    }

    public Customer(UUID customerId, String name, String email, LocalDateTime createdAt) {
        validate(name);
        validate(email);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.isBlack = false;
    }

    public void changeName(String name) {
        validate(name);
        this.name = name;
    }

    public void changeEmail(String email) {
        validate(email);
        this.email = email;
    }

    public void toBlack() {
        this.isBlack = true;
    }

    public void revokeBlack() {
        this.isBlack = false;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getIsBlack() {return isBlack;}

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    private void validate(String input) {
        if (input.isBlank()) {
            throw new VoucherException("Name and email is required!");
        }
    }
}
