package com.prgrms.common.codes;

import lombok.Getter;

@Getter
public enum HttpErrorCode {

    /**
     * ******************************* Global Error CodeList ***************************************
     * HTTP Status Code
     * 400 : Bad Request
     * 401 : Unauthorized
     * 403 : Forbidden
     * 404 : Not Found
     * 500 : Internal Server Error
     * *********************************************************************************************
     */
    BAD_REQUEST_ERROR(400, "G001", "Bad Request Exception"),
    REQUEST_BODY_MISSING_ERROR(400, "G002", "Required request body is missing"),
    INVALID_TYPE_VALUE(400, "G003", " Invalid Type Value"),
    MISSING_REQUEST_PARAMETER_ERROR(400, "G004", "Missing Servlet RequestParameter Exception"),
    IO_ERROR(400, "G005", "I/O Exception"),

    NOT_FOUND_ERROR(404, "G006", "Not Found Exception"),
    NULL_POINT_ERROR(404, "G007", "Null Point Exception"),
    NOT_VALID_ERROR(404, "G008", "handle Validation Exception"),
    NOT_VALID_HEADER_ERROR(404, "G009", "Header에 데이터가 존재하지 않는 경우 "),

    INTERNAL_SERVER_ERROR(500, "G010", "Internal Server Error Exception"),

    /**
     * ******************************* Custom Error CodeList ***************************************
     */
    INSERT_ERROR(200, "C001", "Insert Transaction Error Exception"),
    UPDATE_ERROR(200, "C002", "Update Transaction Error Exception"),

    NEGATIVE_ARGUMENT_ERROR(400, "C003","음수를 입력할 수 없습니다."),
    USER_NOT_FOUND_ERROR(400,"C004", "해당하는 정보의 사용자를 찾을 수 없습니다."),
    DISCOUNT_PERCENT_LIMIT_ERROR(400,"C005", "100보다 큰 값을 입력할 수 없습니다."),
    DISCOUNT_AMOUNT_LIMIT_ERROR(400,"C006", "일정 수준을 초과하는 값을 입력할 수 없습니다.")
    ;

    private final int status;

    private final String divisionCode;

    private final String message;

    HttpErrorCode(final int status, final String divisionCode, final String message) {
        this.status = status;
        this.divisionCode = divisionCode;
        this.message = message;
    }

}
