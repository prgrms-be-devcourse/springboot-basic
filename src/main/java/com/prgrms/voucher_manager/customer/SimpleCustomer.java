package com.prgrms.voucher_manager.customer;

import lombok.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public class SimpleCustomer implements Customer {

    private static final Logger logger = LoggerFactory.getLogger(SimpleCustomer.class);

    private final UUID customerId;
    private String name;
    private final String email;
    private LocalDateTime lastLoginAt;
    private final LocalDateTime createdAt;

    public SimpleCustomer(UUID customerId, String name, String email, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    public SimpleCustomer(UUID customerId, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt) {
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }


    public void loginInNow() {
        this.lastLoginAt = LocalDateTime.now();
    }


    public void changeName(String name) {
        validateName(name);
        this.name = name;
    }

    private void validateName(String name) {
        if(name.isBlank()) {
            logger.info("이름은 비어있을 수 없습니다.", name);
            throw new RuntimeException("Name  should not be blank");
        }
    }

    @Override
    public String toString() {
        return "Customer : " +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", lastLoginAt=" + lastLoginAt +
                ", createdAt=" + createdAt;
    }
}
