package org.programmers.springbootbasic.web;

import lombok.extern.slf4j.Slf4j;
import org.programmers.springbootbasic.voucher.domain.IllegalVoucherTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class ApiControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ApiErrorData handleIllegalVoucherTypeException(IllegalVoucherTypeException e) {
        log.warn("API: 클라이언트에게 잘못된 바우처 종류를 전달받음, 에러 정보:", e);
        return new ApiErrorData("잘못된 바우처 종류 입력", "올바른 바우처 종류를 입력하세요.");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ApiErrorData handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("API: 클라이언트에게 잘못된 인자 값을 전달받음, 에러 정보:", e);
        return new ApiErrorData("잘못된 입력값", "정상 값으로 시도하세요.");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ApiErrorData handleOtherException(Exception e) {
        log.error("API: 식별되지 않은 오류에 대해 사용자에게 메시지 전달함, 에러 정보:", e);
        return new ApiErrorData("서버 내부 오류", "죄송합니다.");
    }

    record ApiErrorData(String exceptionName, String message) {
    }
}
