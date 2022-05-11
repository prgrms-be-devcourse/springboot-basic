package kdt.vouchermanagement.domain.customer.entity;

import kdt.vouchermanagement.domain.customer.domain.Customer;

import java.time.LocalDateTime;

public class CustomerEntity {
    private final long id;
    private final String name;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private CustomerEntity(long id, String name, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public static CustomerEntity of(long id, String name, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new CustomerEntity(id, name, createdAt, updatedAt);
    }

    public Customer toDomain() {
        return Customer.of(id, name, createdAt, updatedAt);
    }

    public static CustomerEntity from(Customer customer) {
        return new CustomerEntity(0, customer.getName(), null, null);
    }
}
