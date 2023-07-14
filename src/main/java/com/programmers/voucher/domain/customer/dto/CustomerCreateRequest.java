package com.programmers.voucher.domain.customer.dto;

import com.programmers.voucher.domain.customer.entity.Customer;
import jakarta.validation.constraints.NotBlank;

public record CustomerCreateRequest(
        @NotBlank String nickname
) {
    public static CustomerCreateRequest of(String nickname) {
        return new CustomerCreateRequest(nickname);
    }

    public Customer toEntity() {
        return Customer.create(nickname);
    }
}
