package org.prgrms.kdt.exception;

/**
 * Created by yhh1056
 * Date: 2021/09/10 Time: 12:06 오후
 */
public class BadRequestException extends RuntimeException {

    public BadRequestException() {
        super();
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(Throwable cause) {
        super(cause);
    }

}
