package com.programmers.voucher.domain.customer.dto;

public record CustomerUpdateRequest(
        String nickname
) {
    public static CustomerUpdateRequest of(String nickname) {
        return new CustomerUpdateRequest(nickname);
    }
}
