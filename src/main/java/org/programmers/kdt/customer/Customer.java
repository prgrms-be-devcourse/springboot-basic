package org.programmers.kdt.customer;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private String name;
    private final String email; // 변경가능해야할 것 같은데 강의 내용 때문에...
    private LocalDateTime lastLoginAt;
    private LocalDateTime createdAt;

    public Customer(UUID customerId, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
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

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean changeName(String newName) {
        if (newName.isBlank()) {
            throw new RuntimeException("Name should not be a black");
        }
        name = newName;
        return true;
    }

    @Override
    public String toString() {
        return MessageFormat
                .format("<< Customer Information >>\nCustomer ID : {0}\nCustomer Name : {1}\nCustomer Email : {2}\nLast Login At : {3}\nSign Up At : {4}",
                        customerId, name, email, lastLoginAt, createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(customerId);
    }
}
