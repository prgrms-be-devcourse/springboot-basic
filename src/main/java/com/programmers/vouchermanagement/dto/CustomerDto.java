package com.programmers.vouchermanagement.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class CustomerDto {
    public static class Create {
        public UUID id;
        public String name;
        public LocalDateTime createdAt;
        public boolean isBanned;

        public Create(String name) {
            this.name = name;
        }

        public Create(UUID id, String name, LocalDateTime createdAt, boolean isBanned) {
            this.id = id;
            this.name = name;
            this.createdAt = createdAt;
            this.isBanned = isBanned;
        }

        public Create(String[] customerInfo) {
            this.id = UUID.fromString(customerInfo[0]);
            this.name = customerInfo[1];
            this.createdAt = LocalDateTime.parse(customerInfo[2]);
            this.isBanned = Boolean.parseBoolean(customerInfo[3]);
        }
    }
}
