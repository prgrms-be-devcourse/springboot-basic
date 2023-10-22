package com.prgrms.vouchermanager.exception;

import com.prgrms.vouchermanager.domain.Customer;
import com.prgrms.vouchermanager.domain.Voucher;
import com.prgrms.vouchermanager.message.ConsoleMessage;
import com.prgrms.vouchermanager.message.LogMessage;

import java.util.List;
import java.util.Objects;

public class EmptyListException extends MyException {
    public EmptyListException(Object list) {
        super(String.format(LogMessage.EMPTY_LIST_EXCEPTION.getMessage(), list.getClass().getName()));
    }

    @Override
    public String consoleMessage() {
        return ConsoleMessage.EMPTY_LIST_EXCEPTION.getMessage();
    }
}
