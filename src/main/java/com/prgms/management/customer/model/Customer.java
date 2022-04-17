package com.prgms.management.customer.model;

import com.prgms.management.customer.exception.CustomerException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
public class Customer {
    private final UUID id;
    private final String email;
    private final Timestamp lastLoginAt;
    private final Timestamp createdAt;
    private String name;
    private CustomerType type;

    public Customer(CustomerType type, UUID id, String name) {
        this(id, name, type, "demo", Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()));
    }

    public Customer(String name, CustomerType type, String email) {
        this(UUID.randomUUID(), name, type, email, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()));
    }

    public Customer(UUID id, String name, CustomerType type, String email, Timestamp lastLoginAt, Timestamp createdAt) {
        if (type == CustomerType.NONE) {
            throw new CustomerException("유효하지 않은 타입입니다.");
        }
        this.id = id;
        this.name = name;
        this.type = type;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) && Objects.equals(email, customer.email) && Objects.equals(name, customer.name) && type == customer.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, name, type);
    }
}
