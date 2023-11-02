package com.prgrms.vouchermanager.exception;

import com.prgrms.vouchermanager.message.ConsoleMessage;
import com.prgrms.vouchermanager.message.LogMessage;

public class NotCorrectFormException extends MyException {
    public NotCorrectFormException(String command) {
        super(String.format(LogMessage.NOT_CORRECT_FORM.getMessage(), command));
    }

    @Override
    public String consoleMessage() {
        return ConsoleMessage.NOT_CORRECT_FORM.getMessage();
    }
}
