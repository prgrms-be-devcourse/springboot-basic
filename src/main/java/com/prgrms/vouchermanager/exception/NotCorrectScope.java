package com.prgrms.vouchermanager.exception;

import com.prgrms.vouchermanager.message.ConsoleMessage;

public class NotCorrectScope extends Exception {
    public NotCorrectScope() {
        super(ConsoleMessage.NOT_CORRECT_SCOPE.getMessage());
    }

}
