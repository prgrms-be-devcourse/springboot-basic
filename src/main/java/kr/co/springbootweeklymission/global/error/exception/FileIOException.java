package kr.co.springbootweeklymission.global.error.exception;

import kr.co.springbootweeklymission.global.response.ResponseStatus;

public class FileIOException extends RuntimeException {
    public FileIOException(ResponseStatus responseStatus) {
        super(responseStatus.getMessage());
    }
}
