package com.programmers.springmission.global.exception;

/**
 * 잘못된 입력으로 인하여 발생하는 예외를 로그로 남긴다.
 *
 * @see com.programmers.springmission.global.aop.LoggerAspect
 */

public class InvalidInputException extends RuntimeException {

    public InvalidInputException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}

