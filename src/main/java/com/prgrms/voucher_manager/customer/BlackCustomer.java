//package com.prgrms.voucher_manager.customer;
//
//import lombok.Builder;
//
//import java.sql.Array;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//@Builder
//public class BlackCustomer implements Customer {
//
//    private final UUID customerId;
//    private String name;
//    private final String email;
//    private LocalDateTime lastLoginAt;
//    private final LocalDateTime createdAt;
//
//    public BlackCustomer(UUID customerId, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt) {
//        this.customerId = customerId;
//        this.name = name;
//        this.email = email;
//        this.lastLoginAt = lastLoginAt;
//        this.createdAt = createdAt;
//    }
//
//    @Override
//    public UUID getCustomerId() {
//        return customerId;
//    }
//
//    @Override
//    public String getName() {
//        return name;
//    }
//
//    @Override
//    public String getEmail() {
//        return email;
//    }
//
//    @Override
//    public LocalDateTime getCreatedAt() {
//        return createdAt;
//    }
//
//    @Override
//    public LocalDateTime getLastLoginAt() {
//        return lastLoginAt;
//    }
//
//    @Override
//    public void loginInNow() {
//        this.lastLoginAt = LocalDateTime.now();
//    }
//
//    @Override
//    public String toString() {
//        return "BlackCustomer{" +
//                "customerId=" + customerId +
//                ", name='" + name + '\'' +
//                ", email='" + email + '\'' +
//                ", lastLoginAt=" + lastLoginAt +
//                ", createdAt=" + createdAt +
//                '}';
//    }
//}
