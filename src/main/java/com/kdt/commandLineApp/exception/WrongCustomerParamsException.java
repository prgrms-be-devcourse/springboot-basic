package com.kdt.commandLineApp.exception;

public class WrongCustomerParamsException extends CommandLineApplicationException {
    public WrongCustomerParamsException() {
        this.msg = "insert correct customer info";
    }
}
