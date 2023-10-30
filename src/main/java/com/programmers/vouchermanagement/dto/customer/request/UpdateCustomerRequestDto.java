package com.programmers.vouchermanagement.dto.customer.request;

import java.util.UUID;

// PATCH
public record UpdateCustomerRequestDto(UUID id, String email) {
}
