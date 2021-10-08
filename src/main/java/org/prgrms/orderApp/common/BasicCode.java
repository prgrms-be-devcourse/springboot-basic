package org.prgrms.orderApp.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum BasicCode {
    GET_ALL_LIST_SUCCESS(OK, "모든 voucher 정보를 취합하여 드립니다."),
    GET_DETAIL_SUCCESS(OK, "특정 voucher 정보를 전송드립니다."),
    GET_LIST_BY_DATE_SUCCESS(OK, "요청해주신 기간 안에 생성된 바우처 정보를 전송해 드립니다."),
    GET_LIST_BY_TYPE_SUCCESS(OK, "요청해주신 바우처 타입의 모든 정보를 전송해 드립니다."),
    DELETE_VOUCHER_BY_ID_SUCCESS(OK, "성공적으로 voucher 정보를 삭제 완료하였습니다."),
    CREATE_VOUCHER_SUCCESS(OK, "성공적으로 voucher 생성이 완료되었습니다."),

    UNEXPECTED_ERROR(BAD_GATEWAY, "예기치 못한 에러가 발생하였습니다. 불편을 드려 죄송합니다.");
    private final HttpStatus httpStatus;
    private final String errorMessage;
}

