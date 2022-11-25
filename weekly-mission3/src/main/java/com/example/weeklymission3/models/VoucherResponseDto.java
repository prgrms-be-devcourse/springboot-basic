package com.example.weeklymission3.models;

import java.time.LocalDateTime;
import java.util.UUID;

public record VoucherResponseDto(UUID voucherId, String type, int discount, LocalDateTime createdAt) {
}
