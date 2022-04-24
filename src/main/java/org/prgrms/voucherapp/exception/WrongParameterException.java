package org.prgrms.voucherapp.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WrongParameterException extends RuntimeException{
    private String message;

    @Override
    public String toString() {
        return message;
    }
}
