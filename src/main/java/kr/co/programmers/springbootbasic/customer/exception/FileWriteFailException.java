package kr.co.programmers.springbootbasic.customer.exception;

public class FileWriteFailException extends RuntimeException {
    public FileWriteFailException(String message) {
        super(message);
    }
}
