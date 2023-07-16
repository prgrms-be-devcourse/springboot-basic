package com.programmers.springweekly.exception;

import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    final static String ERROR_MSG = "errorMsg";
    final static String ERROR_CODE = "errorCode";
    final static String ERROR_PAGE = "errorPage";

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoSuchElementException(NoSuchElementException e, Model model) {
        log.warn("GlobalExceptionHandler - NoSuchElementException 발생, 찾는 데이터가 없음 {}", e.getMessage(), e);
        model.addAttribute(ERROR_MSG, e.getMessage());
        model.addAttribute(ERROR_CODE, 404);

        return ERROR_PAGE;
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleNullPointerException(NullPointerException e, Model model) {
        log.error("GlobalExceptionHandler - NullPointerException 발생 {}", e.getMessage(), e);
        model.addAttribute(ERROR_MSG, e.getMessage());
        model.addAttribute(ERROR_CODE, 500);

        return ERROR_PAGE;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgumentException(IllegalArgumentException e, Model model) {
        log.error("GlobalExceptionHandler - IllegalArgumentException 발생, 클라이언트의 잘못된 입력 값 예상 {}", e.getMessage(), e);
        model.addAttribute(ERROR_MSG, e.getMessage());
        model.addAttribute(ERROR_CODE, 400);

        return ERROR_PAGE;
    }

    @ExceptionHandler(IndexOutOfBoundsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIndexOutOfBoundsException(IndexOutOfBoundsException e, Model model) {
        log.error("GlobalExceptionHandler - IndexOutOfBoundsException 발생, 배열의 범위를 초과한 작업 예상 {}", e.getMessage(), e);
        model.addAttribute(ERROR_MSG, e.getMessage());
        model.addAttribute(ERROR_CODE, 400);

        return ERROR_PAGE;
    }

    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleDuplicateKeyException(DuplicateKeyException e, Model model) {
        log.error("GlobalExceptionHandler - DuplicateKeyException 발생, 유니크/중복 키 충돌 예상 {}", e.getMessage(), e);
        model.addAttribute(ERROR_MSG, e.getMessage());
        model.addAttribute(ERROR_CODE, 409);

        return ERROR_PAGE;
    }

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleDataAccessException(DataAccessException e, Model model) {
        log.error("GlobalExceptionHandler - DataAccessException 발생, 데이터 접근 관련 예외 {}", e.getMessage(), e);
        model.addAttribute(ERROR_MSG, e.getMessage());
        model.addAttribute(ERROR_CODE, 500);

        return ERROR_PAGE;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception e, Model model) {
        log.error("GlobalExceptionHandler - Exception 발생, 개발자가 잡지 못한 예외 {}", e.getMessage(), e);
        model.addAttribute(ERROR_MSG, e.getMessage());
        model.addAttribute(ERROR_CODE, 500);

        return ERROR_PAGE;
    }

}
