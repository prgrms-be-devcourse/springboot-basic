package org.prgms.springbootbasic.exception;

public class FileIOException extends RuntimeException{

    public FileIOException(String message, Throwable cause) {
        super(message, cause);
    }
}
