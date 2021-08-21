package org.prgrms.kdt.exception;

import java.io.IOException;

public class FileIOException extends RuntimeException {
    public FileIOException(ErrorMessage message, IOException error) {
        super(message.name() + error);
    }

}
