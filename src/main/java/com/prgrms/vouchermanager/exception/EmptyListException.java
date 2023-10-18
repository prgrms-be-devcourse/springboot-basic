package com.prgrms.vouchermanager.exception;

import com.prgrms.vouchermanager.message.ConsoleMessage;

import java.util.List;

public class EmptyListException extends RuntimeException {
    public EmptyListException() {
        super(ConsoleMessage.EMPTY_LIST_EXCEPTION.getMessage());
    }
}
