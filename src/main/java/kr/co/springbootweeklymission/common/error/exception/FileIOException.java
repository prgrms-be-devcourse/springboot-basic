package kr.co.springbootweeklymission.common.error.exception;

import kr.co.springbootweeklymission.common.response.ResponseStatus;

public class FileIOException extends RuntimeException {
    public FileIOException(ResponseStatus responseStatus) {
        super(responseStatus.getMessage());
    }
}
