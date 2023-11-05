package com.prgrms.voucher_manage.base;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

import static com.prgrms.voucher_manage.base.ErrorMessage.EMPTY_INPUT_NOT_ALLOWED;
import static com.prgrms.voucher_manage.base.ErrorMessage.INVALID_DISCOUNT_RANGE;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    protected BaseResponse<String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        return extractErrors(errors);
    }

    private BaseResponse<String> extractErrors(List<String> errors) {
        ErrorMessage[] statusList = new ErrorMessage[]{
                EMPTY_INPUT_NOT_ALLOWED, INVALID_DISCOUNT_RANGE
        };


        for (ErrorMessage message : statusList) {
            if (errors.contains(message.getMessage())) {
                return new BaseResponse<>(message.getMessage());
            }
        }

        return new BaseResponse<>("잘못된 요청입니다.");
    }
}
