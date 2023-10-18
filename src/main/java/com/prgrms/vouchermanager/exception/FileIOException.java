package com.prgrms.vouchermanager.exception;

import com.prgrms.vouchermanager.message.ConsoleMessage;

public class FileIOException extends RuntimeException {

    public FileIOException() {
        super(ConsoleMessage.FILE_IO_EXCEPTION.getMessage());
    }

}
