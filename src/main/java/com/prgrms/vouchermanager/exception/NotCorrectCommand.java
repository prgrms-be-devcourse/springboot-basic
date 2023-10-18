package com.prgrms.vouchermanager.exception;

import com.prgrms.vouchermanager.message.ConsoleMessage;

public class NotCorrectCommand extends RuntimeException {

    public NotCorrectCommand() {
        super(ConsoleMessage.NOT_CORRECT_COMMAND.getMessage());
    }
}
