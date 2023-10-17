package com.prgrms.vouchermanager.exception;

public class NotCorrectCommand extends RuntimeException {
    public NotCorrectCommand() {
        super("The command is incorrect.");
    }
}
