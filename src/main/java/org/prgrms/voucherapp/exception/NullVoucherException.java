package org.prgrms.voucherapp.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NullVoucherException extends Exception{
    private final String message;

    @Override
    public String toString(){
        return this.message;
    }
}
