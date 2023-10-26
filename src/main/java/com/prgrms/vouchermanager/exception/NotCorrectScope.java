package com.prgrms.vouchermanager.exception;

import com.prgrms.vouchermanager.message.ConsoleMessage;
import com.prgrms.vouchermanager.message.LogMessage;

public class NotCorrectScope extends MyException {
    public NotCorrectScope(Long discount) {
        super(String.format(LogMessage.NOT_CORRECT_SCOPE.getMessage(), discount));
    }

    @Override
    public String consoleMessage() {
        return ConsoleMessage.NOT_CORRECT_SCOPE.getMessage();
    }
}
