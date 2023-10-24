package com.prgrms.vouchermanager.exception;

import com.prgrms.vouchermanager.message.ConsoleMessage;

public class NotCorrectId extends MyException {

    public NotCorrectId() {
        super(String.format(ConsoleMessage.NOT_CORRECT_ID.getMessage()));
    }

    @Override
    public String consoleMessage() {
        return ConsoleMessage.NOT_CORRECT_ID.getMessage();
    }
}
