package com.devcourse.voucher.presentation.dto;

public record ApplicationResponse<T>(boolean power, T payload) {
}
