package com.prgrms.vouchermanager.exception;

import com.prgrms.vouchermanager.message.ConsoleMessage;

public class NotCorrectIdException extends MyException {

    public NotCorrectIdException() {
        super(String.format(ConsoleMessage.NOT_CORRECT_ID.getMessage()));
    }

    @Override
    public String consoleMessage() {
        return ConsoleMessage.NOT_CORRECT_ID.getMessage();
    }
}
