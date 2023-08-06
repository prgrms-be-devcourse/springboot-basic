package com.programmers.springbootbasic.domain.customer;

import java.util.UUID;

public record CustomerUpdateDto(
        UUID id,
        String name,
        String email
) {
}
