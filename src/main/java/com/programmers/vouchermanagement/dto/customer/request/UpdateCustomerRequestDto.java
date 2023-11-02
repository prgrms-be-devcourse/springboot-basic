package com.programmers.vouchermanagement.dto.customer.request;

import java.util.UUID;

// PUT
public record UpdateCustomerRequestDto(UUID id, String email) {
}
