package com.prgrms.voucher_manager.customer;

import com.prgrms.voucher_manager.customer.controller.CustomerDto;
import lombok.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
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
        validateName(name);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    public SimpleCustomer(UUID customerId, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt) {
        validateName(name);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
    }


    public UUID getCustomerId() {
        return customerId;
    }


    public void loginInNow() {
        this.lastLoginAt = LocalDateTime.now();
    }


    @Override
    public void changeName(String name) {
        validateName(name);
        this.name = name;
    }

    @Override
    public CustomerDto toCustomerDto() {
        return new CustomerDto(customerId, name, email, lastLoginAt, createdAt);
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> hashMap = new HashMap<>() {{
            put("customerId", customerId.toString().getBytes());
            put("name", name);
            put("email", email);
            put("createdAt", createdAt);
            put("lastLoginAt", lastLoginAt != null ? lastLoginAt : null);
        }};
        return hashMap;
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
