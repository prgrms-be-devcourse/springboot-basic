package com.prgrms.vouchermanager.exception;

import com.prgrms.vouchermanager.message.ConsoleMessage;

public class EmptyListException extends RuntimeException {
    public EmptyListException() {
        super(ConsoleMessage.EMPTY_LIST_EXCEPTION.getMessage());
    }
}
