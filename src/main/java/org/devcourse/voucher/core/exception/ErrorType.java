package org.devcourse.voucher.core.exception;

import org.devcourse.voucher.core.configuration.ErrorProperties;

import java.util.function.Supplier;

public enum ErrorType {
    INVALID_COMMAND(ErrorProperties::getInvalidCommand),
    INPUT_NEGATIVE_NUMBERS(ErrorProperties::getInputNegativeNumbers),
    INPUT_NOT_NUMBERS(ErrorProperties::getInputNotNumbers),
    INVALID_TYPE(ErrorProperties::getInvalidType),
    NOT_FOUND_VOUCHER(ErrorProperties::getNotFoundVoucher),
    NOT_FOUND_CUSTOMER(ErrorProperties::getNotFoundCustomer);

    private Supplier<String> error;

    ErrorType(Supplier<String> error) {
        this.error = error;
    }

    public String message() {
        return error.get();
    }
}