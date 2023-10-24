package org.prgms.kdtspringweek1.exception;

public class FileException extends RuntimeException {
    private String message;

    public FileException(FileExceptionCode fileExceptionCode) {
        this.message = fileExceptionCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
