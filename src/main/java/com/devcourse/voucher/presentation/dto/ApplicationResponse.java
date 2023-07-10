package com.devcourse.voucher.presentation.dto;

public record ApplicationResponse<T>(boolean status, T payload) {
}
