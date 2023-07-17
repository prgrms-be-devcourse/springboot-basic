package com.example.voucher.service.customer.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import com.example.voucher.constant.CustomerType;

public record CustomerDTO(UUID customerId, String name, String email, CustomerType customerType,
                          LocalDateTime createdAt) {
}
