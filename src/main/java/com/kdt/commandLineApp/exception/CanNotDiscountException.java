package com.kdt.commandLineApp.exception;

public class CanNotDiscountException extends CommandLineApplicationException {
    public CanNotDiscountException() {
        this.msg = "can not discount";
    }
}
