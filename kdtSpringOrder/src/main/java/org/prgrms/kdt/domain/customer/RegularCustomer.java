package org.prgrms.kdt.domain.customer;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.UUID;

//도메인 클래스에서 비즈니스 룰을 작성한다.
public class RegularCustomer implements Customer {
    private final UUID customerId;
    private String name;
    private final String email;
    private Integer isBadCustomer;
    private LocalDateTime lastLoginAt;
    private final LocalDateTime createdAt;

    private void validate(String name) {
        if(name.isBlank()) {
            throw new RuntimeException("Name should not be blank");
        }
    }

    public void login() {
        this.lastLoginAt = LocalDateTime.now();
    }

    public RegularCustomer(UUID customerId, String name, String email, Integer isBadCustomer, LocalDateTime lastLoginAt, LocalDateTime createdAt) {
        validate(name);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.isBadCustomer = isBadCustomer;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
    }

    public RegularCustomer(UUID customerId, String name, String email, LocalDateTime createdAt) {
        validate(name);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    public void changeName(String name) {
        validate(name);
        this.name = name;
    }

    public void changeBadCustomer(Integer isBadCustomer) {
        this.isBadCustomer = isBadCustomer;
    }

    @Override
    public UUID getCustomerId() {
        return customerId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public Integer getBadCustomer() {
        return isBadCustomer;
    }

    @Override
    public String toString() {
        return MessageFormat.format(" {0},\" {1}\",\" {2},\" {3}\" ", customerId, name, email, createdAt);
    }
}
