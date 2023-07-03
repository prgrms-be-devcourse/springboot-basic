package com.devcourse.voucher.presentation.dto;

import com.devcourse.voucher.presentation.Command;

import static com.devcourse.voucher.presentation.Command.CREATE;

public record ApplicationRequest<T>(Command command, T payload) {

    public static <T> ApplicationRequest creation(T payload) {
        return new ApplicationRequest(CREATE, payload);
    }

    public static ApplicationRequest noPayload(Command command) {
        return new ApplicationRequest(command, null);
    }
}
