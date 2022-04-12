package com.kdt.commandLineApp.exception;

public class FileLoadException extends CommandLineApplicationException {
    public FileLoadException() {
        this.msg = "error in loading voucher file.";
    }
}
