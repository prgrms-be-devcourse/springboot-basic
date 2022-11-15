package org.prgrms.springorder.domain.customer.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import org.springframework.util.StringUtils;


public class Customer {

    private final UUID customerId;

    private String name;

    private final String email;

    private LocalDateTime lastLoginAt;

    private final LocalDateTime createdAt;

    private CustomerStatus customerStatus;

    public Customer(UUID customerId, String name, String email, LocalDateTime lastLoginAt,
        LocalDateTime createdAt, CustomerStatus customerStatus) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
        this.customerStatus = customerStatus;
    }

    public Customer(UUID customerId, String name, String email) {
        validateName(name);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdAt = LocalDateTime.now();
        customerStatus = CustomerStatus.NORMAL;
    }

    public void changeName(String name) {
        validateName(name);
        this.name = name;
    }

    private void validateName(String name) {
        if (!StringUtils.hasText(name)) {
            throw new RuntimeException("Name should not be black");
        }
    }

    public void updateLastLoginAt(LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    public void block(CustomerStatus blockStatus) {
        this.customerStatus = blockStatus;
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

    public CustomerStatus getCustomerStatus() {
        return customerStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Customer customer = (Customer) o;
        return Objects.equals(customerId, customer.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId);
    }
}