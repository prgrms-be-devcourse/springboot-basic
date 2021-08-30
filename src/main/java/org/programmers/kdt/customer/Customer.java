package org.programmers.kdt.customer;

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
        // TODO : DB에서 login 을 위한 접근이 발생할 때 마다 갱신되도록 수정하기
        this.lastLoginAt = lastLoginAt;
        // TODO : DB에 insert 된 후 DB에서 받아오도록 수정
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return customerId.equals(customer.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId);
    }
}
