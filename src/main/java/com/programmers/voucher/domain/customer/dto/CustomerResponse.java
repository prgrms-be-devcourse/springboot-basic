package com.programmers.voucher.domain.customer.dto;

import com.programmers.voucher.domain.customer.entity.Customer;

import java.util.UUID;

public record CustomerResponse(
        UUID customerId,
        String nickname
) {
    public static CustomerResponse from(Customer customer) {
        return new CustomerResponse(customer.getId(), customer.getNickname());
    }

    @Override
    public String toString() {
        return String.format("[%s] nickname => %s", customerId, nickname);
    }
}
