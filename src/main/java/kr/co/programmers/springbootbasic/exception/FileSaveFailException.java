package kr.co.programmers.springbootbasic.exception;

public class FileSaveFailException extends RuntimeException {
    public FileSaveFailException(String message) {
        super(message);
    }
}
