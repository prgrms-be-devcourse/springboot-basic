package org.devcourse.voucher.error;

import java.util.function.Supplier;

public enum ErrorType {
    INVALID_COMMAND(ErrorProperties::getInvalidCommand),
    INPUT_NEGATIVE_NUMBERS(ErrorProperties::getInputNegativeNumbers),
    INPUT_NOT_NUMBERS(ErrorProperties::getInputNotNumbers);


    private Supplier<String> error;

    ErrorType(Supplier<String> error) {
        this.error = error;
    }

    public String message() {
        return error.get();
    }
}