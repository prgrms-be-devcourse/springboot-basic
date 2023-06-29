package kr.co.programmers.springbootbasic.exception;

public class NoValidCommandException extends RuntimeException {
    public NoValidCommandException(String s) {
        super(s);
    }
}
