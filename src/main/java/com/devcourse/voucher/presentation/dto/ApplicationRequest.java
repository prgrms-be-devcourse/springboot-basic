package com.devcourse.voucher.presentation.dto;

import com.devcourse.voucher.presentation.Command;

public record ApplicationRequest<T>(Command command, T payload) {
}
