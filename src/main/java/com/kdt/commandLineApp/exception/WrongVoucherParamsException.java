package com.kdt.commandLineApp.exception;

public class WrongVoucherParamsException extends CommandLineApplicationException {
    public WrongVoucherParamsException() {
        this.msg = "insert correct voucher.html type";
    }

    public WrongVoucherParamsException(String msg) {
        super(msg);
    }
}
