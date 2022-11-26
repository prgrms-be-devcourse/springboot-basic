package com.example.springbootbasic.controller.request;

import java.time.LocalDateTime;

public record CreateVoucherRequest(Long discountValue, String voucherType, LocalDateTime startAt, LocalDateTime endAt) {
}
