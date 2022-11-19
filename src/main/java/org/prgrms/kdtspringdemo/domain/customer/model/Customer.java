package org.prgrms.kdtspringdemo.domain.customer.model;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.UUID;

public class Customer {
    @NonNull
    private final UUID customerId;
    @NonNull
    private final String name;
    @NonNull
    private final LocalDate birth;
    @NonNull
    private final String email;
    @NonNull
    private final LocalDateTime createdAt;
    @Nullable
    private LocalDateTime lastLoginAt;
    @NonNull
    private boolean isBlackCustomer;


    public Customer(UUID customerId, String name, LocalDate birth, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt, boolean isBlackCustomer) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt != null ? lastLoginAt.truncatedTo(ChronoUnit.MILLIS) : null;
        this.createdAt = createdAt.truncatedTo(ChronoUnit.MILLIS);
        this.birth = birth;
        this.isBlackCustomer = isBlackCustomer;
    }

    public Customer(UUID customerId, String name, LocalDate birth, String email, LocalDateTime createdAt, boolean isBlackCustomer) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt.truncatedTo(ChronoUnit.MILLIS);
        this.birth = birth;
        this.isBlackCustomer = isBlackCustomer;
    }

    public Customer(UUID customerId, String name, LocalDate birth, String email, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt.truncatedTo(ChronoUnit.MILLIS);
        this.birth = birth;
        this.isBlackCustomer = false;
    }

    public Customer(UUID customerId, String name, LocalDate birth, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
        this.birth = birth;
        this.isBlackCustomer = false;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isBlackCustomer() {
        return isBlackCustomer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return isBlackCustomer == customer.isBlackCustomer && customerId.equals(customer.customerId) && name.equals(customer.name) && birth.equals(customer.birth) && email.equals(customer.email) && createdAt.equals(customer.createdAt) && Objects.equals(lastLoginAt, customer.lastLoginAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, name, birth, email, createdAt, lastLoginAt, isBlackCustomer);
    }
}
