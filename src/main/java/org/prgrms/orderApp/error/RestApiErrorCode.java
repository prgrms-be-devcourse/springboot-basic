package org.prgrms.orderApp.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum RestApiErrorCode {
    NOT_FOUNT_SEARCH_TYPE(BAD_REQUEST, "요청하시는 검색 타입은 본 api에서는 제공하지 않는 검색 타입입니다. 확인 바랍니다."),

    NOT_FOUNT_VOUCHER_ID(BAD_REQUEST, "Voucher Id 를 찾을 수가 없습니다. "),
    FAIL_DELETE_BY_ID(BAD_REQUEST, "요청하신 삭제처리를 실패했습니다."),
    PERCENT_VOUCHER_OVER_100(BAD_REQUEST, "Percent 쿠폰은 100을 넘을 수 없습니다."),
    FAIL_SAVE_VOUCHER(BAD_REQUEST, "쿠폰 저장을 실패하였습니다."),

    IllegalArgumentException(BAD_REQUEST, "전송하신 정보가 옳지 않습니다. 확인바립니다."),
    NoSuchElementException(BAD_REQUEST,"알 수 없는 값을 전송해 주셨습니다. 확인바랍니다."),
    DateTimeParseException(BAD_REQUEST,"Date 타입이 잘 못 되어씁니다. yyyy-dd-MM 형식이 맞는지 확인 바립니다.");

    private final HttpStatus httpStatus;
    private final String errorMessage;
}
