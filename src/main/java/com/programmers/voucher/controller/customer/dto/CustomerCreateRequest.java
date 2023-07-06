package com.programmers.voucher.controller.customer.dto;

import com.programmers.voucher.entity.customer.Customer;

public record CustomerCreateRequest(
        String nickname
) {
    public static CustomerCreateRequest of(String nickname) {
        return new CustomerCreateRequest(nickname);
    }

    public Customer toEntity() {
        return Customer.create(nickname);
    }
}
