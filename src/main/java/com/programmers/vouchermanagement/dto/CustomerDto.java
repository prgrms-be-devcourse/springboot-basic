package com.programmers.vouchermanagement.dto;

import com.programmers.vouchermanagement.domain.customer.Customer;

import java.time.LocalDateTime;

public class CustomerDto {

    public record CreateRequest(String name) {
    }

    public static class Response {
        private final String id;
        private final String name;
        private final LocalDateTime createdAt;
        private final boolean isBanned;

        public Response(Customer customer) {
            this.id = customer.getId().toString();
            this.name = customer.getName();
            this.createdAt = customer.getCreatedAt();
            this.isBanned = customer.isBanned();
        }
    }
}
