package com.example.springbootbasic.controller.request;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record CreateVoucherRequest(Long discountValue,
                                   String voucherType,
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startAt,
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endAt) {
}
