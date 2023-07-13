package com.programmers.voucher.domain.customer.dto;

import com.programmers.voucher.domain.customer.entity.Customer;

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
