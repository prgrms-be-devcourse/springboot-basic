package org.prgrms.voucherapp.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WrongAmountException extends Exception {
    private final String message;

    @Override
    public String toString(){
        return this.message;
    }

}
