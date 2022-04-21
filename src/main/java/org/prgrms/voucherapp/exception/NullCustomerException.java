package org.prgrms.voucherapp.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NullCustomerException extends RuntimeException{
    private final String message;

    @Override
    public String toString(){
        return this.message;
    }
}
