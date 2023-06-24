package com.devcourse.springbootbasic.engine.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvalidDataException extends RuntimeException {

    public static final String INVALID_MENU = "올바른 메뉴를 선택해주세요.";
    public static final String INVALID_VOUCHER_TYPE = "올바른 바우처 메뉴를 선택해주세요.";
    public static final String INVALID_LIST_MENU = "올바른 목록 메뉴를 선택해주세요.";
    public static final String INVALID_VOUCHER_INFO = "올바른 바우처 정보를 입력해주세요.";
    public static final String INVALID_DISCOUNT_VALUE = "부적절한 값입니다. 0.0 ~ 100.0 사이의 값을 입력해주세요.";
    public static final String INVALID_FILE_ACCESS = "부적절한 파일 접근입니다.";
    private static final Logger logger = LoggerFactory.getLogger(InvalidDataException.class);

    public InvalidDataException() {
        super();
    }

    public InvalidDataException(String errorMessage) {
        super(errorMessage);
        logger.error(errorMessage);
    }
}
