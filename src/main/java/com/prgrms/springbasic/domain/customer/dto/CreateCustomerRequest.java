package com.prgrms.springbasic.domain.customer.dto;

public record CreateCustomerRequest(
        String email, String name
) {
}
