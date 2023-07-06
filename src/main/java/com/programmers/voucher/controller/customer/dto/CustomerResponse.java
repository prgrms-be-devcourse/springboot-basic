package com.programmers.voucher.controller.customer.dto;

import com.programmers.voucher.entity.customer.Customer;

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
        return "CustomerResponse{" +
                "customerId=" + customerId +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
