package kr.co.programmers.springbootbasic.global.exception;

public class NoValidCommandException extends RuntimeException {
    public NoValidCommandException(String s) {
        super(s);
    }
}
