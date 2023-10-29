package com.prgrms.vouchermanager.exception;

import com.prgrms.vouchermanager.message.ConsoleMessage;
import com.prgrms.vouchermanager.message.LogMessage;

public class NotCorrectCommandException extends MyException {

    public NotCorrectCommandException(String command) {
        super(String.format(LogMessage.NOT_CORRECT_COMMAND.getMessage(), command));
    }

    @Override
    public String consoleMessage() {
        return ConsoleMessage.NOT_CORRECT_COMMAND.getMessage();
    }
}
