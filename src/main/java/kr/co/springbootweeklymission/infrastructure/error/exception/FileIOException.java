package kr.co.springbootweeklymission.infrastructure.error.exception;

import kr.co.springbootweeklymission.infrastructure.error.model.ResponseStatus;

public class FileIOException extends RuntimeException {
    public FileIOException(ResponseStatus responseStatus) {
        super(responseStatus.getMessage());
    }
}
