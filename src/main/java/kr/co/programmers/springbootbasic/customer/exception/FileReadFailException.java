package kr.co.programmers.springbootbasic.customer.exception;

public class FileReadFailException extends RuntimeException {
    public FileReadFailException(String message) {
        super(message);
    }
}
