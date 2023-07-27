package com.example.voucher.customer.service.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import com.example.voucher.constant.CustomerType;

public record CustomerDTO(UUID customerId, String name, String email, CustomerType customerType,
                          LocalDateTime createdAt) {
}
