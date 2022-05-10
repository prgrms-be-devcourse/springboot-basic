package org.prgrms.voucher.models;

import java.time.LocalDateTime;

public class Customer {

    private final Long customerId;
    private final String customerName;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public Customer(String customerName) {

        this.customerId = null;
        this.customerName = customerName;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Customer(Long customerId, String customerName, LocalDateTime createdAt, LocalDateTime updatedAt) {

        this.customerId = customerId;
        this.customerName = customerName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getCustomerId() {

        return customerId;
    }

    public String getCustomerName() {

        return customerName;
    }

    public LocalDateTime getCreatedAt() {

        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {

        return updatedAt;
    }
}