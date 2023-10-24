package com.prgrms.vouchermanager.exception;

import com.prgrms.vouchermanager.message.ConsoleMessage;
import com.prgrms.vouchermanager.message.LogMessage;

public class EmptyListException extends MyException {
    public EmptyListException(Object list) {
        super(String.format(LogMessage.EMPTY_LIST_EXCEPTION.getMessage(), list.getClass().getName()));
    }

    @Override
    public String consoleMessage() {
        return ConsoleMessage.EMPTY_LIST_EXCEPTION.getMessage();
    }
}
