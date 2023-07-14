package org.prgrms.assignment.voucher.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    AMOUNT_ERROR(HttpStatus.BAD_REQUEST, "Amount의 범위가 잘못되었습니다."),
    PERCENT_ERROR(HttpStatus.BAD_REQUEST, "Percent의 범위가 잘못되었습니다."),
    NO_DATA_ERROR(HttpStatus.BAD_REQUEST, "요청한 데이터가 없습니다.");

    private final HttpStatus status;
    private final String message;

}
