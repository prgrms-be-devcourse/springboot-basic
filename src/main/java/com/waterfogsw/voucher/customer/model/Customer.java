package com.waterfogsw.voucher.customer.model;

import com.waterfogsw.voucher.customer.controller.dto.CustomerAddRequest;

import java.time.LocalDateTime;

public class Customer {
    private final long id;
    private final String name;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public Customer(String name) {
        this(0, name);
    }

    public Customer(long id, String name) {
        this(id, name, LocalDateTime.now(), LocalDateTime.now());
    }

    public Customer(long id, String name, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Customer from(CustomerAddRequest customerAddRequest) {
        return new Customer(customerAddRequest.name());
    }

    public static Customer toEntity(long id, Customer customer) {
        return new Customer(id, customer.getName(), customer.getCreatedAt(), customer.getUpdatedAt());
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
}
