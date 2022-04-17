package com.kdt.commandLineApp.exception;

public class FileSaveException extends CommandLineApplicationException {
    public FileSaveException() {
        this.msg = "error in saving voucher file.";
    }

    public FileSaveException(String msg) {
        super(msg);
    }
}
