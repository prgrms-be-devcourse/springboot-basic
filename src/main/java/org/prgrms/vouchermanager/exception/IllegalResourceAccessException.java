package org.prgrms.vouchermanager.exception;

/**
 * CustomException 입니다.
 * 외부 file에 접근했을 때, 에러가 생긴다면 이 예외를 던집니다.
 */
public class IllegalResourceAccessException extends RuntimeException {
    public IllegalResourceAccessException() {
    }

    public IllegalResourceAccessException(String message) {
        super(message);
    }

    public IllegalResourceAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalResourceAccessException(Throwable cause) {
        super(cause);
    }

    public IllegalResourceAccessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
