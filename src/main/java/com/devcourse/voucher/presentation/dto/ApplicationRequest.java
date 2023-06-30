package com.devcourse.voucher.presentation.dto;

import com.devcourse.console.Command;

public record ApplicationRequest<T>(Command command, T payload) {
}
