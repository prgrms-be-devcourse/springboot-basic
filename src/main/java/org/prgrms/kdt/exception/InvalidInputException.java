package org.prgrms.kdt.exception;

// exception /  error message
public class InvalidInputException extends RuntimeException{
    public static final String msg = "입력이 잘못되었습니다.";

    public InvalidInputException() {
        super(msg);
    }
}
