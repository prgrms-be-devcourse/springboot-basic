package com.prgrms.vouchermanager.exception;

import com.prgrms.vouchermanager.message.ConsoleMessage;
import com.prgrms.vouchermanager.message.LogMessage;

public class FileIOException extends MyException {

    public FileIOException(Object object) {
        super(String.format(LogMessage.FILE_IO_EXCEPTION.getMessage(), object.getClass().getName()));
    }

    @Override
    public String consoleMessage() {
        return ConsoleMessage.FILE_IO_EXCEPTION.getMessage();
    }

}
