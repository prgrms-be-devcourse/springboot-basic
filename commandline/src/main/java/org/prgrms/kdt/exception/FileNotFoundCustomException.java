package org.prgrms.kdt.exception;

public class FileNotFoundCustomException extends ServerException {

    public FileNotFoundCustomException() {
    }

    public FileNotFoundCustomException(String message) {
        super(message);
    }
}
