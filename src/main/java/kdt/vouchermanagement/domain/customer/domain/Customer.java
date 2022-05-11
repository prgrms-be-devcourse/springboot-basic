package kdt.vouchermanagement.domain.customer.domain;

import kdt.vouchermanagement.domain.customer.dto.CustomerCreateRequest;

import java.time.LocalDateTime;

public class Customer {
    private long id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Customer(String name) {
        this(0, name, null, null);
    }

    private Customer(long id, String name, LocalDateTime createdAt, LocalDateTime updatedAt) {
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

    public static Customer of(CustomerCreateRequest request) {
        return new Customer(request.getName());
    }

    public static Customer of(long id, String name, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new Customer(id, name, createdAt, updatedAt);
    }
}
