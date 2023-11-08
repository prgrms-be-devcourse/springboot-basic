package org.programmers.springorder.customer.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private final String name;
    private final CustomerType customerType;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private Customer(UUID customerId, String name, CustomerType customerType) {
        this.customerId = customerId;
        this.name = name;
        this.customerType = customerType;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    private Customer(UUID customerId, String name, CustomerType customerType, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.customerId = customerId;
        this.name = name;
        this.customerType = customerType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Customer toNewCustomer(UUID customerId, String name, CustomerType customerType) {
        return new Customer(customerId, name, customerType);
    }

    public static Customer fromDbCustomer(UUID customerId, String name, CustomerType customerType, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new Customer(customerId, name, customerType, createdAt, updatedAt);
    }

    public boolean sameCustomerId(UUID customerId){
        return this.customerId.equals(customerId);
    }
    public boolean isBlackList() {
        return this.customerType == CustomerType.BLACK;
    }

    public String insertCustomerDataInFile() {
        StringBuilder data = new StringBuilder();
        data.append(this.customerId).append(",")
                .append(this.name).append(",")
                .append(this.customerType.name()).append(",")
                .append(this.createdAt).append(",")
                .append(this.updatedAt);
        return data.toString();
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer customer)) return false;
        return Objects.equals(customerId, customer.customerId) && Objects.equals(name, customer.name) && customerType == customer.customerType && Objects.equals(createdAt, customer.createdAt) && Objects.equals(updatedAt, customer.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, name, customerType, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", customerType=" + customerType +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
