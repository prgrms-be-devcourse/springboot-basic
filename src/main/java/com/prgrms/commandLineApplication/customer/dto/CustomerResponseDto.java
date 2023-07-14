package com.prgrms.commandLineApplication.customer.dto;

import java.util.UUID;

public record CustomerResponseDto(UUID customerId, String customerName, String email) {
}
