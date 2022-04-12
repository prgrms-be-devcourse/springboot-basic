package org.prgrms.voucherapp.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WrongInputException extends Exception {

    private String message;

    @Override
    public String toString() {
        return message;
    }
}
