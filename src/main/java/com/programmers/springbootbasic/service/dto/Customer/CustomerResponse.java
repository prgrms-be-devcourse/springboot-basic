package com.programmers.springbootbasic.service.dto.Customer;

import java.util.UUID;

public record CustomerResponse(
        UUID customerId,
        String name,
        String email
) {
}
