package org.prgrms.application.domain.customer;

import java.time.LocalDateTime;

public class Customer {
    private final Long customerId;
    private String name;
    private String email;
    private LocalDateTime lastLoginAt;
    private final LocalDateTime createdAt;

    public Customer(Long customerId, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt) {
        validateName(name);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
    }

    public Customer(Long customerId, String name, String email, LocalDateTime createdAt) {
        validateName(name);
        this.name = name;
        this.customerId = customerId;
        this.email = email;
        this.createdAt = createdAt;
    }

    public void changeName(String name){
        validateName(name);
        this.name = name;
    }

    public void login(){ //로그인에 따른 로그인 시간 변
        this.lastLoginAt = LocalDateTime.now();
    }

    public Long getCustomerId() {
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

    private void validateName(String name) {
        if(name.isBlank()){
            throw new RuntimeException("name should not be blank");
        }
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", lastLoginAt=" + lastLoginAt +
                ", createdAt=" + createdAt +
                '}';
    }
}
