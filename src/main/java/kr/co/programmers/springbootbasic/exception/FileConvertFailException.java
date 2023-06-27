package kr.co.programmers.springbootbasic.exception;

public class FileConvertFailException extends RuntimeException {
    public FileConvertFailException(String message) {
        super(message);
    }
}
